package org.akip.event;

import org.springframework.context.ApplicationEvent;

public class NoteAddedEvent extends ApplicationEvent {


    public NoteAddedEvent(Object source) {
        super(source);
    }
}
