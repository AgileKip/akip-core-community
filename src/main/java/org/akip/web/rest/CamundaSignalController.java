package org.akip.web.rest;

import org.akip.service.CamundaSignalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/signal")
public class CamundaSignalController {

    private final Logger log = LoggerFactory.getLogger(CamundaSignalController.class);

    private final CamundaSignalService camundaSignalService;

    public CamundaSignalController(CamundaSignalService camundaSignalService) {
        this.camundaSignalService = camundaSignalService;
    }

    @GetMapping("/send-broadcast-signal/{signalName}")
    public ResponseEntity<Void> sendBroadcastSignal(@PathVariable String signalName) {
        log.debug("Sending Broadcast Signal: {}", signalName);
        camundaSignalService.sendBroadcastSignal(signalName);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/send-signal-to-process-instance/{processInstanceId}/{signalName}")
    public ResponseEntity<Void> sendSignalToProcessInstance(@PathVariable String signalName, @PathVariable String processInstanceId) {
        log.debug("Sending Signal {} to Process Instance: {}", signalName, processInstanceId);
        camundaSignalService.sendSignalToProcessInstance(signalName, processInstanceId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/retrieve-bpmn-signal-events/{camundaDeploymentId}")
    public List<String> retrieveSignalEvents(@PathVariable String camundaDeploymentId) {
        log.debug("Retrieving the bpmn signal events from Camunda Deployment id: {}", camundaDeploymentId);
        return camundaSignalService.retrieveSignalEvents(camundaDeploymentId);
    }
}
