package org.akip.listener;

import org.akip.event.*;
import org.akip.service.SubscriptionService;
import org.akip.service.dto.TaskInstanceDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class TaskCompletedEventListener {

    private final Logger log = LoggerFactory.getLogger(TaskCompletedEventListener.class);

    private final SubscriptionService subscriptionService;

    @Autowired
    public TaskCompletedEventListener(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @Async
    @EventListener
    public void onApplicationEvent(TaskCompletedEvent event) throws InterruptedException {
        TaskInstanceDTO completedTask = event.getTaskInstance();
        subscriptionService.taskCompletedEventNotify(completedTask.getProcessInstance());
    }
}
