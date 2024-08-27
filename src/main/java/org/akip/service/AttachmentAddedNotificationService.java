package org.akip.service;

import org.akip.domain.ProcessInstance;
import org.akip.domain.ProcessInstanceSubscription;
import org.akip.domain.enumeration.ProcessInstanceEventType;
import org.akip.repository.ProcessInstanceRepository;
import org.akip.repository.ProcessInstanceSubscriptionRepository;
import org.akip.resolver.UserResolver;
import org.akip.service.mapper.ProcessInstanceMapper;
import org.springframework.stereotype.Service;

import java.util.function.Predicate;

@Service
public class AttachmentAddedNotificationService extends AbstractNotificationService {

    public AttachmentAddedNotificationService(ProcessInstanceSubscriptionMailService processInstanceSubscriptionMailService, ProcessInstanceSubscriptionRepository processInstanceSubscriptionRepository, UserResolver userResolver, ProcessInstanceNotificationService processInstanceNotificationService, ProcessInstanceRepository processInstanceRepository) {
        super(processInstanceSubscriptionMailService, processInstanceSubscriptionRepository, userResolver, processInstanceNotificationService, processInstanceRepository);
    }

    @Override
    protected String getTitle() {
        return "Attachment Added Notification";
    }

    @Override
    protected String getDescription(Long entityId, ProcessInstance processInstance) {
        return  "An attachment with the identifier: " + entityId + " in the process " +
                processInstance.getProcessDefinition().getName() +
                " with the instance: " + processInstance.getBusinessKey() +
                ", which you signed, has been edited.";
    }

    @Override
    protected String getEmailTitle() {
        return "email.attachmentAddedEvent.title";
    }

    @Override
    protected String getEmailTemplate() {
        return "mail/attachmentAddedEventEmail";
    }

    @Override
    protected ProcessInstanceEventType getNotificationType() {
        return ProcessInstanceEventType.ATTACHMENT_ADDED;
    }

    @Override
    protected Predicate<? super ProcessInstanceSubscription> getFilter() {
        return ProcessInstanceSubscription::getNotifyAttachments;
    }
}

