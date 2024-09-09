package org.akip.service;

import org.akip.domain.ProcessInstance;
import org.akip.domain.ProcessInstanceNotification;
import org.akip.domain.ProcessInstanceSubscription;
import org.akip.domain.enumeration.ProcessInstanceEventType;
import org.akip.repository.ProcessInstanceNotificationRepository;
import org.akip.repository.ProcessInstanceRepository;
import org.akip.repository.ProcessInstanceSubscriptionRepository;
import org.akip.resolver.UserResolver;
import org.akip.service.dto.AttachmentDTO;
import org.akip.service.dto.NoteDTO;
import org.akip.service.mapper.ProcessInstanceMapper;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.function.Predicate;

@Service
public class AttachmentRemovedNotificationService extends AbstractNotificationService {


    public AttachmentRemovedNotificationService(ProcessInstanceSubscriptionMailService processInstanceSubscriptionMailService, ProcessInstanceSubscriptionRepository processInstanceSubscriptionRepository, UserResolver userResolver, ProcessInstanceNotificationService processInstanceNotificationService, ProcessInstanceNotificationRepository processInstanceNotificationRepository, ProcessInstanceRepository processInstanceRepository) {
        super(processInstanceSubscriptionMailService, processInstanceSubscriptionRepository, userResolver, processInstanceNotificationService, processInstanceNotificationRepository, processInstanceRepository);
    }

    @Override
    protected String getTitle(Object source, ProcessInstance processInstance) {
        return "Attachment Removed in the: " + processInstance.getBusinessKey();
    }

    @Override
    protected String getDescription(Object source, ProcessInstance processInstance) {
        AttachmentDTO attachment = (AttachmentDTO) source;
        return  " An attachment was removed by user: " + attachment.getUploadedBy() +",\n" +
                " with name: " + attachment.getName() + ",\n" +
                " on this date: " + attachment.getUploadedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
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

