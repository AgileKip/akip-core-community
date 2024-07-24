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

    public void publishEventCompleteTask(Object source, TaskInstanceDTO taskInstance) {
        EventCompletedTask event = new EventCompletedTask(source, taskInstance);
        applicationEventPublisher.publishEvent(event);
    }

    public void publishEventAddedNote(Object source, NoteDTO note) {
        EventAddedNote event = new EventAddedNote(source, note);
        applicationEventPublisher.publishEvent(event);
    }

    public void publishEventChangedNote(Object source, NoteDTO note) {
        EventChangedNote event = new EventChangedNote(source, note);
        applicationEventPublisher.publishEvent(event);
    }

    public void publishEventRemovedNote(Object source, NoteDTO note) {
        EventRemovedNote event = new EventRemovedNote(source, note);
        applicationEventPublisher.publishEvent(event);
    }

    public void publishEventAddedAttachment(Object source, AttachmentDTO attachment) {
        EventAddedAttachment event = new EventAddedAttachment(source, attachment);
        applicationEventPublisher.publishEvent(event);
    }

    public void publishEventChangedAttachment(Object source, AttachmentDTO attachment) {
        EventChangedAttachment event = new EventChangedAttachment(source, attachment);
        applicationEventPublisher.publishEvent(event);
    }

    public void publishEventRemovedAttachment(Object source, AttachmentDTO attachment) {
        EventRemovedAttachment event = new EventRemovedAttachment(source, attachment);
        applicationEventPublisher.publishEvent(event);
    }
}
