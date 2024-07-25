package org.akip.listener;


import org.akip.domain.AttachmentEntity;
import org.akip.event.*;
import org.akip.repository.AttachmentEntityRepository;
import org.akip.service.SubscriptionService;
import org.akip.service.dto.AttachmentDTO;
import org.akip.service.dto.AttachmentEntityDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EventListenerAddedAttachment implements ApplicationListener<EventAddedAttachment> {

    private final SubscriptionService subscriptionService;
    private final AttachmentEntityRepository attachmentEntityRepository;

    @Autowired
    public EventListenerAddedAttachment(SubscriptionService subscriptionService,
                                        AttachmentEntityRepository attachmentEntityRepository) {
        this.subscriptionService = subscriptionService;
        this.attachmentEntityRepository = attachmentEntityRepository;
    }

    @Async
    public void onApplicationEvent(EventAddedAttachment event) {
        AttachmentDTO attachmentAddedEvent = event.getAttachment();
        List<AttachmentEntity> attachmentEntities = attachmentEntityRepository.findAttachmentEntityByAttachment_Id(attachmentAddedEvent.getId());
        for (AttachmentEntity attachmentEntity : attachmentEntities) {
            if (attachmentEntity.getEntityName().equals("ProcessInstance")) {
                subscriptionService.notifyAddedAttachment(attachmentEntity.getEntityId());
                return;
            }
        }
    }
}
