package org.akip.event;

import org.akip.service.dto.NoteDTO;
import org.springframework.context.ApplicationEvent;

public class NoteAddedEvent extends ApplicationEvent {

    private final NoteDTO note;

    public NoteAddedEvent(Object source, NoteDTO note) {
        super(source);
        this.note = note;
    }

    public NoteDTO getNote() {
        return note;
    }
}
