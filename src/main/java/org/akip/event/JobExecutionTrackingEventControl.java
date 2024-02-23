package org.akip.event;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

@Service
public class JobExecutionTrackingEventControl {

    private final ApplicationEventPublisher applicationEventPublisher;

    public JobExecutionTrackingEventControl(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Transactional
    public void start(String identifier, String cronName) {
        applicationEventPublisher.publishEvent(new JobExecutionTrackingEventStart(identifier, cronName));
    }

    @Transactional
    public void completeWithError(String identifier, String error, HashMap<String, String> descriptions) {
        descriptions.put(JobExecutionTrackingConstants.ERROR, error);
        applicationEventPublisher.publishEvent(
            new JobExecutionTrackingEventComplete(identifier, JobExecutionTrackingConstants.STATUS_COMPLETE_ERROR, descriptions)
        );
    }

    @Transactional
    public void completeWithSuccess(String identifier, HashMap<String, String> descriptions) {
        applicationEventPublisher.publishEvent(
            new JobExecutionTrackingEventComplete(identifier, JobExecutionTrackingConstants.STATUS_COMPLETE_SUCCESS, descriptions)
        );
    }

    public String generateJobExecutionTrackingIdentifier(String cronName) {
        return (ZonedDateTime.now(ZoneId.of("Brazil/East")).format(DateTimeFormatter.ISO_INSTANT) + "#" + cronName);
    }
}
