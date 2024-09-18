package org.akip.web.rest;

import org.akip.service.TenantMemberService;
import org.akip.service.dto.TenantMemberDTO;
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
 * REST controller for managing {@link TenantMember}.
 */
@RestController
@RequestMapping("/api")
public class TenantMemberController {

    private final Logger log = LoggerFactory.getLogger(TenantMemberController.class);

    private static final String ENTITY_NAME = "tenantMember";

    private static final String MESSAGE_TENANT_MEMBER_UPDATED = "Tenant Member Successfully Updated";

    private static final String MESSAGE_TENANT_MEMBER_CREATED = "Tenant Member Successfully Created";

    private static final String MESSAGE_TENANT_MEMBER_REMOVED = "Tenant Member Successfully Removed";


    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TenantMemberService tenantMemberService;

    public TenantMemberController(TenantMemberService tenantMemberService) {
        this.tenantMemberService = tenantMemberService;
    }

    @GetMapping("/tenant/{tenantId}/members")
    public List<TenantMemberDTO> getTenantMembers(@PathVariable("tenantId") Long tenantId) throws URISyntaxException {
        log.debug("REST request to save TentantMember : {}{}", tenantId);
        return tenantMemberService.getTenantMembers(tenantId);
    }

    @PostMapping("/tenant/{tenantId}/members")
    public ResponseEntity<TenantMemberDTO> createTenantMember(@PathVariable("tenantId") Long tenantId, @RequestBody TenantMemberDTO tenantMember) throws URISyntaxException {
        log.debug("REST request to save TentantMember : {}{}", tenantId, tenantMember.getUsername());
        TenantMemberDTO result = tenantMemberService.save(tenantId, tenantMember);
        return ResponseEntity
            .created(new URI("/api/tentant-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PutMapping("/tenant/{tenantId}/members")
    public ResponseEntity<TenantMemberDTO> updateTenantMember(@PathVariable Long tenantId, @RequestBody TenantMemberDTO tenantMember) throws URISyntaxException {
        log.debug("REST request to save ProcessMember : {}", tenantMember.getUsername());
        TenantMemberDTO result = tenantMemberService.save(tenantId, tenantMember);
        return ResponseEntity
                .ok()
                .headers(HeaderUtil.createAlert(HeaderConstants.APPLICATION_NAME, tenantMember.getId() != null ? MESSAGE_TENANT_MEMBER_UPDATED : MESSAGE_TENANT_MEMBER_CREATED, result.getId().toString()))
                .body(result);
    }

    @DeleteMapping("/tenant/{tenantId}/members/{tenantMemberId}")
    public ResponseEntity<Void> deleteTenantMember(@PathVariable("tenantId") Long tenantId, @PathVariable("tenantMemberId") Long tenantMemberId) {
        log.debug("REST request to delete TenantMember : {}{}", tenantId, tenantMemberId);
        tenantMemberService.delete(tenantId, tenantMemberId);
        return ResponseEntity
            .noContent()
                .headers(HeaderUtil.createAlert(HeaderConstants.APPLICATION_NAME, MESSAGE_TENANT_MEMBER_REMOVED, String.valueOf(tenantId)))
            .build();
    }
}
