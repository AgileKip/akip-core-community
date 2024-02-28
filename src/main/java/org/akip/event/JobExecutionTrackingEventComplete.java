package org.akip.event;

import org.springframework.context.ApplicationEvent;

import java.util.HashMap;

public class JobExecutionTrackingEventComplete extends ApplicationEvent {
    private static final long serialVersionUID = 1L;

    public JobExecutionTrackingEventComplete(String identifier, String status, HashMap<String, String> descriptions) {
        super(new JobExecutionTrackingEventDTO(identifier, status, descriptions));
    }
}
