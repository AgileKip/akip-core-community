package org.akip.service;

import org.akip.domain.ProcessInstance;
import org.akip.domain.ProcessInstanceNotification;
import org.akip.domain.ProcessInstanceSubscription;
import org.akip.domain.enumeration.ProcessInstanceEventType;
import org.akip.repository.ProcessInstanceRepository;
import org.akip.repository.ProcessInstanceSubscriptionRepository;
import org.akip.resolver.UserResolver;
import org.akip.service.mapper.ProcessInstanceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.function.Predicate;

@Service
public class NoteChangedNotificationService extends AbstractNotificationService {

    public NoteChangedNotificationService(ProcessInstanceSubscriptionMailService processInstanceSubscriptionMailService, ProcessInstanceSubscriptionRepository processInstanceSubscriptionRepository, UserResolver userResolver, ProcessInstanceNotificationService processInstanceNotificationService, ProcessInstanceRepository processInstanceRepository) {
        super(processInstanceSubscriptionMailService, processInstanceSubscriptionRepository, userResolver, processInstanceNotificationService, processInstanceRepository);
    }

    @Override
    protected String getTitle() {
        return "Note Edited Notification";
    }

    @Override
    protected String getDescription(Long entityId, ProcessInstance processInstance) {
        return "A note with the identifier: " + entityId + " in the process " +
                processInstance.getProcessDefinition().getName() +
                " with the instance: " + processInstance.getBusinessKey() +
                ", which you signed, has been edited.";
    }

    @Override
    protected String getEmailTitle() {
        return "email.noteChangedEvent.title";
    }

    @Override
    protected String getEmailTemplate() {
        return "mail/noteChangedEventEmail";
    }

    @Override
    protected ProcessInstanceEventType getNotificationType() {
        return ProcessInstanceEventType.NOTE_CHANGED;
    }

    @Override
    protected Predicate<? super ProcessInstanceSubscription> getFilter() {
        return ProcessInstanceSubscription::getNotifyNotes;
    }
}
