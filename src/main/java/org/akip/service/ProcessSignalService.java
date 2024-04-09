package org.akip.service;

import org.camunda.bpm.engine.RuntimeService;
import org.springframework.stereotype.Service;

@Service
public class ProcessSignalService {
    private final RuntimeService runtimeService;

    public ProcessSignalService(RuntimeService runtimeService) {
        this.runtimeService = runtimeService;
    }

    public void sendBroadcastSignal(String signalName) {
        runtimeService.signalEventReceived(signalName);
    }

    public void sendSignalToProcessInstance(String signalName, String processInstanceId) {
        runtimeService
            .createEventSubscriptionQuery()
            .eventName(signalName)
            .processInstanceId(processInstanceId)
            .list()
            .forEach(
                eventSubscription -> {
                    runtimeService.signalEventReceived(signalName, eventSubscription.getExecutionId());
                }
            );
    }
}
