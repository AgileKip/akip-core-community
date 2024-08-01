package org.akip.listener;

import org.akip.domain.AttachmentEntity;
import org.akip.event.*;
import org.akip.repository.AttachmentEntityRepository;
import org.akip.service.SubscriptionService;
import org.akip.service.dto.AttachmentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AttachmentChangedEventListener implements ApplicationListener<AttachmentChangedEvent> {

    private final SubscriptionService subscriptionService;
    private final AttachmentEntityRepository attachmentEntityRepository;

    @Autowired
    public AttachmentChangedEventListener(SubscriptionService subscriptionService,
                                          AttachmentEntityRepository attachmentEntityRepository) {
        this.subscriptionService = subscriptionService;
        this.attachmentEntityRepository = attachmentEntityRepository;
    }

    @Async
    public void onApplicationEvent(AttachmentChangedEvent event) {
        AttachmentDTO attachmentChangedEvent = event.getAttachment();
        List<AttachmentEntity> attachmentEntities = attachmentEntityRepository.findAttachmentEntityByAttachment_Id(attachmentChangedEvent.getId());
        for (AttachmentEntity attachmentEntity : attachmentEntities) {
            if (attachmentEntity.getEntityName().equals("ProcessInstance")) {
                subscriptionService.notifyAttachmentChanged(attachmentEntity.getEntityId());
                return;
            }
        }
    }
}
