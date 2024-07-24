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
public class EventListenerAddedAttachment implements ApplicationListener<EventAddedAttachment> {

    private final SubscriptionService subscriptionService;

    @Autowired
    public EventListenerAddedAttachment(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @Async
    public void onApplicationEvent(EventAddedAttachment event) {
        AttachmentDTO attachmentAddedEvent = event.getAttachment();
        if (attachmentAddedEvent.getEntityName().equals("processInstance")) {
            subscriptionService.notifyAddedAttachment(attachmentAddedEvent.getEntityId());
            return;
        }
        for (AttachmentEntityDTO otherEntity : attachmentAddedEvent.getOtherEntities()) {
            if (otherEntity.getEntityName().equals("processInstance")) {
                subscriptionService.notifyAddedAttachment(attachmentAddedEvent.getEntityId());
                return;
            }
        }
    }
}
