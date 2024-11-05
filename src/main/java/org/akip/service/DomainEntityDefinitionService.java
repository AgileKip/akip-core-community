package org.akip.service;

import org.akip.domain.DomainEntityDefinition;
import org.akip.repository.DomainEntityDefinitionRepository;
import org.akip.service.dto.DomainEntityDefinitionDTO;
import org.akip.service.mapper.DomainEntityDefinitionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class DomainEntityDefinitionService {

    private final Logger log = LoggerFactory.getLogger(DomainEntityDefinitionService.class);

    private final DomainEntityDefinitionRepository domainEntityDefinitionRepository;

    private final DomainEntityDefinitionMapper domainEntityDefinitionMapper;

    public DomainEntityDefinitionService(
        DomainEntityDefinitionRepository domainEntityDefinitionRepository,
        DomainEntityDefinitionMapper domainEntityDefinitionMapper
    ) {
        this.domainEntityDefinitionRepository = domainEntityDefinitionRepository;
        this.domainEntityDefinitionMapper = domainEntityDefinitionMapper;
    }

    /**
     * Save a domainEntityDefinition.
     *
     * @param domainEntityDefinitionDTO the entity to save.
     * @return the persisted entity.
     */
    public DomainEntityDefinitionDTO save(DomainEntityDefinitionDTO domainEntityDefinitionDTO) {
        log.debug("Request to save DomainEntityDefinition : {}", domainEntityDefinitionDTO);
        DomainEntityDefinition domainEntityDefinition = domainEntityDefinitionMapper.toEntity(domainEntityDefinitionDTO);
        domainEntityDefinition = domainEntityDefinitionRepository.save(domainEntityDefinition);
        return domainEntityDefinitionMapper.toDto(domainEntityDefinition);
    }

    /**
     * Partially update a domainEntityDefinition.
     *
     * @param domainEntityDefinitionDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<DomainEntityDefinitionDTO> partialUpdate(DomainEntityDefinitionDTO domainEntityDefinitionDTO) {
        log.debug("Request to partially update DomainEntityDefinition : {}", domainEntityDefinitionDTO);

        return domainEntityDefinitionRepository
            .findById(domainEntityDefinitionDTO.getId())
            .map(
                existingDomainEntityDefinition -> {
                    domainEntityDefinitionMapper.partialUpdate(existingDomainEntityDefinition, domainEntityDefinitionDTO);
                    return existingDomainEntityDefinition;
                }
            )
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
        log.debug("Request to get all DomainEntityDefinitions");
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
        log.debug("Request to get DomainEntityDefinition : {}", id);
        return domainEntityDefinitionRepository.findById(id).map(domainEntityDefinitionMapper::toDto);
    }

    /**
     * Delete the domainEntityDefinition by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DomainEntityDefinition : {}", id);
        domainEntityDefinitionRepository.deleteById(id);
    }
}
