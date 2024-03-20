package org.akip.service;

import org.akip.domain.ProcessInstanceSubscription;
import org.akip.repository.ProcessInstanceSubscriptionRepository;
import org.akip.service.dto.ProcessInstanceSubscriptionDTO;
import org.akip.service.mapper.ProcessInstanceSubscriptionMapper;
import org.akip.domain.ProcessInstance;
import org.akip.repository.ProcessInstanceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public ProcessInstanceSubscriptionService(
        ProcessInstanceSubscriptionRepository processInstanceSubscriptionRepository,
        ProcessInstanceSubscriptionMapper processInstanceSubscriptionMapper,
        ProcessInstanceRepository processInstanceRepository
    ) {
        this.processInstanceSubscriptionRepository = processInstanceSubscriptionRepository;
        this.processInstanceSubscriptionMapper = processInstanceSubscriptionMapper;
        this.processInstanceRepository = processInstanceRepository;
    }

    /**
     * Save a processInstanceSubscription.
     *
     * @param processInstanceId the entity to save.
     * @param processInstanceSubscriptionDTO the entity to save.
     * @return the persisted entity.
     */
    public ProcessInstanceSubscriptionDTO save(Long processInstanceId, ProcessInstanceSubscriptionDTO processInstanceSubscriptionDTO) {
        log.debug("Request to save ProcessInstanceSubscription : {}", processInstanceSubscriptionDTO);
        ProcessInstanceSubscription processInstanceSubscription = processInstanceSubscriptionMapper.toEntity(
            processInstanceSubscriptionDTO
        );
        ProcessInstance processInstance = processInstanceRepository.findById(processInstanceId).get();
        processInstanceSubscription.setProcessInstance(processInstance);
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

    /**
     * Partially update a processInstanceSubscription.
     *
     * @param processInstanceSubscriptionDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<ProcessInstanceSubscriptionDTO> partialUpdate(ProcessInstanceSubscriptionDTO processInstanceSubscriptionDTO) {
        log.debug("Request to partially update ProcessInstanceSubscription : {}", processInstanceSubscriptionDTO);

        return processInstanceSubscriptionRepository
            .findById(processInstanceSubscriptionDTO.getId())
            .map(
                existingProcessInstanceSubscription -> {
                    processInstanceSubscriptionMapper.partialUpdate(existingProcessInstanceSubscription, processInstanceSubscriptionDTO);
                    return existingProcessInstanceSubscription;
                }
            )
            .map(processInstanceSubscriptionRepository::save)
            .map(processInstanceSubscriptionMapper::toDto);
    }

    /**
     * Get all the processInstanceSubscriptions.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ProcessInstanceSubscriptionDTO> findAll() {
        log.debug("Request to get all ProcessInstanceSubscriptions");
        return processInstanceSubscriptionRepository
            .findAll()
            .stream()
            .map(processInstanceSubscriptionMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one processInstanceSubscription by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProcessInstanceSubscriptionDTO> findOne(Long id) {
        log.debug("Request to get ProcessInstanceSubscription : {}", id);
        return processInstanceSubscriptionRepository.findById(id).map(processInstanceSubscriptionMapper::toDto);
    }

    /**
     * Get one processInstanceSubscription by id.
     *
     * @param subscriberId the id of the entity.
     * @param processInstanceId the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProcessInstanceSubscriptionDTO> findBySubscriberIdAndProcessInstanceId(String subscriberId, Long processInstanceId) {
        log.debug("Request to get ProcessInstanceSubscription : {}, {}", subscriberId, subscriberId);
        return processInstanceSubscriptionRepository
            .findBySubscriberIdAndProcessInstanceId(subscriberId, processInstanceId)
            .map(processInstanceSubscriptionMapper::toDto);
    }

    /**
     * Delete the processInstanceSubscription by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ProcessInstanceSubscription : {}", id);
        processInstanceSubscriptionRepository.deleteById(id);
    }
}
