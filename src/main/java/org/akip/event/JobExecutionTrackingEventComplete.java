package org.akip.event;

import org.springframework.context.ApplicationEvent;

import java.util.HashMap;
import java.util.Map;

public class JobExecutionTrackingEventComplete extends ApplicationEvent {
    private static final long serialVersionUID = 1L;

    public JobExecutionTrackingEventComplete(String identifier, String status, Map<String, String> summary) {
        super(new JobExecutionTrackingEventDTO(identifier, status, summary));
    }
}
