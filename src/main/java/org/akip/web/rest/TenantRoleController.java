package org.akip.web.rest;

import org.akip.domain.TenantRole;
import org.akip.service.TenantRoleService;
import org.akip.service.dto.TenantRoleDTO;
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
 * REST controller for managing {@link TenantRole}.
 */
@RestController
@RequestMapping("/api")
public class TenantRoleController {

    private final Logger log = LoggerFactory.getLogger(TenantRoleController.class);

    private static final String ENTITY_NAME = "tenantRole";

    private static final String MESSAGE_TENANT_ROLE_UPDATED = "Tenant Role Successfully Updated";

    private static final String MESSAGE_TENANT_ROLE_CREATED = "Tenant Role Successfully Created";

    private static final String MESSAGE_TENANT_ROLE_REMOVED = "Tenant Role Successfully Removed";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TenantRoleService tenantRoleService;

    public TenantRoleController(TenantRoleService tenantRoleService) {
        this.tenantRoleService = tenantRoleService;
    }

    @GetMapping("/tenant/{tenantId}/roles")
    public List<TenantRoleDTO> getTenantRoles(@PathVariable("tenantId") Long tenantId) throws URISyntaxException {
        log.debug("REST request to save TentantRole : {}{}", tenantId);
        return tenantRoleService.getTenantRoles(tenantId);
    }

    @PostMapping("/tenant/{tenantId}/roles")
    public ResponseEntity<TenantRoleDTO> createTenantRole(@PathVariable("tenantId") Long tenantId, @RequestBody TenantRoleDTO tenantRole) throws URISyntaxException {
        log.debug("REST request to save TentantRole : {}{}", tenantId, tenantRole.getName());
        TenantRoleDTO result = tenantRoleService.save(tenantId, tenantRole);
        return ResponseEntity
            .created(new URI("/api/tentant-roles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PutMapping("/tenant/{tenantId}/roles")
    public ResponseEntity<TenantRoleDTO> updateTenantRole(@PathVariable("tenantId") Long tenantId, @RequestBody TenantRoleDTO tenantRole) throws URISyntaxException {
        log.debug("REST request to save ProcessRole : {}", tenantRole.getName());
        TenantRoleDTO result = tenantRoleService.save(tenantId, tenantRole);
        return ResponseEntity
                .ok()
                .headers(HeaderUtil.createAlert(HeaderConstants.APPLICATION_NAME, tenantRole.getId() != null ? MESSAGE_TENANT_ROLE_UPDATED : MESSAGE_TENANT_ROLE_CREATED, result.getId().toString()))
                .body(result);
    }

    @DeleteMapping("/tenant/{tenantId}/roles/{tenantRoleId}")
    public ResponseEntity<Void> deleteTentantRole(@PathVariable("tenantId") Long tenantId, @PathVariable("tenantRoleId") Long tenantRoleId) {
        log.debug("REST request to delete TenantRole : {}{}", tenantId, tenantRoleId);
        tenantRoleService.delete(tenantId, tenantRoleId);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createAlert(HeaderConstants.APPLICATION_NAME, MESSAGE_TENANT_ROLE_REMOVED, String.valueOf(tenantId)))
            .build();
    }
}
