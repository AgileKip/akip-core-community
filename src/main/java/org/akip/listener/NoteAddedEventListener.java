package org.akip.listener;

import org.akip.domain.NoteEntity;
import org.akip.domain.ProcessInstance;
import org.akip.event.*;
import org.akip.repository.NoteEntityRepository;
import org.akip.service.NoteAddedSubscriptionService;
import org.akip.service.dto.NoteDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NoteAddedEventListener implements ApplicationListener<NoteAddedEvent> {

    private final NoteEntityRepository noteEntityRepository;
    private final NoteAddedSubscriptionService noteAddedSubscriptionService;

    @Autowired
    public NoteAddedEventListener(
            NoteEntityRepository noteEntityRepository, NoteAddedSubscriptionService noteAddedSubscriptionService
    ) {
        this.noteEntityRepository = noteEntityRepository;
        this.noteAddedSubscriptionService = noteAddedSubscriptionService;
    }

    @Async
    public void onApplicationEvent(NoteAddedEvent event) {
        NoteDTO noteAddedEvent = event.getNote();
        List<NoteEntity> noteEntities = noteEntityRepository.findByNoteIdAndEntityName(noteAddedEvent.getId(), ProcessInstance.class.getSimpleName());
        for (NoteEntity noteEntity : noteEntities) {
            noteAddedSubscriptionService.notifyUsers(noteAddedEvent.getId(), noteEntity.getEntityId());
        }
    }
}