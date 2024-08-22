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
public class AttachmentAddedEventListener implements ApplicationListener<AttachmentAddedEvent> {

    private final SubscriptionService subscriptionService;
    private final AttachmentEntityRepository attachmentEntityRepository;

    @Autowired
    public AttachmentAddedEventListener(SubscriptionService subscriptionService,
                                        AttachmentEntityRepository attachmentEntityRepository) {
        this.subscriptionService = subscriptionService;
        this.attachmentEntityRepository = attachmentEntityRepository;
    }

    @Async
    public void onApplicationEvent(AttachmentAddedEvent event) {
        AttachmentDTO attachmentAddedEvent = event.getAttachment();
        List<AttachmentEntity> attachmentEntities = attachmentEntityRepository.findAttachmentEntityByAttachment_Id(attachmentAddedEvent.getId());
        for (AttachmentEntity attachmentEntity : attachmentEntities) {
            if (attachmentEntity.getEntityName().equals("ProcessInstance")) {
                subscriptionService.notifyAttachmentAdded(attachmentEntity.getEntityId());
                return;
            }
        }
    }
}
