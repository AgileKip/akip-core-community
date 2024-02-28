package org.akip.event;

public class JobExecutionTrackingEventStart extends JobExecutionTrackingEventCustom {

    private static final long serialVersionUID = 1L;

    public JobExecutionTrackingEventStart(String identifier, String cronName) {
        super(new JobExecutionTrackingEventDTO(identifier, cronName));
    }
}
