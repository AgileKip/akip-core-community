package org.akip.publisher;

import org.akip.event.*;
import org.akip.service.dto.AttachmentDTO;
import org.akip.service.dto.NoteDTO;
import org.akip.service.dto.TaskInstanceDTO;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class PublisherEvent {

    private final ApplicationEventPublisher applicationEventPublisher;

    public PublisherEvent(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void publishCompleteTaskEvent(Object source, TaskInstanceDTO taskInstance) {
        TaskCompletedEvent event = new TaskCompletedEvent(source, taskInstance);
        applicationEventPublisher.publishEvent(event);
    }

    public void publishNoteAddedEvent(Object source, NoteDTO note) {
        NoteAddedEvent event = new NoteAddedEvent(source, note);
        applicationEventPublisher.publishEvent(event);
    }

    public void publishNoteChangedEvent(Object source, NoteDTO note) {
        NoteChangedEvent event = new NoteChangedEvent(source, note);
        applicationEventPublisher.publishEvent(event);
    }

    public void publishNoteRemovedEvent(Object source, NoteDTO note) {
        NoteRemovedEvent event = new NoteRemovedEvent(source, note);
        applicationEventPublisher.publishEvent(event);
    }

    public void publishAttachmentAddedEvent(Object source, AttachmentDTO attachment) {
        AttachmentAddedEvent event = new AttachmentAddedEvent(source, attachment);
        applicationEventPublisher.publishEvent(event);
    }

    public void publishAttachmentChangedEvent(Object source, AttachmentDTO attachment) {
        AttachmentChangedEvent event = new AttachmentChangedEvent(source, attachment);
        applicationEventPublisher.publishEvent(event);
    }

    public void publishAttachmentRemovedEvent(Object source, AttachmentDTO attachment) {
        AttachmentRemovedEvent event = new AttachmentRemovedEvent(source, attachment);
        applicationEventPublisher.publishEvent(event);
    }
}
