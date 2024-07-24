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
public class EventListenerCompletedTask {

    private final Logger log = LoggerFactory.getLogger(EventListenerCompletedTask.class);

    private final SubscriptionService subscriptionService;

    @Autowired
    public EventListenerCompletedTask(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @Async
    @EventListener
    public void onApplicationEvent(EventCompletedTask event) throws InterruptedException {
        TaskInstanceDTO completedTask = event.getTaskInstance();
        subscriptionService.notifyCompletedTask(completedTask.getProcessInstance().getId());
    }
}
