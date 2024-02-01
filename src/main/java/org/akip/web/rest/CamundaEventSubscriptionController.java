package org.akip.web.rest;

import org.akip.domain.ProcessInstance;
import org.akip.exception.BadRequestErrorException;
import org.akip.repository.ProcessInstanceRepository;
import org.akip.service.dto.CamundaEventSubscriptionDTO;
import org.akip.service.mapper.CamundaEventSubscriptionsMapper;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.EventSubscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * REST controller for managing Camunda Event Subscriptions.
 */
@RestController
@RequestMapping("/api")
public class CamundaEventSubscriptionController {

    private final Logger log = LoggerFactory.getLogger(CamundaEventSubscriptionController.class);

    private final RuntimeService runtimeService;

    private final CamundaEventSubscriptionsMapper camundaEventSubscriptionsMapper;

    private final ProcessInstanceRepository processInstanceRepository;

    public CamundaEventSubscriptionController(
        RuntimeService runtimeService,
        CamundaEventSubscriptionsMapper camundaEventSubscriptionsMapper,
        ProcessInstanceRepository processInstanceRepository
    ) {
        this.runtimeService = runtimeService;
        this.camundaEventSubscriptionsMapper = camundaEventSubscriptionsMapper;
        this.processInstanceRepository = processInstanceRepository;
    }

    @GetMapping("/process-instance/{processInstanceId}/camunda-event-subscriptions")
    public List<CamundaEventSubscriptionDTO> getEventSubscriptionsByProcessInstanceId(@PathVariable Long processInstanceId) {
        log.debug("REST request to get all EventSubscriptions for process instance id: {}", processInstanceId);
        try {
            ProcessInstance processInstance = processInstanceRepository.findById(processInstanceId).orElseThrow();

            List<EventSubscription> eventSubscriptions = runtimeService
                .createEventSubscriptionQuery()
                .processInstanceId(processInstance.getCamundaProcessInstanceId())
                .list();

            return eventSubscriptions
                .stream()
                .map(camundaEventSubscriptionsMapper::mapToCamundaEventSubscriptionsDTO)
                .collect(Collectors.toList());
        } catch (Exception e) {
            log.error(
                "Error occurred while fetching EventSubscriptions for process instance id: {}. Error: {}",
                processInstanceId,
                e.getMessage()
            );
            throw new BadRequestErrorException("Error occurred while fetching EventSubscriptions", String.valueOf(e));
        }
    }

    @GetMapping("/camunda-event-subscriptions")
    public List<CamundaEventSubscriptionDTO> getAllEventSubscriptions() {
        log.debug("REST request to get all EventSubscriptions");
        try {
            List<EventSubscription> eventSubscriptions = runtimeService.createEventSubscriptionQuery().list();
            return eventSubscriptions
                .stream()
                .map(camundaEventSubscriptionsMapper::mapToCamundaEventSubscriptionsDTO)
                .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error occurred while fetching EventSubscriptions. Error: {}", e.getMessage());
            throw new BadRequestErrorException("Error occurred while fetching EventSubscriptions", String.valueOf(e));
        }
    }
}
