package org.akip.listener;

import org.akip.domain.NoteEntity;
import org.akip.event.*;
import org.akip.repository.NoteEntityRepository;
import org.akip.service.SubscriptionService;
import org.akip.service.dto.NoteDTO;
import org.akip.service.dto.NoteEntityDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EventListenerAddedNote implements ApplicationListener<EventAddedNote> {

    private final SubscriptionService subscriptionService;
    private final NoteEntityRepository noteEntityRepository;

    @Autowired
    public EventListenerAddedNote(SubscriptionService subscriptionService,
                                  NoteEntityRepository noteEntityRepository) {
        this.subscriptionService = subscriptionService;
        this.noteEntityRepository = noteEntityRepository;
    }

    @Async
    public void onApplicationEvent(EventAddedNote event) {
        NoteDTO noteAddedEvent = event.getNote();
        List<NoteEntity> noteEntities = noteEntityRepository.findNoteEntityByNote_Id(noteAddedEvent.getId());
        for (NoteEntity noteEntity : noteEntities ){
            if (noteEntity.getEntityName().equals("ProcessInstance")) {
                subscriptionService.notifyAddedNote(noteEntity.getEntityId());
                return;
            }
        }
    }
}
