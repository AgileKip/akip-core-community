package org.akip.service;

import org.akip.domain.ProcessInstance;
import org.akip.domain.ProcessInstanceSubscription;
import org.akip.domain.enumeration.ProcessInstanceEventType;
import org.akip.repository.ProcessInstanceNotificationRepository;
import org.akip.repository.ProcessInstanceRepository;
import org.akip.repository.ProcessInstanceSubscriptionRepository;
import org.akip.resolver.UserResolver;
import org.akip.service.dto.TaskInstanceDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.function.Predicate;

@Service
public class TaskCompletedNotificationService extends AbstractNotificationService {


    public TaskCompletedNotificationService(ProcessInstanceNotificationMailService processInstanceNotificationMailService, ProcessInstanceSubscriptionRepository processInstanceSubscriptionRepository, UserResolver userResolver, ProcessInstanceNotificationService processInstanceNotificationService, ProcessInstanceNotificationRepository processInstanceNotificationRepository, ProcessInstanceRepository processInstanceRepository) {
        super(processInstanceNotificationMailService, processInstanceSubscriptionRepository, userResolver, processInstanceNotificationService, processInstanceNotificationRepository, processInstanceRepository);
    }

    @Override
    protected String getTitle(Object source, ProcessInstance processInstance) {
        return "Completed Task in the " + processInstance.getBusinessKey();
    }

    @Override
    protected String getDescription(Object source, ProcessInstance processInstance) {
        TaskInstanceDTO taskInstance = (TaskInstanceDTO) source;
        return  " A task with identifier: " + taskInstance.getId() + "\n" +
                " was completed by the user: "+ taskInstance.getAssignee() + "\n" +
                " on this date: " + LocalDateTime.ofInstant(taskInstance.getEndTime(), ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    @Override
    protected ProcessInstanceEventType getNotificationType() {
        return ProcessInstanceEventType.TASK_COMPLETED;
    }

    @Override
    protected Predicate<? super ProcessInstanceSubscription> getFilter() {
        return ProcessInstanceSubscription::getNotifyTasks;
    }
}
