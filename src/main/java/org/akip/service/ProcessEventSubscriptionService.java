package org.akip.service;

import org.akip.domain.ProcessInstance;
import org.akip.exception.BadRequestErrorException;
import org.akip.repository.ProcessInstanceRepository;
import org.akip.service.dto.ProcessEventSubscriptionDTO;
import org.akip.service.mapper.ProcessEventSubscriptionsMapper;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.EventSubscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProcessEventSubscriptionService {

    private final Logger log = LoggerFactory.getLogger(ProcessEventSubscriptionService.class);

    private final RuntimeService runtimeService;

    private final ProcessEventSubscriptionsMapper camundaEventSubscriptionsMapper;

    private final ProcessInstanceRepository processInstanceRepository;

    public ProcessEventSubscriptionService(
        RuntimeService runtimeService,
        ProcessEventSubscriptionsMapper camundaEventSubscriptionsMapper,
        ProcessInstanceRepository processInstanceRepository
    ) {
        this.runtimeService = runtimeService;
        this.camundaEventSubscriptionsMapper = camundaEventSubscriptionsMapper;
        this.processInstanceRepository = processInstanceRepository;
    }

    public List<ProcessEventSubscriptionDTO> getByProcessInstanceId(Long processInstanceId) {
        log.debug("REST request to get EventSubscriptions by process instance id: {}", processInstanceId);
        try {
            ProcessInstance processInstance = processInstanceRepository.findById(processInstanceId).orElseThrow();

            List<EventSubscription> eventSubscriptions = runtimeService
                .createEventSubscriptionQuery()
                .processInstanceId(processInstance.getCamundaProcessInstanceId())
                .list();

            return eventSubscriptions
                .stream()
                .map(camundaEventSubscriptionsMapper::camundaEventSubscriptionToProcessEventSubscriptionDTO)
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

    public List<ProcessEventSubscriptionDTO> getAll() {
        log.debug("REST request to get all ProcessEventSubscriptions");
        try {
            List<EventSubscription> eventSubscriptions = runtimeService.createEventSubscriptionQuery().list();
            return eventSubscriptions
                .stream()
                .map(camundaEventSubscriptionsMapper::camundaEventSubscriptionToProcessEventSubscriptionDTO)
                .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error occurred while fetching EventSubscriptions. Error: {}", e.getMessage());
            throw new BadRequestErrorException("Error occurred while fetching ProcessEventSubscriptions", String.valueOf(e));
        }
    }
}
