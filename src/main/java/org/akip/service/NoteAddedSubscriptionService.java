package org.akip.service;

import org.akip.domain.ProcessInstanceSubscription;
import org.akip.domain.enumeration.ProcessInstanceEventType;
import org.akip.repository.ProcessInstanceRepository;
import org.akip.repository.ProcessInstanceSubscriptionRepository;
import org.akip.resolver.UserResolver;
import org.akip.service.mapper.ProcessInstanceMapper;
import org.springframework.stereotype.Service;

import java.util.function.Predicate;

@Service
public class NoteAddedSubscriptionService extends AbstractSubscriptionService {

    public NoteAddedSubscriptionService(ProcessInstanceSubscriptionMailService processInstanceSubscriptionMailService,
                                        ProcessInstanceSubscriptionRepository processInstanceSubscriptionRepository,
                                        UserResolver userResolver,
                                        ProcessInstanceNotificationService processInstanceNotificationService,
                                        ProcessInstanceMapper processInstanceMapper,
                                        ProcessInstanceRepository processInstanceRepository) {
        super(processInstanceSubscriptionMailService, processInstanceSubscriptionRepository, userResolver, processInstanceNotificationService, processInstanceMapper, processInstanceRepository);
    }

    @Override
    protected String emailTitle() {
        return "email.noteAddedEvent.title";
    }

    @Override
    protected String emailTemplate() {
        return "mail/noteAddedEventEmail";
    }

    @Override
    protected ProcessInstanceEventType notificationType() {
        return ProcessInstanceEventType.NOTE_ADDED;
    }

    @Override
    protected Predicate<? super ProcessInstanceSubscription> getFilter() {
        return ProcessInstanceSubscription::getNotifyNotes;
    }
}
