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
public class EventListenerRemovedAttachment implements ApplicationListener<EventRemovedAttachment> {

    private final SubscriptionService subscriptionService;
    private final AttachmentEntityRepository attachmentEntityRepository;

    @Autowired
    public EventListenerRemovedAttachment(SubscriptionService subscriptionService,
                                          AttachmentEntityRepository attachmentEntityRepository) {
        this.subscriptionService = subscriptionService;
        this.attachmentEntityRepository = attachmentEntityRepository;
    }

    @Async
    public void onApplicationEvent(EventRemovedAttachment event) {
        AttachmentDTO attachmentRemovedEvent = event.getAttachment();
        List<AttachmentEntity> attachmentEntities = attachmentEntityRepository.findAttachmentEntityByAttachment_Id(attachmentRemovedEvent.getId());
        for (AttachmentEntity attachmentEntity : attachmentEntities) {
            if (attachmentEntity.getEntityName().equals("ProcessInstance")) {
                subscriptionService.notifyRemovedAttachment(attachmentEntity.getEntityId());
                return;
            }
        }
    }
}
