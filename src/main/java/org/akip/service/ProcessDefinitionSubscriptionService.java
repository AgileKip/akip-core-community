package org.akip.service;

import org.akip.domain.ProcessDefinitionSubscription;
import org.akip.repository.ProcessDefinitionSubscriptionRepository;
import org.akip.service.dto.ProcessDefinitionSubscriptionDTO;
import org.akip.service.mapper.ProcessDefinitionSubscriptionMapper;
import org.akip.domain.ProcessDefinition;
import org.akip.repository.ProcessDefinitionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        log.debug("Request to save ProcessDefinitionSubscription : {}", processDefinitionSubscriptionDTO);
        ProcessDefinitionSubscription processDefinitionSubscription = processDefinitionSubscriptionMapper.toEntity(
            processDefinitionSubscriptionDTO
        );
        Optional<ProcessDefinition> processDefinition = processDefinitionRepository.findByBpmnProcessDefinitionId(processDefinitionId);
        processDefinitionSubscription.setProcessDefinition(processDefinition.get());
        processDefinitionSubscription.setBpmnProcessDefinitionId(processDefinitionId);
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
     * Partially update a processDefinitionSubscription.
     *
     * @param processDefinitionSubscriptionDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ProcessDefinitionSubscriptionDTO> partialUpdate(ProcessDefinitionSubscriptionDTO processDefinitionSubscriptionDTO) {
        log.debug("Request to partially update ProcessDefinitionSubscription : {}", processDefinitionSubscriptionDTO);

        return processDefinitionSubscriptionRepository
            .findById(processDefinitionSubscriptionDTO.getId())
            .map(
                existingProcessDefinitionSubscription -> {
                    processDefinitionSubscriptionMapper.partialUpdate(
                        existingProcessDefinitionSubscription,
                        processDefinitionSubscriptionDTO
                    );
                    return existingProcessDefinitionSubscription;
                }
            )
            .map(processDefinitionSubscriptionRepository::save)
            .map(processDefinitionSubscriptionMapper::toDto);
    }

    /**
     * Get all the processDefinitionSubscriptions.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ProcessDefinitionSubscriptionDTO> findAll() {
        log.debug("Request to get all ProcessDefinitionSubscriptions");
        return processDefinitionSubscriptionRepository
            .findAll()
            .stream()
            .map(processDefinitionSubscriptionMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
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
        String subscriberId,
        String bpmnProcessDefinitionId
    ) {
        log.debug("Request to get ProcessInstanceSubscription : {}, {}", subscriberId, bpmnProcessDefinitionId);
        return processDefinitionSubscriptionRepository
            .findBySubscriberIdAndBpmnProcessDefinitionId(subscriberId, bpmnProcessDefinitionId)
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
