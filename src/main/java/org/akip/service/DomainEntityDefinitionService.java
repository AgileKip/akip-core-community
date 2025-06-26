package org.akip.service;

import org.akip.domain.DomainEntityDefinition;
import org.akip.domainEntity.DomainEntityDDLManager;
import org.akip.repository.DomainEntityDefinitionRepository;
import org.akip.service.dto.DomainEntityDefinitionDTO;
import org.akip.service.mapper.DomainEntityDefinitionMapper;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link org.akip.domain.DomainEntityDefinition}.
 */
@Service
@Transactional
public class DomainEntityDefinitionService {

    private static final Logger LOG = LoggerFactory.getLogger(DomainEntityDefinitionService.class);

    private final DomainEntityDefinitionRepository domainEntityDefinitionRepository;

    private final DomainEntityDefinitionMapper domainEntityDefinitionMapper;

    private final DomainEntityDDLManager domainEntityDDLManager;

    public DomainEntityDefinitionService(
            DomainEntityDefinitionRepository domainEntityDefinitionRepository,
            DomainEntityDefinitionMapper domainEntityDefinitionMapper, DomainEntityDDLManager domainEntityDDLManager
    ) {
        this.domainEntityDefinitionRepository = domainEntityDefinitionRepository;
        this.domainEntityDefinitionMapper = domainEntityDefinitionMapper;
        this.domainEntityDDLManager = domainEntityDDLManager;
    }

    /**
     * Save a domainEntityDefinition.
     *
     * @param domainEntityDefinitionDTO the entity to save.
     * @return the persisted entity.
     */
    public DomainEntityDefinitionDTO save(DomainEntityDefinitionDTO domainEntityDefinitionDTO) {
        LOG.debug("Request to save DomainEntityDefinition : {}", domainEntityDefinitionDTO);
        DomainEntityDefinition domainEntityDefinition = domainEntityDefinitionMapper.toEntity(domainEntityDefinitionDTO);
        DomainEntityDefinitionDTO domainEntityDefinitionSaved = domainEntityDefinitionMapper.toDto(domainEntityDefinitionRepository.save(domainEntityDefinition));
        domainEntityDDLManager.createTable(domainEntityDefinitionSaved);
        return domainEntityDefinitionSaved;
    }

    /**
     * Update a domainEntityDefinition.
     *
     * @param domainEntityDefinitionDTO the entity to save.
     * @return the persisted entity.
     */
    public DomainEntityDefinitionDTO update(DomainEntityDefinitionDTO domainEntityDefinitionDTO) {
        LOG.debug("Request to update DomainEntityDefinition : {}", domainEntityDefinitionDTO);
        DomainEntityDefinitionDTO domainEntityDefinitionOriginal = findOne(domainEntityDefinitionDTO.getId()).get();
        DomainEntityDefinition domainEntityDefinition = domainEntityDefinitionMapper.toEntity(domainEntityDefinitionDTO);
        DomainEntityDefinitionDTO domainEntityDefinitionUpdated =  domainEntityDefinitionMapper.toDto(domainEntityDefinitionRepository.save(domainEntityDefinition));
        domainEntityDDLManager.updateTable(domainEntityDefinitionOriginal, domainEntityDefinitionDTO);
        return domainEntityDefinitionUpdated;
    }

    /**
     * Partially update a domainEntityDefinition.
     *
     * @param domainEntityDefinitionDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<DomainEntityDefinitionDTO> partialUpdate(DomainEntityDefinitionDTO domainEntityDefinitionDTO) {
        LOG.debug("Request to partially update DomainEntityDefinition : {}", domainEntityDefinitionDTO);

        return domainEntityDefinitionRepository
            .findById(domainEntityDefinitionDTO.getId())
            .map(existingDomainEntityDefinition -> {
                domainEntityDefinitionMapper.partialUpdate(existingDomainEntityDefinition, domainEntityDefinitionDTO);

                return existingDomainEntityDefinition;
            })
            .map(domainEntityDefinitionRepository::save)
            .map(domainEntityDefinitionMapper::toDto);
    }

    /**
     * Get all the domainEntityDefinitions.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<DomainEntityDefinitionDTO> findAll() {
        LOG.debug("Request to get all DomainEntityDefinitions");
        return domainEntityDefinitionRepository
            .findAll()
            .stream()
            .map(domainEntityDefinitionMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one domainEntityDefinition by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DomainEntityDefinitionDTO> findOne(Long id) {
        LOG.debug("Request to get DomainEntityDefinition : {}", id);
        return domainEntityDefinitionRepository.findById(id).map(domainEntityDefinitionMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<DomainEntityDefinitionDTO> findByIdOrName(String idOrName) {
        LOG.debug("Request to get DomainEntityDefinition : {}", idOrName);
        Long id = NumberUtils.isCreatable(idOrName) ? Integer.parseInt(idOrName) : 0L;
        return domainEntityDefinitionRepository.findByIdOrName(id, idOrName).map(domainEntityDefinitionMapper::toDto);
    }

    /**
     * Delete the domainEntityDefinition by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete DomainEntityDefinition : {}", id);
        domainEntityDefinitionRepository.deleteById(id);
    }
}
