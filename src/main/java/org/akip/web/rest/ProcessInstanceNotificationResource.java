package org.akip.web.rest;

import org.akip.exception.BadRequestErrorException;
import org.akip.repository.ProcessInstanceNotificationRepository;
import org.akip.service.ProcessInstanceNotificationService;
import org.akip.service.dto.ProcessInstanceNotificationDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;

/**
 * REST controller for managing {@link org.akip.domain.ProcessInstanceNotification}.
 */
@RestController
@RequestMapping("/api")
public class ProcessInstanceNotificationResource {

    private final Logger log = LoggerFactory.getLogger(ProcessInstanceNotificationResource.class);

    private static final String ENTITY_NAME = "processInstanceNotification";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProcessInstanceNotificationService processInstanceNotificationService;

    private final ProcessInstanceNotificationRepository processInstanceNotificationRepository;

    public ProcessInstanceNotificationResource(
        ProcessInstanceNotificationService processInstanceNotificationService,
        ProcessInstanceNotificationRepository processInstanceNotificationRepository
    ) {
        this.processInstanceNotificationService = processInstanceNotificationService;
        this.processInstanceNotificationRepository = processInstanceNotificationRepository;
    }

    /**
     * {@code POST  /process-instance-notifications} : Create a new processInstanceNotification.
     *
     * @param processInstanceNotificationDTO the processInstanceNotificationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new processInstanceNotificationDTO, or with status {@code 400 (Bad Request)} if the processInstanceNotification has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/process-instance-notifications")
    public ResponseEntity<ProcessInstanceNotificationDTO> createProcessInstanceNotification(
        @RequestBody ProcessInstanceNotificationDTO processInstanceNotificationDTO
    ) throws URISyntaxException {
        log.debug("REST request to save ProcessInstanceNotification : {}", processInstanceNotificationDTO);
        if (processInstanceNotificationDTO.getId() != null) {
            throw new BadRequestErrorException("A new processInstanceNotification cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProcessInstanceNotificationDTO result = processInstanceNotificationService.save(processInstanceNotificationDTO);
        return ResponseEntity
            .created(new URI("/api/process-instance-notifications/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /process-instance-notifications/:id} : Updates an existing processInstanceNotification.
     *
     * @param id the id of the processInstanceNotificationDTO to save.
     * @param processInstanceNotificationDTO the processInstanceNotificationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated processInstanceNotificationDTO,
     * or with status {@code 400 (Bad Request)} if the processInstanceNotificationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the processInstanceNotificationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/process-instance-notifications/{id}")
    public ResponseEntity<ProcessInstanceNotificationDTO> updateProcessInstanceNotification(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ProcessInstanceNotificationDTO processInstanceNotificationDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ProcessInstanceNotification : {}, {}", id, processInstanceNotificationDTO);
        if (processInstanceNotificationDTO.getId() == null) {
            throw new BadRequestErrorException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, processInstanceNotificationDTO.getId())) {
            throw new BadRequestErrorException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!processInstanceNotificationRepository.existsById(id)) {
            throw new BadRequestErrorException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ProcessInstanceNotificationDTO result = processInstanceNotificationService.save(processInstanceNotificationDTO);
        return ResponseEntity
            .ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, processInstanceNotificationDTO.getId().toString())
            )
            .body(result);
    }
    /**
     * {@code GET  /process-instance-notifications/:id} : get the "id" processInstanceNotification.
     *
     * @param id the id of the processInstanceNotificationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the processInstanceNotificationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/process-instance-notifications/read-notification/{id}")
    public ProcessInstanceNotificationDTO getReadNotification(@PathVariable Long id) {
        log.debug("REST request to get ReadNotification : {}", id);
        return processInstanceNotificationService.readNotification(id);
    }

    /**
     * {@code GET  /process-instance-notifications/:id} : get the "id" processInstanceNotification.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the processInstanceNotificationDTO, or with status
     * {@code 404 (Not Found)}.
     */
    @GetMapping("/process-instance-notifications/my-last-notifications/")
    public List<ProcessInstanceNotificationDTO> findTop6BySubscriberIdOrderByIdDesc() {
        log.debug("REST request to get findTop6BySubscriberIdOrderByIdDesc :");
        return processInstanceNotificationService.findTop6BySubscriberIdOrderByIdDesc();
    }

    /**
     * {@code GET  /process-instance-notifications/:id} : get the "id" processInstanceNotification.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the processInstanceNotificationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/process-instance-notifications/count-my-unread-notifications")
    public Long countBySubscriberIdAndStatus() {
        log.debug("REST request to get countBySubscriberIdAndStatus");
        return processInstanceNotificationService.countBySubscriberIdAndStatus();
    }

    /**
     * {@code DELETE  /process-instance-notifications/:id} : delete the "id" processInstanceNotification.
     *
     * @param id the id of the processInstanceNotificationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/process-instance-notifications/{id}")
    public ResponseEntity<Void> deleteProcessInstanceNotification(@PathVariable Long id) {
        log.debug("REST request to delete ProcessInstanceNotification : {}", id);
        processInstanceNotificationService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
