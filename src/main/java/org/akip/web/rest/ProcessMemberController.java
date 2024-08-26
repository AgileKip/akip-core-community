package org.akip.web.rest;

import org.akip.domain.ProcessMember;
import org.akip.service.ProcessMemberService;
import org.akip.service.dto.ProcessMemberDTO;
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
 * REST controller for managing {@link ProcessMember}.
 */
@RestController
@RequestMapping("/api")
public class ProcessMemberController {

    private final Logger log = LoggerFactory.getLogger(ProcessMemberController.class);

    private static final String ENTITY_NAME = "processMember";

    private static final String MESSAGE_PROCESS_MEMBER_UPDATED = "Process Member Successfully Updated";

    private static final String MESSAGE_PROCESS_MEMBER_CREATED = "Process Member Successfully Created";

    private static final String MESSAGE_PROCESS_MEMBER_REMOVED = "Process Member Successfully Removed";


    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProcessMemberService processMemberService;

    public ProcessMemberController(ProcessMemberService processMemberService) {
        this.processMemberService = processMemberService;
    }

    @GetMapping("/process-definition/{processDefinitionId}/members")
    public List<ProcessMemberDTO> getProcessMembers(@PathVariable("processDefinitionId") Long processDefinitionId) throws URISyntaxException {
        log.debug("REST request to save ProcessMember : {}{}", processDefinitionId);
        return processMemberService.getProcessMembers(processDefinitionId);
    }

    @PostMapping("/process-definition/{processDefinitionId}/members")
    public ResponseEntity<ProcessMemberDTO> createProcessMember(@PathVariable("processDefinitionId") Long processDefinitionId, @RequestBody ProcessMemberDTO processMember) throws URISyntaxException {
        log.debug("REST request to save ProcessMember : {}{}", processDefinitionId, processMember.getUsername());
        ProcessMemberDTO result = processMemberService.save(processDefinitionId, processMember);
        return ResponseEntity
            .created(new URI("/api/process-members/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PutMapping("/process-definition/{processDefinitionId}/members")
    public ResponseEntity<ProcessMemberDTO> updateProcessMember(@PathVariable Long processDefinitionId, @RequestBody ProcessMemberDTO processMember) throws URISyntaxException {
        log.debug("REST request to save ProcessMember : {}", processMember.getUsername());
        ProcessMemberDTO result = processMemberService.save(processDefinitionId, processMember);
        return ResponseEntity
                .ok()
                .headers(HeaderUtil.createAlert(HeaderConstants.APPLICATION_NAME, processMember.getId() != null ? MESSAGE_PROCESS_MEMBER_UPDATED : MESSAGE_PROCESS_MEMBER_CREATED, result.getId().toString()))
                .body(result);
    }

    @DeleteMapping("/process-definition/{processDefinitionId}/members/{processMemberId}")
    public ResponseEntity<Void> deleteProcessMember(@PathVariable("processDefinitionId") Long processDefinitionId, @PathVariable("processMemberId") Long processMemberId) {
        log.debug("REST request to delete ProcessMember : {}{}", processDefinitionId, processMemberId);
        processMemberService.delete(processDefinitionId, processMemberId);
        return ResponseEntity
            .noContent()
                .headers(HeaderUtil.createAlert(HeaderConstants.APPLICATION_NAME, MESSAGE_PROCESS_MEMBER_REMOVED, String.valueOf(processDefinitionId)))
                .build();
    }
}
