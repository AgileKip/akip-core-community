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
public class EventListenerChangedNote implements ApplicationListener<EventChangedNote> {

    private final SubscriptionService subscriptionService;

    @Autowired
    public EventListenerChangedNote(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @Async
    public void onApplicationEvent(EventChangedNote event) {
        NoteDTO noteChangedEvent = event.getNote();
        if (noteChangedEvent.getEntityName().equals("processInstance")) {
            subscriptionService.notifyChangedNote(noteChangedEvent.getEntityId());
            return;
        }
        for (NoteEntityDTO otherEntity : noteChangedEvent.getOtherEntities()) {
            if (otherEntity.getEntityName().equals("processInstance")) {
                subscriptionService.notifyAddedAttachment(noteChangedEvent.getEntityId());
                return;
            }
        }
    }
}
