package org.akip.service;

import org.akip.domain.ProcessInstance;
import org.akip.domain.ProcessInstanceSubscription;
import org.akip.domain.enumeration.ProcessInstanceEventType;
import org.akip.repository.ProcessInstanceNotificationRepository;
import org.akip.repository.ProcessInstanceRepository;
import org.akip.repository.ProcessInstanceSubscriptionRepository;
import org.akip.resolver.UserResolver;
import org.akip.service.dto.NoteDTO;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.function.Predicate;

@Service
public class NoteRemovedNotificationService extends AbstractNotificationService {


    public NoteRemovedNotificationService(ProcessInstanceNotificationMailService processInstanceNotificationMailService, ProcessInstanceSubscriptionRepository processInstanceSubscriptionRepository, UserResolver userResolver, ProcessInstanceNotificationService processInstanceNotificationService, ProcessInstanceNotificationRepository processInstanceNotificationRepository, ProcessInstanceRepository processInstanceRepository) {
        super(processInstanceNotificationMailService, processInstanceSubscriptionRepository, userResolver, processInstanceNotificationService, processInstanceNotificationRepository, processInstanceRepository);
    }

    @Override
    protected String getTitle(Object source, ProcessInstance processInstance) {

        return "Note Removed in the " + processInstance.getBusinessKey();
    }

    @Override
    protected String getDescription(Object source, ProcessInstance processInstance) {
        NoteDTO note = (NoteDTO) source;
        return  " A note was removed by user: " + note.getAuthor() +",\n" +
                " with content: " + note.getText() + ",\n" +
                " on this date: " + note.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    @Override
    protected ProcessInstanceEventType getNotificationType() {
        return ProcessInstanceEventType.NOTE_REMOVED;
    }

    @Override
    protected Predicate<? super ProcessInstanceSubscription> getFilter() {
        return ProcessInstanceSubscription::getNotifyNotes;
    }
}

