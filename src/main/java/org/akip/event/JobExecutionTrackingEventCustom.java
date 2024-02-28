package org.akip.event;

import org.springframework.context.ApplicationEvent;

public class JobExecutionTrackingEventCustom extends ApplicationEvent {
    private static final long serialVersionUID = 1L;

    public JobExecutionTrackingEventCustom(JobExecutionTrackingEventDTO source) {
        super(source);
    }
}
