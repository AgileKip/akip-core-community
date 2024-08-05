package org.akip.listener;

import org.akip.domain.AttachmentEntity;
import org.akip.domain.ProcessInstance;
import org.akip.event.*;
import org.akip.repository.AttachmentEntityRepository;
import org.akip.service.AttachmentRemovedSubscriptionService;
import org.akip.service.dto.AttachmentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AttachmentRemovedEventListener implements ApplicationListener<AttachmentRemovedEvent> {

    private final AttachmentEntityRepository attachmentEntityRepository;

    private final AttachmentRemovedSubscriptionService attachmentRemovedSubscriptionService;

    @Autowired
    public AttachmentRemovedEventListener(
            AttachmentEntityRepository attachmentEntityRepository, AttachmentRemovedSubscriptionService attachmentRemovedSubscriptionService) {
        this.attachmentEntityRepository = attachmentEntityRepository;
        this.attachmentRemovedSubscriptionService = attachmentRemovedSubscriptionService;
    }

    @Async
    public void onApplicationEvent(AttachmentRemovedEvent event) {
        AttachmentDTO attachmentRemovedEvent = event.getAttachment();
        List<AttachmentEntity> attachmentEntities = attachmentEntityRepository.findByAttachmentIdAndEntityName(attachmentRemovedEvent.getId(), ProcessInstance.class.getSimpleName());
        for (AttachmentEntity attachmentEntity : attachmentEntities) {
            attachmentRemovedSubscriptionService.notifyUsers(attachmentRemovedEvent.getId(), attachmentEntity.getEntityId());
        }
    }
}
