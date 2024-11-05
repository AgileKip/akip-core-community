package org.akip.service;

import org.akip.domain.EntityFieldDefinition;
import org.akip.repository.EntityFieldDefinitionRepository;
import org.akip.service.dto.EntityFieldDefinitionDTO;
import org.akip.service.mapper.EntityFieldDefinitionMapper;
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
public class EntityFieldDefinitionService {

    private final Logger log = LoggerFactory.getLogger(EntityFieldDefinitionService.class);

    private final EntityFieldDefinitionRepository entityFieldDefinitionRepository;

    private final EntityFieldDefinitionMapper entityFieldDefinitionMapper;

    public EntityFieldDefinitionService(
        EntityFieldDefinitionRepository entityFieldDefinitionRepository,
        EntityFieldDefinitionMapper entityFieldDefinitionMapper
    ) {
        this.entityFieldDefinitionRepository = entityFieldDefinitionRepository;
        this.entityFieldDefinitionMapper = entityFieldDefinitionMapper;
    }

    /**
     * Save a entityFieldDefinition.
     *
     * @param entityFieldDefinitionDTO the entity to save.
     * @return the persisted entity.
     */
    public EntityFieldDefinitionDTO save(EntityFieldDefinitionDTO entityFieldDefinitionDTO) {
        log.debug("Request to save EntityFieldDefinition : {}", entityFieldDefinitionDTO);
        EntityFieldDefinition entityFieldDefinition = entityFieldDefinitionMapper.toEntity(entityFieldDefinitionDTO);
        entityFieldDefinition = entityFieldDefinitionRepository.save(entityFieldDefinition);
        return entityFieldDefinitionMapper.toDto(entityFieldDefinition);
    }

    /**
     * Partially update a entityFieldDefinition.
     *
     * @param entityFieldDefinitionDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<EntityFieldDefinitionDTO> partialUpdate(EntityFieldDefinitionDTO entityFieldDefinitionDTO) {
        log.debug("Request to partially update EntityFieldDefinition : {}", entityFieldDefinitionDTO);

        return entityFieldDefinitionRepository
            .findById(entityFieldDefinitionDTO.getId())
            .map(
                existingEntityFieldDefinition -> {
                    entityFieldDefinitionMapper.partialUpdate(existingEntityFieldDefinition, entityFieldDefinitionDTO);
                    return existingEntityFieldDefinition;
                }
            )
            .map(entityFieldDefinitionRepository::save)
            .map(entityFieldDefinitionMapper::toDto);
    }

    /**
     * Get all the entityFieldDefinitions.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<EntityFieldDefinitionDTO> findAll() {
        log.debug("Request to get all EntityFieldDefinitions");
        return entityFieldDefinitionRepository
            .findAll()
            .stream()
            .map(entityFieldDefinitionMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Transactional(readOnly = true)
    public List<EntityFieldDefinitionDTO> findByDomainEntityName(String domainEntityName) {
        log.debug("Request to get all EntityFieldDefinitions by DomainEntityName");
        return entityFieldDefinitionRepository
                .findEntityFieldDefinitionsByDefinitionName(domainEntityName)
                .stream()
                .map(entityFieldDefinitionMapper::toDto)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one entityFieldDefinition by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EntityFieldDefinitionDTO> findOne(Long id) {
        log.debug("Request to get EntityFieldDefinition : {}", id);
        return entityFieldDefinitionRepository.findById(id).map(entityFieldDefinitionMapper::toDto);
    }

    /**
     * Delete the entityFieldDefinition by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete EntityFieldDefinition : {}", id);
        entityFieldDefinitionRepository.deleteById(id);
    }
}
