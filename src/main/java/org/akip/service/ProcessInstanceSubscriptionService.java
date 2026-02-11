package org.akip.service;

import org.akip.domain.ProcessDefinitionSubscription;
import org.akip.domain.ProcessInstanceSubscription;
import org.akip.domain.enumeration.ActiveInactiveStatus;
import org.akip.domain.enumeration.SubscriberType;
import org.akip.repository.ProcessDefinitionSubscriptionRepository;
import org.akip.repository.ProcessInstanceSubscriptionRepository;
import org.akip.security.SecurityUtils;
import org.akip.service.dto.ProcessInstanceDTO;
import org.akip.service.dto.ProcessInstanceSubscriptionDTO;
import org.akip.service.mapper.ProcessInstanceSubscriptionMapper;
import org.akip.domain.ProcessInstance;
import org.akip.repository.ProcessInstanceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link ProcessInstanceSubscription}.
 */
@Service
@Transactional
public class ProcessInstanceSubscriptionService {

    private final Logger log = LoggerFactory.getLogger(ProcessInstanceSubscriptionService.class);

    private final ProcessInstanceSubscriptionRepository processInstanceSubscriptionRepository;

    private final ProcessInstanceSubscriptionMapper processInstanceSubscriptionMapper;

    private final ProcessInstanceRepository processInstanceRepository;

    private final ProcessDefinitionSubscriptionRepository processDefinitionSubscriptionRepository;


    public ProcessInstanceSubscriptionService(
            ProcessInstanceSubscriptionRepository processInstanceSubscriptionRepository,
            ProcessInstanceSubscriptionMapper processInstanceSubscriptionMapper,
            ProcessInstanceRepository processInstanceRepository, ProcessDefinitionSubscriptionRepository processDefinitionSubscriptionRepository
    ) {
        this.processInstanceSubscriptionRepository = processInstanceSubscriptionRepository;
        this.processInstanceSubscriptionMapper = processInstanceSubscriptionMapper;
        this.processInstanceRepository = processInstanceRepository;
        this.processDefinitionSubscriptionRepository = processDefinitionSubscriptionRepository;
    }

    /**
     * Save a processInstanceSubscription.
     *
     * @param processInstanceId the entity to save.
     * @param processInstanceSubscriptionDTO the entity to save.
     * @return the persisted entity.
     */
    public ProcessInstanceSubscriptionDTO save(Long processInstanceId, ProcessInstanceSubscriptionDTO processInstanceSubscriptionDTO) {
        log.debug("Request to save ProcessInstanceSubscription : {} {}", processInstanceSubscriptionDTO, processInstanceSubscriptionDTO);
        ProcessInstanceSubscription processInstanceSubscription = processInstanceSubscriptionMapper.toEntity(
            processInstanceSubscriptionDTO
        );
        ProcessInstance processInstance = processInstanceRepository.findById(processInstanceId).get();
        processInstanceSubscription.setProcessInstance(processInstance);
        processInstanceSubscription.setSubscriberType(SubscriberType.USER);
        processInstanceSubscription.setSubscriberId(SecurityUtils.getCurrentUserLogin().get());
        processInstanceSubscription.setStatus(ActiveInactiveStatus.ACTIVE);
        processInstanceSubscription.setSubscriptionDate(LocalDate.now());
        processInstanceSubscription = processInstanceSubscriptionRepository.save(processInstanceSubscription);
        return processInstanceSubscriptionMapper.toDto(processInstanceSubscription);
    }

    /**
     * Save a processInstanceSubscription.
     *
     * @param processInstanceSubscriptionDTO the entity to save.
     * @return the persisted entity.
     */
    public ProcessInstanceSubscriptionDTO save(ProcessInstanceSubscriptionDTO processInstanceSubscriptionDTO) {
        log.debug("Request to save ProcessInstanceSubscription : {}", processInstanceSubscriptionDTO);
        ProcessInstanceSubscription processInstanceSubscription = processInstanceSubscriptionMapper.toEntity(
            processInstanceSubscriptionDTO
        );
        processInstanceSubscription = processInstanceSubscriptionRepository.save(processInstanceSubscription);
        return processInstanceSubscriptionMapper.toDto(processInstanceSubscription);
    }

    public void createSubscription(ProcessInstanceDTO processInstance) {
        List<ProcessDefinitionSubscription> processDefinitionSubscriptions = processDefinitionSubscriptionRepository.findByBpmnProcessDefinitionId(
                processInstance.getProcessDefinition().getBpmnProcessDefinitionId()
        );

        if (processDefinitionSubscriptions.isEmpty()) {
            return;
        }

        for (ProcessDefinitionSubscription processDefinitionSubscription : processDefinitionSubscriptions) {
            if (!processDefinitionSubscription.getNotifyAll()) {
                return;
            }

            ProcessInstanceSubscriptionDTO processInstanceSubscription = new ProcessInstanceSubscriptionDTO();
            processInstanceSubscription.setSubscriberType(processDefinitionSubscription.getSubscriberType());
            processInstanceSubscription.setSubscriberId(processDefinitionSubscription.getSubscriberId());
            processInstanceSubscription.setStatus(ActiveInactiveStatus.ACTIVE);
            processInstanceSubscription.setSubscriptionDate(processDefinitionSubscription.getSubscriptionDate());
            processInstanceSubscription.setNotifyAll(processDefinitionSubscription.getNotifyAll());
            processInstanceSubscription.setNotifyTasks(processDefinitionSubscription.getNotifyTasks());
            processInstanceSubscription.setNotifyNotes(processDefinitionSubscription.getNotifyNotes());
            processInstanceSubscription.setNotifyAttachments(processDefinitionSubscription.getNotifyAttachments());
            processInstanceSubscription.setNotifyChats(processDefinitionSubscription.getNotifyChats());
            processInstanceSubscription.setProcessInstance(processInstance);

            save(processInstanceSubscription);
        }
    }

    /**
     * Get all the processInstanceSubscriptions.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ProcessInstanceSubscriptionDTO> findBySubscriberId() {
        log.debug("Request to get all ProcessInstanceSubscriptions");
        return processInstanceSubscriptionRepository
                .findBySubscriberId(SecurityUtils.getCurrentUserLogin().get())
                .stream()
                .map(processInstanceSubscriptionMapper::toDto)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one processInstanceSubscription by id.
     *
     * @param processInstanceId the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProcessInstanceSubscriptionDTO> findByProcessInstanceId(Long processInstanceId) {
        log.debug("Request to get ProcessInstanceSubscription : {}", processInstanceId);
        return processInstanceSubscriptionRepository
            .findBySubscriberIdAndProcessInstanceId(SecurityUtils.getCurrentUserLogin().get(), processInstanceId)
            .map(processInstanceSubscriptionMapper::toDto);
    }
}
