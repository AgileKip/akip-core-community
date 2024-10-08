package org.akip.scheduler;

import org.akip.event.JobExecutionTrackingEventControl;
import org.akip.service.NotifyUserAssignedTasksService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
@ConditionalOnProperty(value = "akip.notify-user-assigned-tasks.enabled", matchIfMissing = true, havingValue = "true")
public class NotifyAssignedTasksScheduler {
    private final Logger log = LoggerFactory.getLogger(NotifyAssignedTasksScheduler.class);

    @Autowired
    private JobExecutionTrackingEventControl jobExecutionTrackingEventControl;

    @Autowired
    private NotifyUserAssignedTasksService notifyUserAssignedTasksService;

    private static final String CRON_NAME = "notifyUserAssignedTasks";

    @Scheduled(cron = ("${akip.notify-user-assigned-tasks.cron-expression}"))
    public void notifyUserAssignedTasks() {
        String identifier = jobExecutionTrackingEventControl.generateJobExecutionTrackingIdentifier(CRON_NAME);
        try {
            jobExecutionTrackingEventControl.start(identifier, CRON_NAME);

            log.debug("notify open task emails are being sent to Users");
            Map<String, String> summary = notifyUserAssignedTasksService.notifyUserAssignedTasks();

            jobExecutionTrackingEventControl.completeWithSuccess(identifier, summary);
        } catch (Exception e) {
            jobExecutionTrackingEventControl.completeWithError(identifier, e.getMessage(), Collections.emptyMap());
        }
    }
}
