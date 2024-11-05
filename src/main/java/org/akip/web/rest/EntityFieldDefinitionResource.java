package org.akip.web.rest;

import org.akip.exception.BadRequestErrorException;
import org.akip.repository.EntityFieldDefinitionRepository;
import org.akip.service.EntityFieldDefinitionService;
import org.akip.service.dto.EntityFieldDefinitionDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class EntityFieldDefinitionResource {

    private final Logger log = LoggerFactory.getLogger(EntityFieldDefinitionResource.class);

    private static final String ENTITY_NAME = "entityFieldDefinition";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EntityFieldDefinitionService entityFieldDefinitionService;

    private final EntityFieldDefinitionRepository entityFieldDefinitionRepository;

    public EntityFieldDefinitionResource(
        EntityFieldDefinitionService entityFieldDefinitionService,
        EntityFieldDefinitionRepository entityFieldDefinitionRepository
    ) {
        this.entityFieldDefinitionService = entityFieldDefinitionService;
        this.entityFieldDefinitionRepository = entityFieldDefinitionRepository;
    }

    /**
     * {@code POST  /entity-field-definitions} : Create a new entityFieldDefinition.
     *
     * @param entityFieldDefinitionDTO the entityFieldDefinitionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new entityFieldDefinitionDTO, or with status {@code 400 (Bad Request)} if the entityFieldDefinition has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/entity-field-definitions")
    public ResponseEntity<EntityFieldDefinitionDTO> createEntityFieldDefinition(
        @RequestBody EntityFieldDefinitionDTO entityFieldDefinitionDTO
    ) throws URISyntaxException {
        log.debug("REST request to save EntityFieldDefinition : {}", entityFieldDefinitionDTO);
        if (entityFieldDefinitionDTO.getId() != null) {
            throw new BadRequestErrorException("A new entityFieldDefinition cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EntityFieldDefinitionDTO result = entityFieldDefinitionService.save(entityFieldDefinitionDTO);
        return ResponseEntity
            .created(new URI("/api/entity-field-definitions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /entity-field-definitions/:id} : Updates an existing entityFieldDefinition.
     *
     * @param id the id of the entityFieldDefinitionDTO to save.
     * @param entityFieldDefinitionDTO the entityFieldDefinitionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated entityFieldDefinitionDTO,
     * or with status {@code 400 (Bad Request)} if the entityFieldDefinitionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the entityFieldDefinitionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/entity-field-definitions/{id}")
    public ResponseEntity<EntityFieldDefinitionDTO> updateEntityFieldDefinition(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EntityFieldDefinitionDTO entityFieldDefinitionDTO
    ) throws URISyntaxException {
        log.debug("REST request to update EntityFieldDefinition : {}, {}", id, entityFieldDefinitionDTO);
        if (entityFieldDefinitionDTO.getId() == null) {
            throw new BadRequestErrorException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, entityFieldDefinitionDTO.getId())) {
            throw new BadRequestErrorException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!entityFieldDefinitionRepository.existsById(id)) {
            throw new BadRequestErrorException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EntityFieldDefinitionDTO result = entityFieldDefinitionService.save(entityFieldDefinitionDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, entityFieldDefinitionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /entity-field-definitions/:id} : Partial updates given fields of an existing entityFieldDefinition, field will ignore if it is null
     *
     * @param id the id of the entityFieldDefinitionDTO to save.
     * @param entityFieldDefinitionDTO the entityFieldDefinitionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated entityFieldDefinitionDTO,
     * or with status {@code 400 (Bad Request)} if the entityFieldDefinitionDTO is not valid,
     * or with status {@code 404 (Not Found)} if the entityFieldDefinitionDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the entityFieldDefinitionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/entity-field-definitions/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<EntityFieldDefinitionDTO> partialUpdateEntityFieldDefinition(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EntityFieldDefinitionDTO entityFieldDefinitionDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update EntityFieldDefinition partially : {}, {}", id, entityFieldDefinitionDTO);
        if (entityFieldDefinitionDTO.getId() == null) {
            throw new BadRequestErrorException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, entityFieldDefinitionDTO.getId())) {
            throw new BadRequestErrorException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!entityFieldDefinitionRepository.existsById(id)) {
            throw new BadRequestErrorException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EntityFieldDefinitionDTO> result = entityFieldDefinitionService.partialUpdate(entityFieldDefinitionDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, entityFieldDefinitionDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /entity-field-definitions} : get all the entityFieldDefinitions.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of entityFieldDefinitions in body.
     */
    @GetMapping("/entity-field-definitions")
    public List<EntityFieldDefinitionDTO> getAllEntityFieldDefinitions() {
        log.debug("REST request to get all EntityFieldDefinitions");
        return entityFieldDefinitionService.findAll();
    }

    /**
     * {@code GET  /entity-field-definitions/:id} : get the "id" entityFieldDefinition.
     *
     * @param id the id of the entityFieldDefinitionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the entityFieldDefinitionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/entity-field-definitions/{id}")
    public ResponseEntity<EntityFieldDefinitionDTO> getEntityFieldDefinition(@PathVariable Long id) {
        log.debug("REST request to get EntityFieldDefinition : {}", id);
        Optional<EntityFieldDefinitionDTO> entityFieldDefinitionDTO = entityFieldDefinitionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(entityFieldDefinitionDTO);
    }

    /**
     * {@code DELETE  /entity-field-definitions/:id} : delete the "id" entityFieldDefinition.
     *
     * @param id the id of the entityFieldDefinitionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/entity-field-definitions/{id}")
    public ResponseEntity<Void> deleteEntityFieldDefinition(@PathVariable Long id) {
        log.debug("REST request to delete EntityFieldDefinition : {}", id);
        entityFieldDefinitionService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
