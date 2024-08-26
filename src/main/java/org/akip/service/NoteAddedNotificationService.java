package org.akip.service;

import org.akip.domain.ProcessInstance;
import org.akip.domain.ProcessInstanceNotification;
import org.akip.repository.ProcessInstanceNotificationRepository;
import org.akip.service.mapper.ProcessInstanceNotificationMapper;
import org.springframework.stereotype.Service;

@Service
public class NoteAddedNotificationService extends AbstractNotificationService {
    @Override
    public ProcessInstanceNotification createNotification(Long eventId, ProcessInstance processInstance) {
        ProcessInstanceNotification notification = new ProcessInstanceNotification();
        notification.setTitle("Note Added Notification");
        notification.setDescription(
                "A new note with the identifier: " + eventId + " has been added to the process " +
                        processInstance.getProcessDefinition().getName() +
                        " with the instance: " + processInstance.getBusinessKey()
        );
        return notification;
    }
}
