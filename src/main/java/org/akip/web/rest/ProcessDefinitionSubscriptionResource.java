package org.akip.web.rest;

import org.akip.exception.BadRequestErrorException;
import org.akip.repository.ProcessDefinitionSubscriptionRepository;
import org.akip.service.ProcessDefinitionSubscriptionService;
import org.akip.service.dto.ProcessDefinitionSubscriptionDTO;
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
 * REST controller for managing {@link org.akip.domain.ProcessDefinitionSubscription}.
 */
@RestController
@RequestMapping("/api")
public class ProcessDefinitionSubscriptionResource {

    private final Logger log = LoggerFactory.getLogger(ProcessDefinitionSubscriptionResource.class);

    private static final String ENTITY_NAME = "processDefinitionSubscription";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProcessDefinitionSubscriptionService processDefinitionSubscriptionService;

    private final ProcessDefinitionSubscriptionRepository processDefinitionSubscriptionRepository;

    public ProcessDefinitionSubscriptionResource(
        ProcessDefinitionSubscriptionService processDefinitionSubscriptionService,
        ProcessDefinitionSubscriptionRepository processDefinitionSubscriptionRepository
    ) {
        this.processDefinitionSubscriptionService = processDefinitionSubscriptionService;
        this.processDefinitionSubscriptionRepository = processDefinitionSubscriptionRepository;
    }

    /**
     * {@code POST  /process-definition-subscriptions} : Create a new processDefinitionSubscription.
     *
     * @param processDefinitionSubscriptionDTO the processDefinitionSubscriptionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new processDefinitionSubscriptionDTO, or with status {@code 400 (Bad Request)} if the processDefinitionSubscription has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/process-definition-subscriptions/{processDefinitionId}")
    public ResponseEntity<ProcessDefinitionSubscriptionDTO> createProcessDefinitionSubscription(
        @PathVariable String processDefinitionId,
        @RequestBody ProcessDefinitionSubscriptionDTO processDefinitionSubscriptionDTO
    ) throws URISyntaxException {
        log.debug("REST request to save ProcessDefinitionSubscription : {}", processDefinitionSubscriptionDTO);
        if (processDefinitionSubscriptionDTO.getId() != null) {
            throw new BadRequestErrorException("A new processDefinitionSubscription cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProcessDefinitionSubscriptionDTO result = processDefinitionSubscriptionService.save(
            processDefinitionId,
            processDefinitionSubscriptionDTO
        );
        return ResponseEntity
            .created(new URI("/api/process-definition-subscriptions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /process-definition-subscriptions/:id} : Updates an existing processDefinitionSubscription.
     *
     * @param id the id of the processDefinitionSubscriptionDTO to save.
     * @param processDefinitionSubscriptionDTO the processDefinitionSubscriptionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated processDefinitionSubscriptionDTO,
     * or with status {@code 400 (Bad Request)} if the processDefinitionSubscriptionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the processDefinitionSubscriptionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/process-definition-subscriptions/{id}")
    public ResponseEntity<ProcessDefinitionSubscriptionDTO> updateProcessDefinitionSubscription(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ProcessDefinitionSubscriptionDTO processDefinitionSubscriptionDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ProcessDefinitionSubscription : {}, {}", id, processDefinitionSubscriptionDTO);
        if (processDefinitionSubscriptionDTO.getId() == null) {
            throw new BadRequestErrorException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, processDefinitionSubscriptionDTO.getId())) {
            throw new BadRequestErrorException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!processDefinitionSubscriptionRepository.existsById(id)) {
            throw new BadRequestErrorException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ProcessDefinitionSubscriptionDTO result = processDefinitionSubscriptionService.save(processDefinitionSubscriptionDTO);
        return ResponseEntity
            .ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, processDefinitionSubscriptionDTO.getId().toString())
            )
            .body(result);
    }

    @GetMapping("/process-definition-subscriptions/bpmnProcessDefinitionId/{bpmnProcessDefinitionId}")
    public ResponseEntity<ProcessDefinitionSubscriptionDTO> getBySubscriberIdAndBpmnProcessDefinitionId(
        @PathVariable String bpmnProcessDefinitionId
    ) {
        log.debug("REST request to get ProcessInstanceSubscription : {}", bpmnProcessDefinitionId);
        Optional<ProcessDefinitionSubscriptionDTO> processDefinitionSubscription = processDefinitionSubscriptionService.findBySubscriberIdAndBpmnProcessDefinitionId(
            bpmnProcessDefinitionId
        );
        return ResponseUtil.wrapOrNotFound(processDefinitionSubscription);
    }

    /**
     * {@code GET  /process-definition-subscriptions/:id} : get the "id" processDefinitionSubscription.
     *
     * @param id the id of the processDefinitionSubscriptionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the processDefinitionSubscriptionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/process-definition-subscriptions/{id}")
    public ResponseEntity<ProcessDefinitionSubscriptionDTO> getProcessDefinitionSubscription(@PathVariable Long id) {
        log.debug("REST request to get ProcessDefinitionSubscription : {}", id);
        Optional<ProcessDefinitionSubscriptionDTO> processDefinitionSubscriptionDTO = processDefinitionSubscriptionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(processDefinitionSubscriptionDTO);
    }

    /**
     * {@code DELETE  /process-definition-subscriptions/:id} : delete the "id" processDefinitionSubscription.
     *
     * @param id the id of the processDefinitionSubscriptionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/process-definition-subscriptions/{id}")
    public ResponseEntity<Void> deleteProcessDefinitionSubscription(@PathVariable Long id) {
        log.debug("REST request to delete ProcessDefinitionSubscription : {}", id);
        processDefinitionSubscriptionService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
