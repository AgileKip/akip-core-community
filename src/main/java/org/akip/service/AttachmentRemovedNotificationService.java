package org.akip.service;

import org.akip.domain.ProcessInstance;
import org.akip.domain.ProcessInstanceNotification;
import org.akip.domain.ProcessInstanceSubscription;
import org.akip.domain.enumeration.ProcessInstanceEventType;
import org.akip.repository.ProcessInstanceRepository;
import org.akip.repository.ProcessInstanceSubscriptionRepository;
import org.akip.resolver.UserResolver;
import org.akip.service.dto.AttachmentDTO;
import org.akip.service.dto.NoteDTO;
import org.akip.service.mapper.ProcessInstanceMapper;
import org.springframework.stereotype.Service;

import java.util.function.Predicate;

@Service
public class AttachmentRemovedNotificationService extends AbstractNotificationService {

    public AttachmentRemovedNotificationService(ProcessInstanceSubscriptionMailService processInstanceSubscriptionMailService, ProcessInstanceSubscriptionRepository processInstanceSubscriptionRepository, UserResolver userResolver, ProcessInstanceNotificationService processInstanceNotificationService, ProcessInstanceRepository processInstanceRepository) {
        super(processInstanceSubscriptionMailService, processInstanceSubscriptionRepository, userResolver, processInstanceNotificationService, processInstanceRepository);
    }

    @Override
    protected String getTitle(Object source, ProcessInstance processInstance) {
        return "Attachment Removed Notification";
    }

    @Override
    protected String getDescription(Object source, ProcessInstance processInstance) {
        AttachmentDTO attachment = (AttachmentDTO) source;
        return   "An attachment with the identifier: " + attachment.getId() + " in the process " +
                processInstance.getProcessDefinition().getName() +
                " with the instance: " + processInstance.getBusinessKey() +
                ", which you signed, has been removed.";
    }

    @Override
    protected String getEmailTitle() {
        return "email.attachmentRemovedEvent.title";
    }

    @Override
    protected String getEmailTemplate() {
        return "mail/attachmentRemovedEventEmail";
    }

    @Override
    protected ProcessInstanceEventType getNotificationType() {
        return ProcessInstanceEventType.ATTACHMENT_REMOVED;
    }

    @Override
    protected Predicate<? super ProcessInstanceSubscription> getFilter() {
        return ProcessInstanceSubscription::getNotifyAttachments;
    }
}

