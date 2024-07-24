package org.akip.listener;

import org.akip.event.*;
import org.akip.service.SubscriptionService;
import org.akip.service.dto.AttachmentDTO;
import org.akip.service.dto.AttachmentEntityDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class AttachmentChangedEventListener implements ApplicationListener<AttachmentChangedEvent> {

    private final SubscriptionService subscriptionService;

    @Autowired
    public AttachmentChangedEventListener(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @Async
    public void onApplicationEvent(AttachmentChangedEvent event) {
        AttachmentDTO attachmentChangedEvent = event.getAttachment();
        if (attachmentChangedEvent.getEntityName().equals("processInstance")) {
            subscriptionService.notifyChangedAttachment(attachmentChangedEvent.getEntityId());
            return;
        }
        for (AttachmentEntityDTO otherEntity : attachmentChangedEvent.getOtherEntities()) {
            if (otherEntity.getEntityName().equals("processInstance")) {
                subscriptionService.notifyAddedAttachment(attachmentChangedEvent.getEntityId());
                return;
            }
        }
    }
}
