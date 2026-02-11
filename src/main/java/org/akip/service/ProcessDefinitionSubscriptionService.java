package org.akip.service;

import org.akip.domain.ProcessDefinitionSubscription;
import org.akip.domain.enumeration.ActiveInactiveStatus;
import org.akip.domain.enumeration.SubscriberType;
import org.akip.repository.ProcessDefinitionSubscriptionRepository;
import org.akip.security.SecurityUtils;
import org.akip.service.dto.ProcessDefinitionSubscriptionDTO;
import org.akip.service.mapper.ProcessDefinitionSubscriptionMapper;
import org.akip.domain.ProcessDefinition;
import org.akip.repository.ProcessDefinitionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

/**
 * Service Implementation for managing {@link ProcessDefinitionSubscription}.
 */
@Service
@Transactional
public class ProcessDefinitionSubscriptionService {

    private final Logger log = LoggerFactory.getLogger(ProcessDefinitionSubscriptionService.class);

    private final ProcessDefinitionSubscriptionRepository processDefinitionSubscriptionRepository;

    private final ProcessDefinitionSubscriptionMapper processDefinitionSubscriptionMapper;

    private final ProcessDefinitionRepository processDefinitionRepository;

    public ProcessDefinitionSubscriptionService(
        ProcessDefinitionSubscriptionRepository processDefinitionSubscriptionRepository,
        ProcessDefinitionSubscriptionMapper processDefinitionSubscriptionMapper,
        ProcessDefinitionRepository processDefinitionRepository
    ) {
        this.processDefinitionSubscriptionRepository = processDefinitionSubscriptionRepository;
        this.processDefinitionSubscriptionMapper = processDefinitionSubscriptionMapper;
        this.processDefinitionRepository = processDefinitionRepository;
    }

    /**
     * Save a processDefinitionSubscription.
     *
     * @param processDefinitionSubscriptionDTO the entity to save.
     * @return the persisted entity.
     */
    public ProcessDefinitionSubscriptionDTO save(
        String processDefinitionId,
        ProcessDefinitionSubscriptionDTO processDefinitionSubscriptionDTO
    ) {
        log.debug("Request to save ProcessDefinitionSubscription : {}, {}",processDefinitionId, processDefinitionSubscriptionDTO);
        ProcessDefinitionSubscription processDefinitionSubscription = processDefinitionSubscriptionMapper.toEntity(
            processDefinitionSubscriptionDTO
        );
        Optional<ProcessDefinition> processDefinition = processDefinitionRepository.findByBpmnProcessDefinitionId(processDefinitionId);
        processDefinitionSubscription.setProcessDefinition(processDefinition.get());
        processDefinitionSubscription.setBpmnProcessDefinitionId(processDefinitionId);
        processDefinitionSubscription.setSubscriberType(SubscriberType.USER);
        processDefinitionSubscription.setSubscriberId(SecurityUtils.getCurrentUserLogin().get());
        processDefinitionSubscription.setStatus(ActiveInactiveStatus.ACTIVE);
        processDefinitionSubscription.setSubscriptionDate(LocalDate.now());
        processDefinitionSubscription = processDefinitionSubscriptionRepository.save(processDefinitionSubscription);
        return processDefinitionSubscriptionMapper.toDto(processDefinitionSubscription);
    }

    /**
     * Save a processDefinitionSubscription.
     *
     * @param processDefinitionSubscriptionDTO the entity to save.
     * @return the persisted entity.
     */
    public ProcessDefinitionSubscriptionDTO save(ProcessDefinitionSubscriptionDTO processDefinitionSubscriptionDTO) {
        log.debug("Request to save ProcessDefinitionSubscription : {}", processDefinitionSubscriptionDTO);
        ProcessDefinitionSubscription processDefinitionSubscription = processDefinitionSubscriptionMapper.toEntity(
            processDefinitionSubscriptionDTO
        );
        processDefinitionSubscription = processDefinitionSubscriptionRepository.save(processDefinitionSubscription);
        return processDefinitionSubscriptionMapper.toDto(processDefinitionSubscription);
    }


    /**
     * Get one processDefinitionSubscription by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProcessDefinitionSubscriptionDTO> findOne(Long id) {
        log.debug("Request to get ProcessDefinitionSubscription : {}", id);
        return processDefinitionSubscriptionRepository.findById(id).map(processDefinitionSubscriptionMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<ProcessDefinitionSubscriptionDTO> findBySubscriberIdAndBpmnProcessDefinitionId(
        String bpmnProcessDefinitionId
    ) {
        log.debug("Request to get ProcessInstanceSubscription : {}", bpmnProcessDefinitionId);
        return processDefinitionSubscriptionRepository
            .findBySubscriberIdAndBpmnProcessDefinitionId(SecurityUtils.getCurrentUserLogin().get(), bpmnProcessDefinitionId)
            .map(processDefinitionSubscriptionMapper::toDto);
    }

    /**
     * Delete the processDefinitionSubscription by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ProcessDefinitionSubscription : {}", id);
        processDefinitionSubscriptionRepository.deleteById(id);
    }
}
