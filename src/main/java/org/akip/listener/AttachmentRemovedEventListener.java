package org.akip.listener;

import org.akip.domain.AttachmentEntity;
import org.akip.domain.ProcessInstance;
import org.akip.event.*;
import org.akip.repository.AttachmentEntityRepository;
import org.akip.service.AttachmentRemovedNotificationService;
import org.akip.service.dto.AttachmentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AttachmentRemovedEventListener implements ApplicationListener<AttachmentRemovedEvent> {

    private final AttachmentEntityRepository attachmentEntityRepository;

    private final AttachmentRemovedNotificationService attachmentRemovedNotificationService;

    @Autowired
    public AttachmentRemovedEventListener(
            AttachmentEntityRepository attachmentEntityRepository, AttachmentRemovedNotificationService attachmentRemovedNotificationService) {
        this.attachmentEntityRepository = attachmentEntityRepository;
        this.attachmentRemovedNotificationService = attachmentRemovedNotificationService;
    }

    @Async
    public void onApplicationEvent(AttachmentRemovedEvent event) {
        AttachmentDTO attachmentRemovedEvent = (AttachmentDTO) event.getSource();
        List<AttachmentEntity> attachmentEntities = attachmentEntityRepository.findByAttachmentIdAndEntityName(attachmentRemovedEvent.getId(), ProcessInstance.class.getSimpleName());
        for (AttachmentEntity attachmentEntity : attachmentEntities) {
            attachmentRemovedNotificationService.notifyUsers(attachmentRemovedEvent, attachmentEntity.getEntityId());
        }
    }
}
