package org.akip.service;

import org.akip.domain.ProcessInstance;
import org.akip.domain.ProcessInstanceSubscription;
import org.akip.domain.enumeration.ProcessInstanceEventType;
import org.akip.repository.ProcessInstanceNotificationRepository;
import org.akip.repository.ProcessInstanceRepository;
import org.akip.repository.ProcessInstanceSubscriptionRepository;
import org.akip.resolver.UserResolver;
import org.akip.service.dto.AttachmentDTO;
import org.akip.service.mapper.ProcessInstanceMapper;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.function.Predicate;

@Service
public class AttachmentAddedNotificationService extends AbstractNotificationService {


    public AttachmentAddedNotificationService(ProcessInstanceSubscriptionMailService processInstanceSubscriptionMailService, ProcessInstanceSubscriptionRepository processInstanceSubscriptionRepository, UserResolver userResolver, ProcessInstanceNotificationService processInstanceNotificationService, ProcessInstanceNotificationRepository processInstanceNotificationRepository, ProcessInstanceRepository processInstanceRepository) {
        super(processInstanceSubscriptionMailService, processInstanceSubscriptionRepository, userResolver, processInstanceNotificationService, processInstanceNotificationRepository, processInstanceRepository);
    }

    @Override
    protected String getTitle(Object source, ProcessInstance processInstance) {
        return "Attachment Added in the " + processInstance.getBusinessKey();
    }

    @Override
    protected String getDescription(Object source, ProcessInstance processInstance) {
        AttachmentDTO attachment = (AttachmentDTO) source;
        return  " An new attachment was added by user: " + attachment.getUploadedBy() +",\n" +
                " with name: " + attachment.getName() + ",\n" +
                " on this date: " + attachment.getUploadedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
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

