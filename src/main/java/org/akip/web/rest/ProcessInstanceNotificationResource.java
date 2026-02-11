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
import tech.jhipster.web.util.ResponseUtil;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
     * {@code GET  /process-instance-notifications/:id} : get the "id" processInstanceNotification.
     *
     * @param id the id of the processInstanceNotificationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the processInstanceNotificationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/process-instance-notifications/{id}/view")
    public ResponseEntity<ProcessInstanceNotificationDTO> getProcessInstanceNotification(@PathVariable Long id) {
        log.debug("REST request to get ProcessInstanceNotification : {}", id);
        Optional<ProcessInstanceNotificationDTO> processInstanceNotificationDTO = processInstanceNotificationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(processInstanceNotificationDTO);
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
