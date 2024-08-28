package org.akip.listener;

import org.akip.event.*;
import org.akip.service.TaskCompletedNotificationService;
import org.akip.service.dto.TaskInstanceDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class CompletedTaskEventListener {

    private final Logger log = LoggerFactory.getLogger(CompletedTaskEventListener.class);

    private final TaskCompletedNotificationService taskCompletedNotificationService;

    @Autowired
    public CompletedTaskEventListener(
            TaskCompletedNotificationService taskCompletedNotificationService) {
        this.taskCompletedNotificationService = taskCompletedNotificationService;
    }

    @Async
    @EventListener
    public void onApplicationEvent(TaskCompletedEvent event) throws InterruptedException {
        TaskInstanceDTO completedTask = (TaskInstanceDTO) event.getSource();
        taskCompletedNotificationService.notifyUsers(completedTask ,completedTask.getProcessInstance().getId());
    }
}
