package org.akip.web.rest;

import org.akip.exception.BadRequestErrorException;
import org.akip.repository.KipAppRepository;
import org.akip.service.KipAppService;
import org.akip.service.dto.KipAppDTO;
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
 * REST controller for managing {@link org.akip.domain.KipApp}.
 */
@RestController
@RequestMapping("/api")
public class KipAppResource {

    private final Logger log = LoggerFactory.getLogger(KipAppResource.class);

    private static final String ENTITY_NAME = "kipApp";
    private static final String MESSAGE_KIPAPP_CREATED = "KipApp Successfully Created";
    private static final String MESSAGE_KIPAPP_UPDATED = "KipApp Successfully Updated";
    private static final String MESSAGE_KIPAPP_REMOVED = "KipApp Successfully Removed";
    private static final String MESSAGE_KIPAPP_ACTIVATED = "KipApp Successfully Activated";
    private static final String MESSAGE_KIPAPP_INACTIVATED = "KipApp Successfully Inactivated";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final KipAppService kipAppService;

    private final KipAppRepository kipAppRepository;

    public KipAppResource(KipAppService kipAppService, KipAppRepository kipAppRepository) {
        this.kipAppService = kipAppService;
        this.kipAppRepository = kipAppRepository;
    }

    /**
     * {@code POST  /kip-apps} : Create a new kipApp.
     *
     * @param kipAppDTO the kipAppDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new kipAppDTO, or with status {@code 400 (Bad Request)} if the kipApp has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/kip-apps")
    public ResponseEntity<KipAppDTO> createKipApp(@RequestBody KipAppDTO kipAppDTO) throws URISyntaxException {
        log.debug("REST request to save KipApp : {}", kipAppDTO);
        if (kipAppDTO.getId() != null) {
            throw new BadRequestErrorException("A new kipApp cannot already have an ID", ENTITY_NAME, "idexists");
        }
        KipAppDTO result = kipAppService.save(kipAppDTO);
        return ResponseEntity
            .created(new URI("/api/kip-apps/" + result.getId()))
            .headers(HeaderUtil.createAlert(HeaderConstants.APPLICATION_NAME, MESSAGE_KIPAPP_CREATED, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /kip-apps/:id} : Updates an existing kipApp.
     *
     * @param id the id of the kipAppDTO to save.
     * @param kipAppDTO the kipAppDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated kipAppDTO,
     * or with status {@code 400 (Bad Request)} if the kipAppDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the kipAppDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/kip-apps/{id}")
    public ResponseEntity<KipAppDTO> updateKipApp(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody KipAppDTO kipAppDTO
    ) throws URISyntaxException {
        log.debug("REST request to update KipApp : {}, {}", id, kipAppDTO);
        if (kipAppDTO.getId() == null) {
            throw new BadRequestErrorException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, kipAppDTO.getId())) {
            throw new BadRequestErrorException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!kipAppRepository.existsById(id)) {
            throw new BadRequestErrorException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        KipAppDTO result = kipAppService.save(kipAppDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createAlert(HeaderConstants.APPLICATION_NAME, MESSAGE_KIPAPP_UPDATED, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /kip-apps} : get all the kipApps.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of kipApps in body.
     */
    @GetMapping("/kip-apps")
    public List<KipAppDTO> getAllKipApps() {
        log.debug("REST request to get all KipApps");
        return kipAppService.findAll();
    }

    /**
     * {@code GET  /kip-apps/:id} : get the "id" kipApp.
     *
     * @param id the id of the kipAppDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the kipAppDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/kip-apps/{id}")
    public ResponseEntity<KipAppDTO> getKipApp(@PathVariable("id") Long id) {
        log.debug("REST request to get KipApp : {}", id);
        Optional<KipAppDTO> kipAppDTO = kipAppService.findOne(id);
        return ResponseUtil.wrapOrNotFound(kipAppDTO);
    }

    /**
     * {@code DELETE  /kip-apps/:id} : delete the "id" kipApp.
     *
     * @param id the id of the kipAppDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/kip-apps/{id}")
    public ResponseEntity<Void> deleteKipApp(@PathVariable("id") Long id) {
        log.debug("REST request to delete KipApp : {}", id);
        kipAppService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createAlert(HeaderConstants.APPLICATION_NAME, MESSAGE_KIPAPP_REMOVED, ""))
            .build();
    }

    @GetMapping("/kip-apps/{id}/activate")
    public ResponseEntity<Void> activateKipApp(@PathVariable("id") Long id) {
        log.debug("REST request to activate KipApp : {}", id);
        kipAppService.activate(id);
        return ResponseEntity
                .noContent()
                .headers(HeaderUtil.createAlert(HeaderConstants.APPLICATION_NAME, MESSAGE_KIPAPP_ACTIVATED, ""))
                .build();
    }

    @GetMapping("/kip-apps/{id}/inactivate")
    public ResponseEntity<Void> inactivateKipApp(@PathVariable("id") Long id) {
        log.debug("REST request to inactivate KipApp : {}", id);
        kipAppService.inactivate(id);
        return ResponseEntity
                .noContent()
                .headers(HeaderUtil.createAlert(HeaderConstants.APPLICATION_NAME, MESSAGE_KIPAPP_INACTIVATED, ""))
                .build();
    }

}
