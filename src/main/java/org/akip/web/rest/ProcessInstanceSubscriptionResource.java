package org.akip.web.rest;

import org.akip.exception.BadRequestErrorException;
import org.akip.repository.ProcessInstanceSubscriptionRepository;
import org.akip.service.ProcessInstanceSubscriptionService;
import org.akip.service.dto.ProcessInstanceSubscriptionDTO;
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
 * REST controller for managing {@link org.akip.domain.ProcessInstanceSubscription}.
 */
@RestController
@RequestMapping("/api")
public class ProcessInstanceSubscriptionResource {

    private final Logger log = LoggerFactory.getLogger(ProcessInstanceSubscriptionResource.class);

    private static final String ENTITY_NAME = "processInstanceSubscription";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProcessInstanceSubscriptionService processInstanceSubscriptionService;

    private final ProcessInstanceSubscriptionRepository processInstanceSubscriptionRepository;

    public ProcessInstanceSubscriptionResource(
        ProcessInstanceSubscriptionService processInstanceSubscriptionService,
        ProcessInstanceSubscriptionRepository processInstanceSubscriptionRepository
    ) {
        this.processInstanceSubscriptionService = processInstanceSubscriptionService;
        this.processInstanceSubscriptionRepository = processInstanceSubscriptionRepository;
    }

    /**
     * {@code POST  /process-instance-subscriptions} : Create a new processInstanceSubscription.
     *
     * @param processInstanceSubscriptionDTO the processInstanceSubscriptionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new processInstanceSubscriptionDTO, or with status {@code 400 (Bad Request)} if the processInstanceSubscription has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/process-instance-subscriptions/{processInstance}")
    public ResponseEntity<ProcessInstanceSubscriptionDTO> createProcessInstanceSubscription(
        @PathVariable Long processInstance,
        @RequestBody ProcessInstanceSubscriptionDTO processInstanceSubscriptionDTO
    ) throws URISyntaxException {
        log.debug("REST request to save ProcessInstanceSubscription : {}", processInstanceSubscriptionDTO);
        if (processInstanceSubscriptionDTO.getId() != null) {
            throw new BadRequestErrorException("A new processInstanceSubscription cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProcessInstanceSubscriptionDTO result = processInstanceSubscriptionService.save(processInstance, processInstanceSubscriptionDTO);
        return ResponseEntity
            .created(new URI("/api/process-instance-subscriptions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /process-instance-subscriptions/:id} : Updates an existing processInstanceSubscription.
     *
     * @param id the id of the processInstanceSubscriptionDTO to save.
     * @param processInstanceSubscriptionDTO the processInstanceSubscriptionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated processInstanceSubscriptionDTO,
     * or with status {@code 400 (Bad Request)} if the processInstanceSubscriptionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the processInstanceSubscriptionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/process-instance-subscriptions/{id}")
    public ResponseEntity<ProcessInstanceSubscriptionDTO> updateProcessInstanceSubscription(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ProcessInstanceSubscriptionDTO processInstanceSubscriptionDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ProcessInstanceSubscription : {}, {}", id, processInstanceSubscriptionDTO);
        if (processInstanceSubscriptionDTO.getId() == null) {
            throw new BadRequestErrorException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, processInstanceSubscriptionDTO.getId())) {
            throw new BadRequestErrorException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!processInstanceSubscriptionRepository.existsById(id)) {
            throw new BadRequestErrorException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ProcessInstanceSubscriptionDTO result = processInstanceSubscriptionService.save(processInstanceSubscriptionDTO);
        return ResponseEntity
            .ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, processInstanceSubscriptionDTO.getId().toString())
            )
            .body(result);
    }

    /**
     * {@code GET  /process-instance-subscriptions} : get all the processInstanceSubscriptions.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of processInstanceSubscriptions in body.
     */
    @GetMapping("/process-instance-subscriptions")
    public List<ProcessInstanceSubscriptionDTO> getAllProcessInstanceSubscriptionsBySubscriberId() {
        log.debug("REST request to get all ProcessInstanceSubscriptions");
        return processInstanceSubscriptionService.findBySubscriberId();
    }

    @GetMapping("/process-instance-subscriptions/processInstanceId/{processInstanceId}")
    public ResponseEntity<ProcessInstanceSubscriptionDTO> getByProcessInstanceId(
        @PathVariable Long processInstanceId
    ) {
        log.debug("REST request to get ProcessInstanceSubscription: {} ", processInstanceId);
        Optional<ProcessInstanceSubscriptionDTO> processInstanceSubscriptionDTO = processInstanceSubscriptionService.findByProcessInstanceId(
            processInstanceId
        );
        return ResponseUtil.wrapOrNotFound(processInstanceSubscriptionDTO);
    }
}
