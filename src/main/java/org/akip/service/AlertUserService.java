package org.akip.service;

import org.akip.domain.TaskInstance;
import org.akip.domain.enumeration.StatusTaskInstance;
import org.akip.repository.TaskInstanceRepository;
import org.akip.resolver.AkipUserDTO;
import org.akip.resolver.UserResolver;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class AlertUserService {

    private final AkipMailService mailService;

    private final TaskInstanceRepository taskInstanceRepository;

    private final UserResolver userResolver;

    public AlertUserService(AkipMailService mailService, TaskInstanceRepository taskInstanceRepository, UserResolver userResolver) {
        this.mailService = mailService;
        this.taskInstanceRepository = taskInstanceRepository;
        this.userResolver = userResolver;
    }

    public void alertUserAssignedTasks(HashMap<String, String> descriptions){
        int sentEmails = 0;
        Map<String, Object> variables = new HashMap<>();

        List<TaskInstance> tasksAssigned = taskInstanceRepository.findByStatus(StatusTaskInstance.ASSIGNED);

        Set<String> usersLogin = tasksAssigned
            .stream()
            .map(TaskInstance::getAssignee)
            .collect(Collectors.toSet());

        Set<AkipUserDTO> users = userResolver.getUsersByLoginInAndActivationTrue(usersLogin);

        for (AkipUserDTO user : users){
            List<TaskInstance> userTasks = tasksAssigned
                .stream()
                .filter((task) -> task.getAssignee().equals(user.getLogin()))
                .collect(Collectors.toList());

            variables.put("tasks", userTasks);
            mailService.sendUserAssignedTasksMail(user, variables);
            sentEmails++;
        }
        descriptions.put("sentEmails", "Sent Emails: " + sentEmails);
    }
}
