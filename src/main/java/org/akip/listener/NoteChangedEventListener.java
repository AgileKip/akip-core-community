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
public class NoteChangedEventListener implements ApplicationListener<NoteChangedEvent> {

    private final SubscriptionService subscriptionService;

    @Autowired
    public NoteChangedEventListener(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @Async
    public void onApplicationEvent(NoteChangedEvent event) {
        NoteDTO noteChangedEvent = event.getNote();
        if (noteChangedEvent.getEntityName().equals("processInstance")) {
            subscriptionService.noteChangedEventNotify(noteChangedEvent.getEntityId());
            return;
        }
        for (NoteEntityDTO otherEntity : noteChangedEvent.getOtherEntities()) {
            if (otherEntity.getEntityName().equals("processInstance")) {
                subscriptionService.attachmentAddedEventNotify(noteChangedEvent.getEntityId());
                return;
            }
        }
    }
}
