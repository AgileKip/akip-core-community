package org.akip.web.rest;

import org.akip.exception.BadRequestErrorException;
import org.akip.repository.AkipPromptConfigurationRepository;
import org.akip.service.AkipPromptConfigurationService;
import org.akip.service.dto.AkipPromptConfigurationDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

import jakarta.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * REST controller for managing {@link org.akip.domain.AkipPromptConfiguration}.
 */
@RestController
@RequestMapping("/api")
public class AkipPromptConfigurationResource {

    private final Logger log = LoggerFactory.getLogger(AkipPromptConfigurationResource.class);

    private static final String ENTITY_NAME = "akipPromptConfiguration";

    private static final String MESSAGE_PROMPT_CONFIGURATION_CREATED = "Prompt Configuration Successfully Created";
    private static final String MESSAGE_PROMPT_CONFIGURATION_UPDATED = "Prompt Configuration Successfully Updated";
    private static final String MESSAGE_PROMPT_CONFIGURATION_REMOVED = "Prompt Configuration Successfully Removed";

    private final AkipPromptConfigurationService akipPromptConfigurationService;

    private final AkipPromptConfigurationRepository akipPromptConfigurationRepository;

    public AkipPromptConfigurationResource(
        AkipPromptConfigurationService akipPromptConfigurationService,
        AkipPromptConfigurationRepository akipPromptConfigurationRepository
    ) {
        this.akipPromptConfigurationService = akipPromptConfigurationService;
        this.akipPromptConfigurationRepository = akipPromptConfigurationRepository;
    }

    /**
     * {@code POST  /akip-prompt-configurations} : Create a new akipPromptConfiguration.
     *
     * @param akipPromptConfigurationDTO the akipPromptConfigurationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new akipPromptConfigurationDTO, or with status {@code 400 (Bad Request)} if the akipPromptConfiguration has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/akip-prompt-configurations")
    public ResponseEntity<AkipPromptConfigurationDTO> createAkipPromptConfiguration(
        @Valid @RequestBody AkipPromptConfigurationDTO akipPromptConfigurationDTO
    ) throws URISyntaxException {
        log.debug("REST request to save AkipPromptConfiguration : {}", akipPromptConfigurationDTO);
        if (akipPromptConfigurationDTO.getId() != null) {
            throw new BadRequestErrorException("A new akipPromptConfiguration cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AkipPromptConfigurationDTO result = akipPromptConfigurationService.save(akipPromptConfigurationDTO);
        return ResponseEntity
            .created(new URI("/api/akip-prompt-configurations/" + result.getId()))
            .headers(HeaderUtil.createAlert(HeaderConstants.APPLICATION_NAME, MESSAGE_PROMPT_CONFIGURATION_CREATED, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /akip-prompt-configurations/:id} : Updates an existing akipPromptConfiguration.
     *
     * @param id the id of the akipPromptConfigurationDTO to save.
     * @param akipPromptConfigurationDTO the akipPromptConfigurationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated akipPromptConfigurationDTO,
     * or with status {@code 400 (Bad Request)} if the akipPromptConfigurationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the akipPromptConfigurationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/akip-prompt-configurations/{id}")
    public ResponseEntity<AkipPromptConfigurationDTO> updateAkipPromptConfiguration(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AkipPromptConfigurationDTO akipPromptConfigurationDTO
    ) throws URISyntaxException {
        log.debug("REST request to update AkipPromptConfiguration : {}, {}", id, akipPromptConfigurationDTO);
        if (akipPromptConfigurationDTO.getId() == null) {
            throw new BadRequestErrorException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, akipPromptConfigurationDTO.getId())) {
            throw new BadRequestErrorException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!akipPromptConfigurationRepository.existsById(id)) {
            throw new BadRequestErrorException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AkipPromptConfigurationDTO result = akipPromptConfigurationService.save(akipPromptConfigurationDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createAlert(HeaderConstants.APPLICATION_NAME, MESSAGE_PROMPT_CONFIGURATION_UPDATED, id.toString()))
            .body(result);
    }

    /**
     * {@code GET  /akip-prompt-configurations} : get all the akipPromptConfigurations.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of akipPromptConfigurations in body.
     */
    @GetMapping("/akip-prompt-configurations")
    public List<AkipPromptConfigurationDTO> getAllAkipPromptConfigurations() {
        log.debug("REST request to get all AkipPromptConfigurations");
        return akipPromptConfigurationService.findAll();
    }

    /**
     * {@code GET  /akip-prompt-configurations/:id} : get the "id" akipPromptConfiguration.
     *
     * @param id the id of the akipPromptConfigurationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the akipPromptConfigurationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/akip-prompt-configurations/{id}")
    public ResponseEntity<AkipPromptConfigurationDTO> getAkipPromptConfiguration(@PathVariable("id") Long id) {
        log.debug("REST request to get AkipPromptConfiguration : {}", id);
        Optional<AkipPromptConfigurationDTO> akipPromptConfigurationDTO = akipPromptConfigurationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(akipPromptConfigurationDTO);
    }

    @GetMapping("/akip-prompt-configurations/name/{name}")
    public AkipPromptConfigurationDTO getAkipPromptConfigurationByName(@PathVariable("name") String name) {
        log.debug("REST request to get AkipPromptConfiguration by name : {}", name);
        return akipPromptConfigurationService.findByName(name);
    }

    /**
     * {@code DELETE  /akip-prompt-configurations/:id} : delete the "id" akipPromptConfiguration.
     *
     * @param id the id of the akipPromptConfigurationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/akip-prompt-configurations/{id}")
    public ResponseEntity<Void> deleteAkipPromptConfiguration(@PathVariable("id") Long id) {
        log.debug("REST request to delete AkipPromptConfiguration : {}", id);
        akipPromptConfigurationService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createAlert(HeaderConstants.APPLICATION_NAME, MESSAGE_PROMPT_CONFIGURATION_REMOVED, id.toString()))
            .build();
    }
}
