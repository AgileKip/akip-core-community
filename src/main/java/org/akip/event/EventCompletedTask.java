package org.akip.event;

import org.akip.service.dto.TaskInstanceDTO;
import org.springframework.context.ApplicationEvent;

public class EventCompletedTask extends ApplicationEvent {

    private final TaskInstanceDTO taskInstance;

    public EventCompletedTask(Object source, TaskInstanceDTO taskInstance) {
        super(source);
        this.taskInstance = taskInstance;
    }

    public TaskInstanceDTO getTaskInstance() {
        return taskInstance;
    }
}
