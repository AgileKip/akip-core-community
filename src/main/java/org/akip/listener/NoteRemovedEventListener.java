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
public class NoteRemovedEventListener implements ApplicationListener<NoteRemovedEvent> {

    private final SubscriptionService subscriptionService;

    @Autowired
    public NoteRemovedEventListener(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @Async
    public void onApplicationEvent(NoteRemovedEvent event) {
        NoteDTO noteRemovedEvent = event.getNote();
        if (noteRemovedEvent.getEntityName().equals("processInstance")) {
            subscriptionService.noteRemovedEventNotify(noteRemovedEvent.getEntityId());
            return;
        }
        for (NoteEntityDTO otherEntity : noteRemovedEvent.getOtherEntities()) {
            if (otherEntity.getEntityName().equals("processInstance")) {
                subscriptionService.attachmentAddedEventNotify(noteRemovedEvent.getEntityId());
                return;
            }
        }
    }
}
