package org.akip.event;

import org.springframework.context.ApplicationEvent;

public class NoteChangedEvent extends ApplicationEvent {

    public NoteChangedEvent(Object source) {
        super(source);
    }
}
