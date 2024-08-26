package org.akip.service;

import org.akip.domain.ProcessInstance;
import org.akip.domain.ProcessInstanceNotification;
import org.springframework.stereotype.Service;

@Service
public class TaskCompletedNotificationService extends AbstractNotificationService {

    @Override
    public ProcessInstanceNotification createNotification(Long eventId, ProcessInstance processInstance) {
        ProcessInstanceNotification notification = new ProcessInstanceNotification();
        notification.setTitle("Completed Task Notification");
        notification.setDescription(
                "The task with the identifier: " + eventId + " from the " +
                        processInstance.getProcessDefinition().getName() +
                        " with the instance: " + processInstance.getBusinessKey() +
                        " was completed."
        );
        return notification;
    }
}
