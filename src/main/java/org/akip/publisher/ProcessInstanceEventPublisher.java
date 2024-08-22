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

    public void publishEventCompleteTask(Object source, TaskInstanceDTO taskInstance) {
        TaskCompletedEvent event = new TaskCompletedEvent(source, taskInstance);
        applicationEventPublisher.publishEvent(event);
    }

    public void publishEventAddedNote(Object source, NoteDTO note) {
        NoteAddedEvent event = new NoteAddedEvent(source, note);
        applicationEventPublisher.publishEvent(event);
    }

    public void publishEventChangedNote(Object source, NoteDTO note) {
        NoteChangedEvent event = new NoteChangedEvent(source, note);
        applicationEventPublisher.publishEvent(event);
    }

    public void publishEventRemovedNote(Object source, NoteDTO note) {
        NoteRemovedEvent event = new NoteRemovedEvent(source, note);
        applicationEventPublisher.publishEvent(event);
    }

    public void publishEventAddedAttachment(Object source, AttachmentDTO attachment) {
        AttachmentAddedEvent event = new AttachmentAddedEvent(source, attachment);
        applicationEventPublisher.publishEvent(event);
    }

    public void publishEventChangedAttachment(Object source, AttachmentDTO attachment) {
        AttachmentChangedEvent event = new AttachmentChangedEvent(source, attachment);
        applicationEventPublisher.publishEvent(event);
    }

    public void publishEventRemovedAttachment(Object source, AttachmentDTO attachment) {
        AttachmentRemovedEvent event = new AttachmentRemovedEvent(source, attachment);
        applicationEventPublisher.publishEvent(event);
    }
}
