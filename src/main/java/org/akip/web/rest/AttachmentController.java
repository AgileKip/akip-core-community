package org.akip.web.rest;

import org.akip.service.AttachmentService;
import org.akip.service.dto.AttachmentDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;

import java.net.URISyntaxException;
import java.util.List;

/**
 * REST controller for managing {@link org.akip.domain.Attachment}.
 */
@RestController
@RequestMapping("/api")
public class AttachmentController {

    private final Logger log = LoggerFactory.getLogger(AttachmentController.class);

    private final String ENTITY_NAME = "attachment";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AttachmentService attachmentService;

    public AttachmentController(AttachmentService attachmentService) {
        this.attachmentService = attachmentService;
    }

    @PostMapping("/attachments")
    public ResponseEntity<Void> create(@RequestBody List<AttachmentDTO> attachmentsDTO) throws URISyntaxException {
        log.debug("REST request to save attachments: {}", attachmentsDTO);
        attachmentService.save(attachmentsDTO);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, attachmentsDTO.size() + ""))
            .build();
    }

    @PutMapping("/attachments")
    public ResponseEntity<Void> update(@RequestBody AttachmentDTO attachmentDTO) throws URISyntaxException {
        log.debug("REST request to update attachment: {}", attachmentDTO);
        attachmentService.save(attachmentDTO);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, "")).build();
    }

    @GetMapping("/attachments/{attachmentId}")
    public ResponseEntity<AttachmentDTO> get(@PathVariable Long attachmentId) {
        log.debug("REST request to get attachment: {}", attachmentId);
        AttachmentDTO attachment = attachmentService.get(attachmentId);
        return ResponseEntity.ok().body(attachment);
    }

    @DeleteMapping("/attachments/{attachmentId}")
    public ResponseEntity<Void> delete(@PathVariable Long attachmentId) {
        log.debug("REST request to get attachment: {}", attachmentId);
        attachmentService.delete(attachmentId);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, attachmentId.toString()))
            .build();
    }

    @GetMapping("/{entityName}/{entityId}/attachments")
    public List<AttachmentDTO> getAttachmentsByEntityNameAndEntityId(@PathVariable String entityName, @PathVariable Long entityId) {
        log.debug("REST request to getAttachmentsByEntityNameAndEntitiesIds: entityName={}, entitiesIds={}", entityName, entityId);
        return attachmentService.findByEntityNameAndEntityId(entityName, entityId);
    }

    @GetMapping("/{entityName}/{entityId}/{attachmentTypes}/attachments")
    public List<AttachmentDTO> getAttachmentsByEntityNameAndEntityIdAndTypes(
        @PathVariable String entityName,
        @PathVariable Long entityId,
        @PathVariable List<String> attachmentTypes
    ) {
        log.debug(
            "REST request to getAttachmentsByEntityIdAndEntityNameAndTypes: entityName={}, entityId={}, types={}",
            entityName,
            entityId,
            attachmentTypes
        );
        return attachmentService.findByEntityNameAndEntityIdAndTypes(entityName, entityId, attachmentTypes);
    }
}
