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
public class AttachmentRemovedEventListener implements ApplicationListener<AttachmentRemovedEvent> {

    private final SubscriptionService subscriptionService;

    @Autowired
    public AttachmentRemovedEventListener(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @Async
    public void onApplicationEvent(AttachmentRemovedEvent event) {
        AttachmentDTO attachmentRemovedEvent = event.getAttachment();
        if (attachmentRemovedEvent.getEntityName().equals("processInstance")) {
            subscriptionService.notifyRemovedAttachment(attachmentRemovedEvent.getEntityId());
            return;
        }
        for (AttachmentEntityDTO otherEntity : attachmentRemovedEvent.getOtherEntities()) {
            if (otherEntity.getEntityName().equals("processInstance")) {
                subscriptionService.notifyRemovedAttachment(attachmentRemovedEvent.getEntityId());
                return;
            }
        }
    }
}
