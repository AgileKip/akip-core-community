package org.akip.web.rest;

import org.akip.domain.ProcessRole;
import org.akip.service.ProcessRoleService;
import org.akip.service.dto.ProcessRoleDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/**
 * REST controller for managing {@link ProcessRole}.
 */
@RestController
@RequestMapping("/api")
public class ProcessRoleController {

    private final Logger log = LoggerFactory.getLogger(ProcessRoleController.class);

    private static final String ENTITY_NAME = "processRole";

    private static final String MESSAGE_PROCESS_ROLE_UPDATED = "Process Role Successfully Updated";

    private static final String MESSAGE_PROCESS_ROLE_CREATED = "Process Role Successfully Created";

    private static final String MESSAGE_PROCESS_ROLE_REMOVED = "Process Role Successfully Removed";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProcessRoleService processRoleService;

    public ProcessRoleController(ProcessRoleService processRoleService) {
        this.processRoleService = processRoleService;
    }

    @GetMapping("/process/{processId}/roles")
    public List<ProcessRoleDTO> retrieveProcessRoles(@PathVariable("processId") Long processId) throws URISyntaxException {
        log.debug("REST request to save TentantRole : {}{}", processId);
        return processRoleService.getProcessRoles(processId);
    }

    @PostMapping("/process/{processId}/role")
    public ResponseEntity<ProcessRoleDTO> createProcessRole(@PathVariable("processId") Long processId, @RequestBody ProcessRoleDTO processRole) throws URISyntaxException {
        log.debug("REST request to save TentantRole : {}{}", processId, processRole.getName());
        ProcessRoleDTO result = processRoleService.save(processId, processRole);
        return ResponseEntity
            .created(new URI("/api/tentant-roles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PostMapping("/process/{processId}/role/save")
    public ResponseEntity<ProcessRoleDTO> save(@PathVariable("processId") Long processId, @RequestBody ProcessRoleDTO processRole) throws URISyntaxException {
        log.debug("REST request to save ProcessRole : {}{}", processId, processRole.getName());
        ProcessRoleDTO result = processRoleService.save(processId, processRole);
        return ResponseEntity
                .ok()
                .headers(HeaderUtil.createAlert(HeaderConstants.APPLICATION_NAME, processRole.getId() != null ? MESSAGE_PROCESS_ROLE_UPDATED : MESSAGE_PROCESS_ROLE_CREATED, result.getId().toString()))
                .body(result);
    }

    @DeleteMapping("/process/{processId}/role/{processRoleId}")
    public ResponseEntity<Void> deleteTentantRole(@PathVariable("processId") Long processId, @PathVariable("processRoleId") Long processRoleId) {
        log.debug("REST request to delete ProcessRole : {}{}", processId, processRoleId);
        processRoleService.delete(processId, processRoleId);
        return ResponseEntity
            .noContent()
                .headers(HeaderUtil.createAlert(HeaderConstants.APPLICATION_NAME, MESSAGE_PROCESS_ROLE_REMOVED, String.valueOf(processId)))
            .build();
    }
}
