package org.akip.event;

import org.springframework.context.ApplicationEvent;

public class TaskCompletedEvent extends ApplicationEvent {

    public TaskCompletedEvent(Object source) {
        super(source);
    }
}
