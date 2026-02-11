package org.akip.listener;

import org.akip.domain.NoteEntity;
import org.akip.domain.ProcessInstance;
import org.akip.event.*;
import org.akip.repository.NoteEntityRepository;
import org.akip.service.NoteAddedNotificationService;
import org.akip.service.dto.NoteDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NoteAddedEventListener implements ApplicationListener<NoteAddedEvent> {

    private final NoteEntityRepository noteEntityRepository;
    private final NoteAddedNotificationService noteAddedNotificationService;

    public NoteAddedEventListener(
            NoteEntityRepository noteEntityRepository, NoteAddedNotificationService noteAddedNotificationService
    ) {
        this.noteEntityRepository = noteEntityRepository;
        this.noteAddedNotificationService = noteAddedNotificationService;
    }

    @Async
    public void onApplicationEvent(NoteAddedEvent event) {
        NoteDTO noteAdded = (NoteDTO) event.getSource();
        List<NoteEntity> noteEntities = noteEntityRepository.findByNoteIdAndEntityName(noteAdded.getId(), ProcessInstance.class.getSimpleName());
        for (NoteEntity noteEntity : noteEntities) {
            noteAddedNotificationService.notifyUsers(noteAdded, noteEntity.getEntityId());
        }
    }
}
