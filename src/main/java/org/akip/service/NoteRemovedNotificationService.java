package org.akip.service;

import org.akip.domain.ProcessInstance;
import org.akip.domain.ProcessInstanceNotification;
import org.springframework.stereotype.Service;

@Service
public class NoteRemovedNotificationService extends AbstractNotificationService {
    @Override
    public ProcessInstanceNotification createNotification(Long eventId, ProcessInstance processInstance) {
        ProcessInstanceNotification notification = new ProcessInstanceNotification();
        notification.setTitle("Note Removed Notification");
        notification.setDescription(
                "A note with the identifier: " + eventId + " in the process " +
                        processInstance.getProcessDefinition().getName() +
                        " with the instance: " + processInstance.getBusinessKey() +
                        ", which you signed, has been removed."
        );
        return notification;
    }
}

