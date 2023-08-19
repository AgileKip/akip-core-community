package org.akip.web.rest;

import org.akip.exception.BadRequestErrorException;
import org.akip.repository.AkipTaskInstanceRankingContextConfigRepository;
import org.akip.service.AkipTaskInstanceRankingContextConfigService;
import org.akip.service.dto.AkipTaskInstanceRankingContextConfigDTO;
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
 * REST controller for managing {@link org.akip.domain.AkipTaskInstanceRankingContextConfig}.
 */
@RestController
@RequestMapping("/api")
public class AkipTaskInstanceRankingContextConfigResource {

    private final Logger log = LoggerFactory.getLogger(AkipTaskInstanceRankingContextConfigResource.class);

    private static final String ENTITY_NAME = "akipTaskInstanceRankingContextConfig";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AkipTaskInstanceRankingContextConfigService akipTaskInstanceRankingContextConfigService;

    private final AkipTaskInstanceRankingContextConfigRepository akipTaskInstanceRankingContextConfigRepository;

    public AkipTaskInstanceRankingContextConfigResource(
        AkipTaskInstanceRankingContextConfigService akipTaskInstanceRankingContextConfigService,
        AkipTaskInstanceRankingContextConfigRepository akipTaskInstanceRankingContextConfigRepository
    ) {
        this.akipTaskInstanceRankingContextConfigService = akipTaskInstanceRankingContextConfigService;
        this.akipTaskInstanceRankingContextConfigRepository = akipTaskInstanceRankingContextConfigRepository;
    }

    /**
     * {@code POST  /akip-task-instance-ranking-context-configs} : Create a new akipTaskInstanceRankingContextConfig.
     *
     * @param akipTaskInstanceRankingContextConfigDTO the akipTaskInstanceRankingContextConfigDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new akipTaskInstanceRankingContextConfigDTO, or with status {@code 400 (Bad Request)} if the akipTaskInstanceRankingContextConfig has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/akip-task-instance-ranking-context-configs")
    public ResponseEntity<AkipTaskInstanceRankingContextConfigDTO> createAkipTaskInstanceRankingContextConfig(
        @RequestBody AkipTaskInstanceRankingContextConfigDTO akipTaskInstanceRankingContextConfigDTO
    ) throws URISyntaxException {
        log.debug("REST request to save AkipTaskInstanceRankingContextConfig : {}", akipTaskInstanceRankingContextConfigDTO);
        if (akipTaskInstanceRankingContextConfigDTO.getId() != null) {
            throw new BadRequestErrorException("A new akipTaskInstanceRankingContextConfig cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AkipTaskInstanceRankingContextConfigDTO result = akipTaskInstanceRankingContextConfigService.save(
            akipTaskInstanceRankingContextConfigDTO
        );
        return ResponseEntity
            .created(new URI("/api/akip-task-instance-ranking-context-configs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /akip-task-instance-ranking-context-configs/:id} : Updates an existing akipTaskInstanceRankingContextConfig.
     *
     * @param id the id of the akipTaskInstanceRankingContextConfigDTO to save.
     * @param akipTaskInstanceRankingContextConfigDTO the akipTaskInstanceRankingContextConfigDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated akipTaskInstanceRankingContextConfigDTO,
     * or with status {@code 400 (Bad Request)} if the akipTaskInstanceRankingContextConfigDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the akipTaskInstanceRankingContextConfigDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/akip-task-instance-ranking-context-configs/{id}")
    public ResponseEntity<AkipTaskInstanceRankingContextConfigDTO> updateAkipTaskInstanceRankingContextConfig(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AkipTaskInstanceRankingContextConfigDTO akipTaskInstanceRankingContextConfigDTO
    ) throws URISyntaxException {
        log.debug("REST request to update AkipTaskInstanceRankingContextConfig : {}, {}", id, akipTaskInstanceRankingContextConfigDTO);
        if (akipTaskInstanceRankingContextConfigDTO.getId() == null) {
            throw new BadRequestErrorException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, akipTaskInstanceRankingContextConfigDTO.getId())) {
            throw new BadRequestErrorException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!akipTaskInstanceRankingContextConfigRepository.existsById(id)) {
            throw new BadRequestErrorException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AkipTaskInstanceRankingContextConfigDTO result = akipTaskInstanceRankingContextConfigService.save(
            akipTaskInstanceRankingContextConfigDTO
        );
        return ResponseEntity
            .ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(
                    applicationName,
                    true,
                    ENTITY_NAME,
                    akipTaskInstanceRankingContextConfigDTO.getId().toString()
                )
            )
            .body(result);
    }

    /**
     * {@code GET  /akip-task-instance-ranking-context-configs} : get all the akipTaskInstanceRankingContextConfigs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of akipTaskInstanceRankingContextConfigs in body.
     */
    @GetMapping("/akip-task-instance-ranking-context-configs")
    public List<AkipTaskInstanceRankingContextConfigDTO> getAllAkipTaskInstanceRankingContextConfigs() {
        log.debug("REST request to get all AkipTaskInstanceRankingContextConfigs");
        return akipTaskInstanceRankingContextConfigService.findAll();
    }

    /**
     * {@code GET  /akip-task-instance-ranking-context-configs/:id} : get the "id" akipTaskInstanceRankingContextConfig.
     *
     * @param id the id of the akipTaskInstanceRankingContextConfigDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the akipTaskInstanceRankingContextConfigDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/akip-task-instance-ranking-context-configs/{id}")
    public ResponseEntity<AkipTaskInstanceRankingContextConfigDTO> getAkipTaskInstanceRankingContextConfig(@PathVariable Long id) {
        log.debug("REST request to get AkipTaskInstanceRankingContextConfig : {}", id);
        Optional<AkipTaskInstanceRankingContextConfigDTO> akipTaskInstanceRankingContextConfigDTO = akipTaskInstanceRankingContextConfigService.findOne(
            id
        );
        return ResponseUtil.wrapOrNotFound(akipTaskInstanceRankingContextConfigDTO);
    }

    /**
     * {@code DELETE  /akip-task-instance-ranking-context-configs/:id} : delete the "id" akipTaskInstanceRankingContextConfig.
     *
     * @param id the id of the akipTaskInstanceRankingContextConfigDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/akip-task-instance-ranking-context-configs/{id}")
    public ResponseEntity<Void> deleteAkipTaskInstanceRankingContextConfig(@PathVariable Long id) {
        log.debug("REST request to delete AkipTaskInstanceRankingContextConfig : {}", id);
        akipTaskInstanceRankingContextConfigService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
