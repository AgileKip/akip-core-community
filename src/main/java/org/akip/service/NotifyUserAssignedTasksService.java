package org.akip.service;

import org.akip.domain.TaskInstance;
import org.akip.domain.enumeration.StatusTaskInstance;
import org.akip.repository.TaskInstanceRepository;
import org.akip.resolver.AkipUserDTO;
import org.akip.resolver.UserResolver;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class NotifyUserAssignedTasksService {

    private final AkipMailService mailService;

    private final TaskInstanceRepository taskInstanceRepository;

    private final UserResolver userResolver;

    public NotifyUserAssignedTasksService(AkipMailService mailService, TaskInstanceRepository taskInstanceRepository, UserResolver userResolver) {
        this.mailService = mailService;
        this.taskInstanceRepository = taskInstanceRepository;
        this.userResolver = userResolver;
    }

    public Map<String, String> notifyUserAssignedTasks(){
        List<TaskInstance> tasksAssigned = taskInstanceRepository.findByStatus(StatusTaskInstance.ASSIGNED);

        Set<String> usersLogin = tasksAssigned
            .stream()
            .map(TaskInstance::getAssignee)
            .collect(Collectors.toSet());

        List<String> userLoginList = new ArrayList<>(usersLogin);

        List<AkipUserDTO> users = userResolver.getUsersByLogins(userLoginList);

        Map<String, String> summary = new HashMap<>();

        for (AkipUserDTO user : users){
            List<TaskInstance> userTasks = tasksAssigned
                .stream()
                .filter((task) -> task.getAssignee().equals(user.getLogin()))
                .collect(Collectors.toList());

            Map<String, Object> variables = new HashMap<>();
            variables.put("tasks", userTasks);
            mailService.sendNotifyUserAssignedTasksMail(user, variables);
            summary.put(user.getLogin(), "Open tasks assigned to the user: " + userTasks.stream().map(TaskInstance::getId).collect(Collectors.joining ( "," )));
        }
        return summary;
    }
}
