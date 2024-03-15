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
public class NoteAddedEventListener implements ApplicationListener<NoteAddedEvent> {

    private final SubscriptionService subscriptionService;

    @Autowired
    public NoteAddedEventListener(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @Async
    public void onApplicationEvent(NoteAddedEvent event) {
        NoteDTO noteAddedEvent = event.getNote();
        if (noteAddedEvent.getEntityName().equals("processInstance")) {
            subscriptionService.noteAddedEventNotify(noteAddedEvent.getEntityId());
            return;
        }
        for (NoteEntityDTO otherEntity : noteAddedEvent.getOtherEntities()) {
            if (otherEntity.getEntityName().equals("processInstance")) {
                subscriptionService.attachmentAddedEventNotify(noteAddedEvent.getEntityId());
                return;
            }
        }
    }
}
