package org.akip.web.rest;

import org.akip.service.ProcessSignalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/process-signal")
public class ProcessSignalController {

    private final Logger log = LoggerFactory.getLogger(ProcessSignalController.class);

    private final ProcessSignalService camundaSignalService;

    public ProcessSignalController(ProcessSignalService camundaSignalService) {
        this.camundaSignalService = camundaSignalService;
    }

    @GetMapping("/send-broadcast-signal/{signalName}")
    public ResponseEntity<Void> sendBroadcastSignal(@PathVariable String signalName) {
        log.debug("Sending Broadcast Signal: {}", signalName);
        camundaSignalService.sendBroadcastSignal(signalName);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/send-signal-to-process-instance/{signalName}/{processInstanceId}")
    public ResponseEntity<Void> sendSignalToProcessInstance(@PathVariable String signalName, @PathVariable String processInstanceId) {
        log.debug("Sending Signal {} to Process Instance: {}", signalName, processInstanceId);
        camundaSignalService.sendSignalToProcessInstance(signalName, processInstanceId);
        return ResponseEntity.noContent().build();
    }
}
