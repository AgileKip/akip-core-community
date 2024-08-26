package org.akip.service;

import org.akip.domain.ProcessInstance;
import org.akip.domain.ProcessInstanceNotification;
import org.springframework.stereotype.Service;

@Service
public class AttachmentAddedNotificationService extends AbstractNotificationService {
    @Override
    public ProcessInstanceNotification createNotification(Long eventId, ProcessInstance processInstance) {
        ProcessInstanceNotification notification = new ProcessInstanceNotification();
        notification.setTitle("Attachment Added Notification");
        notification.setDescription(
                "A new attachment with the identifier: " + eventId + " has been added to the process " +
                        processInstance.getProcessDefinition().getName() +
                        " with the instance: " + processInstance.getBusinessKey()
        );
        return notification;
    }
}

