package org.akip.publisher;

import org.akip.event.*;
import org.akip.service.dto.AttachmentDTO;
import org.akip.service.dto.NoteDTO;
import org.akip.service.dto.TaskInstanceDTO;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class ProcessInstanceEventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    public ProcessInstanceEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void publishEventCompleteTask(Object source) {
        TaskCompletedEvent event = new TaskCompletedEvent(source);
        applicationEventPublisher.publishEvent(event);
    }

    public void publishEventAddedNote(Object source) {
        NoteAddedEvent event = new NoteAddedEvent(source);
        applicationEventPublisher.publishEvent(event);
    }

    public void publishEventChangedNote(Object source) {
        NoteChangedEvent event = new NoteChangedEvent(source);
        applicationEventPublisher.publishEvent(event);
    }

    public void publishEventRemovedNote(Object source) {
        NoteRemovedEvent event = new NoteRemovedEvent(source);
        applicationEventPublisher.publishEvent(event);
    }

    public void publishEventAddedAttachment(Object source) {
        AttachmentAddedEvent event = new AttachmentAddedEvent(source);
        applicationEventPublisher.publishEvent(event);
    }

    public void publishEventChangedAttachment(Object source) {
        AttachmentChangedEvent event = new AttachmentChangedEvent(source);
        applicationEventPublisher.publishEvent(event);
    }

    public void publishEventRemovedAttachment(Object source) {
        AttachmentRemovedEvent event = new AttachmentRemovedEvent(source);
        applicationEventPublisher.publishEvent(event);
    }
}
