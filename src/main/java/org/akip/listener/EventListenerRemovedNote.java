package org.akip.listener;

import org.akip.event.*;
import org.akip.service.SubscriptionService;
import org.akip.service.dto.NoteDTO;
import org.akip.service.dto.NoteEntityDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class EventListenerRemovedNote implements ApplicationListener<EventRemovedNote> {

    private final SubscriptionService subscriptionService;

    @Autowired
    public EventListenerRemovedNote(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @Async
    public void onApplicationEvent(EventRemovedNote event) {
        NoteDTO noteRemovedEvent = event.getNote();
        if (noteRemovedEvent.getEntityName().equals("processInstance")) {
            subscriptionService.notifyRemovedNote(noteRemovedEvent.getEntityId());
            return;
        }
        for (NoteEntityDTO otherEntity : noteRemovedEvent.getOtherEntities()) {
            if (otherEntity.getEntityName().equals("processInstance")) {
                subscriptionService.notifyAddedAttachment(noteRemovedEvent.getEntityId());
                return;
            }
        }
    }
}
