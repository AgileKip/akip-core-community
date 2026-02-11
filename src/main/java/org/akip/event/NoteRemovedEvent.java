package org.akip.event;

import org.springframework.context.ApplicationEvent;

public class NoteRemovedEvent extends ApplicationEvent {

    public NoteRemovedEvent(Object source) {
        super(source);
    }
}
