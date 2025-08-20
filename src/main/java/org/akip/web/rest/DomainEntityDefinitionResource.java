package org.akip.web.rest;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
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

/**
 * REST controller for managing {@link org.akip.domain.DomainEntityDefinition}.
 */
@RestController
@RequestMapping("/api/domain-entity-definitions")
public class DomainEntityDefinitionResource {

    private static final Logger LOG = LoggerFactory.getLogger(DomainEntityDefinitionResource.class);

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
    @PostMapping("")
    public ResponseEntity<DomainEntityDefinitionDTO> createDomainEntityDefinition(
        @Valid @RequestBody DomainEntityDefinitionDTO domainEntityDefinitionDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to save DomainEntityDefinition : {}", domainEntityDefinitionDTO);
        if (domainEntityDefinitionDTO.getId() != null) {
            throw new BadRequestErrorException("A new domainEntityDefinition cannot already have an ID", ENTITY_NAME, "idexists");
        }
        domainEntityDefinitionDTO = domainEntityDefinitionService.save(domainEntityDefinitionDTO);
        return ResponseEntity.created(new URI("/api/domain-entity-definitions/" + domainEntityDefinitionDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, domainEntityDefinitionDTO.getId().toString()))
            .body(domainEntityDefinitionDTO);
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
    @PutMapping("/{id}")
    public ResponseEntity<DomainEntityDefinitionDTO> updateDomainEntityDefinition(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DomainEntityDefinitionDTO domainEntityDefinitionDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update DomainEntityDefinition : {}, {}", id, domainEntityDefinitionDTO);
        if (domainEntityDefinitionDTO.getId() == null) {
            throw new BadRequestErrorException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, domainEntityDefinitionDTO.getId())) {
            throw new BadRequestErrorException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!domainEntityDefinitionRepository.existsById(id)) {
            throw new BadRequestErrorException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        domainEntityDefinitionDTO = domainEntityDefinitionService.update(domainEntityDefinitionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, domainEntityDefinitionDTO.getId().toString()))
            .body(domainEntityDefinitionDTO);
    }

    /**
     * {@code GET  /domain-entity-definitions} : get all the domainEntityDefinitions.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of domainEntityDefinitions in body.
     */
    @GetMapping("")
    public List<DomainEntityDefinitionDTO> getAllDomainEntityDefinitions() {
        LOG.debug("REST request to get all DomainEntityDefinitions");
        return domainEntityDefinitionService.findAll();
    }

    /**
     * {@code GET  /domain-entity-definitions/:id} : get the "id" domainEntityDefinition.
     *
     * @param idOrName the id of the domainEntityDefinitionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the domainEntityDefinitionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{idOrName}")
    public ResponseEntity<DomainEntityDefinitionDTO> getDomainEntityDefinition(@PathVariable("idOrName") String idOrName) {
        LOG.debug("REST request to get DomainEntityDefinition : {}", idOrName);
        Optional<DomainEntityDefinitionDTO> domainEntityDefinitionDTO = domainEntityDefinitionService.findByIdOrName(idOrName);
        return ResponseUtil.wrapOrNotFound(domainEntityDefinitionDTO);
    }

    /**
     * {@code DELETE  /domain-entity-definitions/:id} : delete the "id" domainEntityDefinition.
     *
     * @param id the id of the domainEntityDefinitionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDomainEntityDefinition(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete DomainEntityDefinition : {}", id);
        domainEntityDefinitionService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
