package org.akip.event;

import org.springframework.context.ApplicationEvent;

public class AttachmentChangedEvent extends ApplicationEvent {

    public AttachmentChangedEvent(Object source) {
        super(source);
    }
}
