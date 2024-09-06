package org.akip.service;

import org.akip.domain.ProcessInstance;
import org.akip.domain.ProcessInstanceNotification;
import org.akip.domain.ProcessInstanceSubscription;
import org.akip.domain.enumeration.ProcessInstanceEventType;
import org.akip.repository.ProcessInstanceNotificationRepository;
import org.akip.repository.ProcessInstanceRepository;
import org.akip.repository.ProcessInstanceSubscriptionRepository;
import org.akip.resolver.UserResolver;
import org.akip.service.dto.NoteDTO;
import org.akip.service.mapper.ProcessInstanceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.function.Predicate;

@Service
public class NoteAddedNotificationService extends AbstractNotificationService {


    public NoteAddedNotificationService(ProcessInstanceSubscriptionMailService processInstanceSubscriptionMailService, ProcessInstanceSubscriptionRepository processInstanceSubscriptionRepository, UserResolver userResolver, ProcessInstanceNotificationService processInstanceNotificationService, ProcessInstanceNotificationRepository processInstanceNotificationRepository, ProcessInstanceRepository processInstanceRepository) {
        super(processInstanceSubscriptionMailService, processInstanceSubscriptionRepository, userResolver, processInstanceNotificationService, processInstanceNotificationRepository, processInstanceRepository);
    }

    @Override
    protected String getTitle(Object source, ProcessInstance processInstance) {
        NoteDTO note = (NoteDTO) source;
       //String subject = messageSource.getMessage(titleKey, null, defaultLocale);
        return "Note Added in the " + processInstance.getProcessDefinition().getName();
    }

    @Override
    protected String getDescription(Object source, ProcessInstance processInstance) {
        NoteDTO note = (NoteDTO) source;
        return "A new note with identifier: " + note.getId() +
                " was added by user: " + note.getAuthor() +","+
                " with content: " + note.getText() +
                " on this date: ";
    }

    @Override
    protected ProcessInstanceEventType getNotificationType() {
        return ProcessInstanceEventType.NOTE_ADDED;
    }

    @Override
    protected Predicate<? super ProcessInstanceSubscription> getFilter() {
        return ProcessInstanceSubscription::getNotifyNotes;
    }
}
