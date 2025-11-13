package org.akip.service;

import org.akip.domain.TaskInstance;
import org.akip.domain.enumeration.StatusTaskInstance;
import org.akip.repository.TaskInstanceRepository;
import org.akip.resolver.AkipUserDTO;
import org.akip.resolver.UserResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class NotifyUserAssignedTasksService {

    private static final Logger log = LoggerFactory.getLogger(NotifyUserAssignedTasksService.class);
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

        Map<String, String> summary = new HashMap<>();

        if (tasksAssigned.isEmpty()){
            log.debug("no tasks assigned to users");
            summary.put("No tasks","No tasks assigned to users");
            return summary;
        }

        Set<String> usersLogin = tasksAssigned
            .stream()
            .map(TaskInstance::getAssignee)
            .collect(Collectors.toSet());

        if (usersLogin.isEmpty()){
            log.debug("no users assigned in tasks");
            summary.put("No users","No users assigned in tasks");
            return summary;
        }

        List<String> userLoginList = new ArrayList<>(usersLogin);

        List<AkipUserDTO> users = userResolver.getUsersByLogins(userLoginList);

        for (AkipUserDTO user : users){
            List<TaskInstance> userTasks = tasksAssigned
                .stream()
                .filter((task) -> task.getAssignee().equals(user.getLogin()))
                .collect(Collectors.toList());

            Map<String, Object> variables = new HashMap<>();
            variables.put("tasks", userTasks);
            mailService.sendNotifyUserAssignedTasksMail(user, variables);
            summary.put(user.getLogin(), "Open tasks assigned to the user: " + userTasks.stream().map(taskInstance -> taskInstance.getId().toString()).collect(Collectors.joining ( ", " )));
        }
        return summary;
    }
}
