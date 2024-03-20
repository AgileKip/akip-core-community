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

    @GetMapping("/process/{processId}/members")
    public List<ProcessMemberDTO> retrieveProcessMembers(@PathVariable("processId") Long processId) throws URISyntaxException {
        log.debug("REST request to save ProcessMember : {}{}", processId);
        return processMemberService.getProcessMembers(processId);
    }

    @PostMapping("/process/{processId}/member")
    public ResponseEntity<ProcessMemberDTO> createProcessMember(@PathVariable("processId") Long processId, @RequestBody ProcessMemberDTO processMember) throws URISyntaxException {
        log.debug("REST request to save ProcessMember : {}{}", processId, processMember.getUsername());
        ProcessMemberDTO result = processMemberService.save(processId, processMember);
        return ResponseEntity
            .created(new URI("/api/process-members/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PostMapping("/process/{processId}/member/save")
    public ResponseEntity<ProcessMemberDTO> save(@PathVariable Long processId, @RequestBody ProcessMemberDTO processMember) throws URISyntaxException {
        log.debug("REST request to save ProcessMember : {}", processMember.getUsername());
        ProcessMemberDTO result = processMemberService.save(processId, processMember);
        return ResponseEntity
                .ok()
                .headers(HeaderUtil.createAlert(HeaderConstants.APPLICATION_NAME, processMember.getId() != null ? MESSAGE_PROCESS_MEMBER_UPDATED : MESSAGE_PROCESS_MEMBER_CREATED, result.getId().toString()))
                .body(result);
    }

    @DeleteMapping("/process/{processId}/member/{processMemberId}")
    public ResponseEntity<Void> deleteProcessMember(@PathVariable("processId") Long processId, @PathVariable("processMemberId") Long processMemberId) {
        log.debug("REST request to delete ProcessMember : {}{}", processId, processMemberId);
        processMemberService.delete(processId, processMemberId);
        return ResponseEntity
            .noContent()
                .headers(HeaderUtil.createAlert(HeaderConstants.APPLICATION_NAME, MESSAGE_PROCESS_MEMBER_REMOVED, String.valueOf(processId)))
                .build();
    }
}
