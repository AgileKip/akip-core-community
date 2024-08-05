package org.akip.listener;

import org.akip.domain.NoteEntity;
import org.akip.domain.ProcessInstance;
import org.akip.event.*;
import org.akip.repository.NoteEntityRepository;
import org.akip.service.NoteChangedSubscriptionService;
import org.akip.service.dto.NoteDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NoteChangedEventListener implements ApplicationListener<NoteChangedEvent> {

    private final NoteChangedSubscriptionService noteChangedSubscriptionService;
    private final NoteEntityRepository noteEntityRepository;

    @Autowired
    public NoteChangedEventListener(
            NoteChangedSubscriptionService noteChangedSubscriptionService, NoteEntityRepository noteEntityRepository) {
        this.noteChangedSubscriptionService = noteChangedSubscriptionService;
        this.noteEntityRepository = noteEntityRepository;
    }

    @Async
    public void onApplicationEvent(NoteChangedEvent event) {
        NoteDTO noteChangedEvent = event.getNote();
        List<NoteEntity> noteEntities = noteEntityRepository.findByNoteIdAndEntityName(noteChangedEvent.getId(), ProcessInstance.class.getSimpleName());
        for (NoteEntity noteEntity : noteEntities ){
            noteChangedSubscriptionService.notifyUsers(noteChangedEvent.getId(), noteEntity.getEntityId());
        }
    }
}
