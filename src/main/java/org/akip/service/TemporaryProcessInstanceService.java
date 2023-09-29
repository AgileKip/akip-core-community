package org.akip.service;

import org.akip.domain.TemporaryProcessInstance;
import org.akip.repository.TemporaryProcessInstanceRepository;
import org.akip.service.dto.TemporaryProcessInstanceDTO;
import org.akip.service.mapper.TemporaryProcessInstanceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link TemporaryProcessInstance}.
 */
@Service
@Transactional
public class TemporaryProcessInstanceService {

    private final Logger log = LoggerFactory.getLogger(TemporaryProcessInstanceService.class);

    private final TemporaryProcessInstanceRepository temporaryProcessInstanceRepository;

    private final TemporaryProcessInstanceMapper temporaryProcessInstanceMapper;

    public TemporaryProcessInstanceService(
        TemporaryProcessInstanceRepository temporaryProcessInstanceRepository,
        TemporaryProcessInstanceMapper temporaryProcessInstanceMapper
    ) {
        this.temporaryProcessInstanceRepository = temporaryProcessInstanceRepository;
        this.temporaryProcessInstanceMapper = temporaryProcessInstanceMapper;
    }

    public TemporaryProcessInstanceDTO create(String bpmnProcessDefinitionId) {
        TemporaryProcessInstanceDTO temporaryProcessInstance = new TemporaryProcessInstanceDTO();
        temporaryProcessInstance.setBpmnProcessDefinitionId(bpmnProcessDefinitionId);
        temporaryProcessInstance.setStartDate(LocalDateTime.now());
        return save(temporaryProcessInstance);
    }

    /**
     * Save a temporaryProcessInstance.
     *
     * @param temporaryProcessInstanceDTO the entity to save.
     * @return the persisted entity.
     */
    public TemporaryProcessInstanceDTO save(TemporaryProcessInstanceDTO temporaryProcessInstanceDTO) {
        log.debug("Request to save TemporaryProcessInstance : {}", temporaryProcessInstanceDTO);
        TemporaryProcessInstance temporaryProcessInstance = temporaryProcessInstanceMapper.toEntity(temporaryProcessInstanceDTO);
        temporaryProcessInstance = temporaryProcessInstanceRepository.save(temporaryProcessInstance);
        return temporaryProcessInstanceMapper.toDto(temporaryProcessInstance);
    }

    /**
     * Get all the temporaryProcessInstances.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<TemporaryProcessInstanceDTO> findAll() {
        log.debug("Request to get all TemporaryProcessInstances");
        return temporaryProcessInstanceRepository
            .findAll()
            .stream()
            .map(temporaryProcessInstanceMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one temporaryProcessInstance by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TemporaryProcessInstanceDTO> findOne(Long id) {
        log.debug("Request to get TemporaryProcessInstance : {}", id);
        return temporaryProcessInstanceRepository.findById(id).map(temporaryProcessInstanceMapper::toDto);
    }

    /**
     * Delete the temporaryProcessInstance by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TemporaryProcessInstance : {}", id);
        temporaryProcessInstanceRepository.deleteById(id);
    }
}
