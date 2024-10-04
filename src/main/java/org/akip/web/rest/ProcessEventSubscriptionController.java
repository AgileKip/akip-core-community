package org.akip.web.rest;

import org.akip.service.ProcessEventSubscriptionService;
import org.akip.service.dto.ProcessEventSubscriptionDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST controller for managing ProcessEventSubscriptions.
 */
@RestController
@RequestMapping("/api")
public class ProcessEventSubscriptionController {

    private final Logger log = LoggerFactory.getLogger(ProcessEventSubscriptionController.class);

    private final ProcessEventSubscriptionService processEventSubscriptionService;

    public ProcessEventSubscriptionController(ProcessEventSubscriptionService processEventSubscriptionService) {
        this.processEventSubscriptionService = processEventSubscriptionService;
    }


    @GetMapping("/process-instance/{processInstanceId}/process-event-subscriptions")
    public List<ProcessEventSubscriptionDTO> getByProcessInstanceId(@PathVariable("processInstanceId") Long processInstanceId) {
        log.debug("REST request to get EventSubscriptions by process instance id: {}", processInstanceId);
        return processEventSubscriptionService.getByProcessInstanceId(processInstanceId);
    }

    @GetMapping("/process-event-subscriptions")
    public List<ProcessEventSubscriptionDTO> getAll() {
        return processEventSubscriptionService.getAll();
    }
}
