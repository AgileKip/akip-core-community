package org.akip.listener;


import org.akip.domain.AttachmentEntity;
import org.akip.domain.ProcessInstance;
import org.akip.event.*;
import org.akip.repository.AttachmentEntityRepository;
import org.akip.service.AttachmentAddedSubscriptionService;
import org.akip.service.dto.AttachmentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AttachmentAddedEventListener implements ApplicationListener<AttachmentAddedEvent> {

    private final AttachmentEntityRepository attachmentEntityRepository;

    private final AttachmentAddedSubscriptionService attachmentAddedSubscriptionService;

    @Autowired
    public AttachmentAddedEventListener(
            AttachmentEntityRepository attachmentEntityRepository, AttachmentAddedSubscriptionService attachmentAddedSubscriptionService) {
        this.attachmentEntityRepository = attachmentEntityRepository;
        this.attachmentAddedSubscriptionService = attachmentAddedSubscriptionService;
    }

    @Async
    public void onApplicationEvent(AttachmentAddedEvent event) {
        AttachmentDTO attachmentAddedEvent = event.getAttachment();
        List<AttachmentEntity> attachmentEntities = attachmentEntityRepository.findByAttachmentIdAndEntityName(attachmentAddedEvent.getId(), ProcessInstance.class.getSimpleName());
        for (AttachmentEntity attachmentEntity : attachmentEntities) {
            attachmentAddedSubscriptionService.notifyUsers(attachmentAddedEvent.getId(), attachmentEntity.getEntityId());
        }
    }
}