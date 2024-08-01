package org.akip.service;

import org.camunda.bpm.engine.RuntimeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CamundaSignalService {
    private final RuntimeService runtimeService;

    private final ProcessDefinitionService processDefinitionService;

    public CamundaSignalService(RuntimeService runtimeService, ProcessDefinitionService processDefinitionService) {
        this.runtimeService = runtimeService;
        this.processDefinitionService = processDefinitionService;
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

    public List<String> retrieveSignalEvents(String camundaDeploymentId) {
       return processDefinitionService.getBpmnSignalEvents(camundaDeploymentId);
    }
}
