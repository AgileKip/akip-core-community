package org.akip.event;

import org.springframework.context.ApplicationEvent;

public class AttachmentAddedEvent extends ApplicationEvent {

    public AttachmentAddedEvent(Object source) {
        super(source);
    }
}
