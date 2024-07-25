package org.akip.scheduler;

import org.akip.event.JobExecutionTrackingEventControl;
import org.akip.service.AlertUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@ConditionalOnProperty(value = "akip.alert-user-open-tasks-notify.enabled", matchIfMissing = true, havingValue = "true")
public class AlertAssignedTasksScheduler {
    private final Logger log = LoggerFactory.getLogger(AlertAssignedTasksScheduler.class);

    @Autowired
    private JobExecutionTrackingEventControl jobExecutionTrackingEventControl;

    @Autowired
    private AlertUserService alertUserService;

    private static final String CRONNAME = "alertUserAssignedTasks";

    @Scheduled(cron = ("${akip.alert-user-assigned-tasks-notify.cron-expression}"))
    public void alertUserAssignedTasks() {
        String identifier = jobExecutionTrackingEventControl.generateJobExecutionTrackingIdentifier(CRONNAME);
        HashMap<String, String> descriptions = new HashMap<>();
        try {
            jobExecutionTrackingEventControl.start(identifier, CRONNAME);

            log.debug("Open task alert emails are being sent to Users");
            alertUserService.alertUserAssignedTasks(descriptions);

            jobExecutionTrackingEventControl.completeWithSuccess(identifier, descriptions);
        } catch (Exception e) {
            jobExecutionTrackingEventControl.completeWithError(identifier, e.getMessage(), descriptions);
        }
    }
}
