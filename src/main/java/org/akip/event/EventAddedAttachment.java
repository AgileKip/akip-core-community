package org.akip.event;

import org.akip.service.dto.AttachmentDTO;
import org.springframework.context.ApplicationEvent;

public class EventAddedAttachment extends ApplicationEvent {

    private final AttachmentDTO attachment;

    public EventAddedAttachment(Object source, AttachmentDTO attachment) {
        super(source);
        this.attachment = attachment;
    }

    public AttachmentDTO getAttachment() {
        return attachment;
    }
}
