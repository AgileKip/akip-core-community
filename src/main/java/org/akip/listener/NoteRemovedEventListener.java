package org.akip.listener;

import org.akip.domain.NoteEntity;
import org.akip.domain.ProcessInstance;
import org.akip.event.*;
import org.akip.repository.NoteEntityRepository;
import org.akip.service.NoteRemovedSubscriptionService;
import org.akip.service.dto.NoteDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NoteRemovedEventListener implements ApplicationListener<NoteRemovedEvent> {

    private final NoteRemovedSubscriptionService noteRemovedSubscriptionService;
    private final NoteEntityRepository noteEntityRepository;

    @Autowired
    public NoteRemovedEventListener(
            NoteRemovedSubscriptionService noteRemovedSubscriptionService, NoteEntityRepository noteEntityRepository) {
        this.noteRemovedSubscriptionService = noteRemovedSubscriptionService;
        this.noteEntityRepository = noteEntityRepository;
    }

    @Async
    public void onApplicationEvent(NoteRemovedEvent event) {
        NoteDTO noteRemovedEvent = event.getNote();
        List<NoteEntity> noteEntities = noteEntityRepository.findByNoteIdAndEntityName(noteRemovedEvent.getId(), ProcessInstance.class.getSimpleName());
        for (NoteEntity noteEntity : noteEntities ){
            noteRemovedSubscriptionService.notifyUsers(noteRemovedEvent.getId(), noteEntity.getEntityId());
        }
    }
}
