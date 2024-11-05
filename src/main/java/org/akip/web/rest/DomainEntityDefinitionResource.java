package org.akip.web.rest;

import org.akip.exception.BadRequestErrorException;
import org.akip.repository.DomainEntityDefinitionRepository;
import org.akip.service.DomainEntityDefinitionService;
import org.akip.service.dto.DomainEntityDefinitionDTO;
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
public class DomainEntityDefinitionResource {

    private final Logger log = LoggerFactory.getLogger(DomainEntityDefinitionResource.class);

    private static final String ENTITY_NAME = "domainEntityDefinition";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DomainEntityDefinitionService domainEntityDefinitionService;

    private final DomainEntityDefinitionRepository domainEntityDefinitionRepository;

    public DomainEntityDefinitionResource(
        DomainEntityDefinitionService domainEntityDefinitionService,
        DomainEntityDefinitionRepository domainEntityDefinitionRepository
    ) {
        this.domainEntityDefinitionService = domainEntityDefinitionService;
        this.domainEntityDefinitionRepository = domainEntityDefinitionRepository;
    }

    /**
     * {@code POST  /domain-entity-definitions} : Create a new domainEntityDefinition.
     *
     * @param domainEntityDefinitionDTO the domainEntityDefinitionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new domainEntityDefinitionDTO, or with status {@code 400 (Bad Request)} if the domainEntityDefinition has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/domain-entity-definitions")
    public ResponseEntity<DomainEntityDefinitionDTO> createDomainEntityDefinition(
        @RequestBody DomainEntityDefinitionDTO domainEntityDefinitionDTO
    ) throws URISyntaxException {
        log.debug("REST request to save DomainEntityDefinition : {}", domainEntityDefinitionDTO);
        if (domainEntityDefinitionDTO.getId() != null) {
            throw new BadRequestErrorException("A new domainEntityDefinition cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DomainEntityDefinitionDTO result = domainEntityDefinitionService.save(domainEntityDefinitionDTO);
        return ResponseEntity
            .created(new URI("/api/domain-entity-definitions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /domain-entity-definitions/:id} : Updates an existing domainEntityDefinition.
     *
     * @param id the id of the domainEntityDefinitionDTO to save.
     * @param domainEntityDefinitionDTO the domainEntityDefinitionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated domainEntityDefinitionDTO,
     * or with status {@code 400 (Bad Request)} if the domainEntityDefinitionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the domainEntityDefinitionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/domain-entity-definitions/{id}")
    public ResponseEntity<DomainEntityDefinitionDTO> updateDomainEntityDefinition(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DomainEntityDefinitionDTO domainEntityDefinitionDTO
    ) throws URISyntaxException {
        log.debug("REST request to update DomainEntityDefinition : {}, {}", id, domainEntityDefinitionDTO);
        if (domainEntityDefinitionDTO.getId() == null) {
            throw new BadRequestErrorException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, domainEntityDefinitionDTO.getId())) {
            throw new BadRequestErrorException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!domainEntityDefinitionRepository.existsById(id)) {
            throw new BadRequestErrorException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DomainEntityDefinitionDTO result = domainEntityDefinitionService.save(domainEntityDefinitionDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, domainEntityDefinitionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /domain-entity-definitions/:id} : Partial updates given fields of an existing domainEntityDefinition, field will ignore if it is null
     *
     * @param id the id of the domainEntityDefinitionDTO to save.
     * @param domainEntityDefinitionDTO the domainEntityDefinitionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated domainEntityDefinitionDTO,
     * or with status {@code 400 (Bad Request)} if the domainEntityDefinitionDTO is not valid,
     * or with status {@code 404 (Not Found)} if the domainEntityDefinitionDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the domainEntityDefinitionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/domain-entity-definitions/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<DomainEntityDefinitionDTO> partialUpdateDomainEntityDefinition(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DomainEntityDefinitionDTO domainEntityDefinitionDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update DomainEntityDefinition partially : {}, {}", id, domainEntityDefinitionDTO);
        if (domainEntityDefinitionDTO.getId() == null) {
            throw new BadRequestErrorException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, domainEntityDefinitionDTO.getId())) {
            throw new BadRequestErrorException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!domainEntityDefinitionRepository.existsById(id)) {
            throw new BadRequestErrorException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DomainEntityDefinitionDTO> result = domainEntityDefinitionService.partialUpdate(domainEntityDefinitionDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, domainEntityDefinitionDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /domain-entity-definitions} : get all the domainEntityDefinitions.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of domainEntityDefinitions in body.
     */
    @GetMapping("/domain-entity-definitions")
    public List<DomainEntityDefinitionDTO> getAllDomainEntityDefinitions() {
        log.debug("REST request to get all DomainEntityDefinitions");
        return domainEntityDefinitionService.findAll();
    }

    /**
     * {@code GET  /domain-entity-definitions/:id} : get the "id" domainEntityDefinition.
     *
     * @param id the id of the domainEntityDefinitionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the domainEntityDefinitionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/domain-entity-definitions/{id}")
    public ResponseEntity<DomainEntityDefinitionDTO> getDomainEntityDefinition(@PathVariable Long id) {
        log.debug("REST request to get DomainEntityDefinition : {}", id);
        Optional<DomainEntityDefinitionDTO> domainEntityDefinitionDTO = domainEntityDefinitionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(domainEntityDefinitionDTO);
    }

    /**
     * {@code DELETE  /domain-entity-definitions/:id} : delete the "id" domainEntityDefinition.
     *
     * @param id the id of the domainEntityDefinitionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/domain-entity-definitions/{id}")
    public ResponseEntity<Void> deleteDomainEntityDefinition(@PathVariable Long id) {
        log.debug("REST request to delete DomainEntityDefinition : {}", id);
        domainEntityDefinitionService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
