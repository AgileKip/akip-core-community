package org.akip.listener;

import org.akip.domain.AttachmentEntity;
import org.akip.domain.ProcessInstance;
import org.akip.event.*;
import org.akip.repository.AttachmentEntityRepository;
import org.akip.service.AttachmentChangedSubscriptionService;
import org.akip.service.dto.AttachmentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AttachmentChangedEventListener implements ApplicationListener<AttachmentChangedEvent> {

    private final AttachmentChangedSubscriptionService attachmentChangedSubscriptionService;
    private final AttachmentEntityRepository attachmentEntityRepository;

    @Autowired
    public AttachmentChangedEventListener(
            AttachmentChangedSubscriptionService attachmentChangedSubscriptionService, AttachmentEntityRepository attachmentEntityRepository) {
        this.attachmentChangedSubscriptionService = attachmentChangedSubscriptionService;
        this.attachmentEntityRepository = attachmentEntityRepository;
    }

    @Async
    public void onApplicationEvent(AttachmentChangedEvent event) {
        AttachmentDTO attachmentChangedEvent = event.getAttachment();
        List<AttachmentEntity> attachmentEntities = attachmentEntityRepository.findByAttachmentIdAndEntityName(attachmentChangedEvent.getId(), ProcessInstance.class.getSimpleName());
        for (AttachmentEntity attachmentEntity : attachmentEntities) {
            attachmentChangedSubscriptionService.notifyUsers(attachmentChangedEvent.getId(), attachmentEntity.getEntityId());
        }
    }
}