package org.akip.scheduler;

import org.akip.event.JobExecutionTrackingEventControl;
import org.akip.service.NotifyUserAssignedTasksService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@ConditionalOnProperty(value = "akip.notify-user-open-tasks.enabled", matchIfMissing = true, havingValue = "true")
public class NotifyAssignedTasksScheduler {
    private final Logger log = LoggerFactory.getLogger(NotifyAssignedTasksScheduler.class);

    @Autowired
    private JobExecutionTrackingEventControl jobExecutionTrackingEventControl;

    @Autowired
    private NotifyUserAssignedTasksService notifyUserAssignedTasksService;

    private static final String CRONNAME = "notifyUserAssignedTasks";

    @Scheduled(cron = ("${akip.notify-user-assigned-tasks.cron-expression}"))
    public void alertUserAssignedTasks() {
        String identifier = jobExecutionTrackingEventControl.generateJobExecutionTrackingIdentifier(CRONNAME);
        HashMap<String, String> summary = new HashMap<>();
        try {
            jobExecutionTrackingEventControl.start(identifier, CRONNAME);

            log.debug("notify open task emails are being sent to Users");
            notifyUserAssignedTasksService.notify(summary);

            jobExecutionTrackingEventControl.completeWithSuccess(identifier, summary);
        } catch (Exception e) {
            jobExecutionTrackingEventControl.completeWithError(identifier, e.getMessage(), summary);
        }
    }
}
