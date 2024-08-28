package org.akip.event;

import org.akip.service.dto.AttachmentDTO;
import org.springframework.context.ApplicationEvent;

public class AttachmentRemovedEvent extends ApplicationEvent {

    public AttachmentRemovedEvent(Object source) {
        super(source);
    }
}
