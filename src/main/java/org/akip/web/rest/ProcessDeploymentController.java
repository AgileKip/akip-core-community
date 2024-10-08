package org.akip.web.rest;

import org.akip.domain.ProcessDeployment;
import org.akip.domain.enumeration.ProcessVisibilityType;
import org.akip.exception.BadRequestErrorException;
import org.akip.service.ProcessDeploymentService;
import org.akip.service.dto.ProcessDeploymentBpmnModelDTO;
import org.akip.service.dto.ProcessDeploymentDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Optional;

/**
 * REST controller for managing {@link org.akip.domain.ProcessDeployment}.
 */
@RestController
@RequestMapping("/api")
public class ProcessDeploymentController {

    private static final String MESSAGE_PROPERTIES_SAVED = "Properties Successfully Saved";

    private final Logger log = LoggerFactory.getLogger(ProcessDeploymentController.class);

    private final ProcessDeploymentService processDeploymentService;

    public ProcessDeploymentController(ProcessDeploymentService processDeploymentService) {
        this.processDeploymentService = processDeploymentService;
    }

    /**
     * {@code POST  /process-definitions} : Create a new processDefinition.
     *
     * @param processDeploymentDTO the processDeploymentDTO to deploy.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new processDeploymentDTO, or with status {@code 400 (Bad Request)} if the processDeploymentDTO has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/process-deployment/deploy")
    public ResponseEntity<Void> deploy(@RequestBody ProcessDeploymentDTO processDeploymentDTO) throws URISyntaxException {
        log.debug("REST request to deploy ProcessDeployment : {}", processDeploymentDTO);
        ProcessDeploymentDTO result = processDeploymentService.deploy(processDeploymentDTO);
        if(ProcessVisibilityType.INTERNAL.equals(processDeploymentDTO.getProcessVisibilityType()) && processDeploymentDTO.getTenant() == null){
            throw new BadRequestErrorException("Internal process requires a tenant", "deployInternalProcess", "tenantIsNull");
        }
        return ResponseEntity
                .created(new URI("/api/process-deployment/" + result.getId()))
                .headers(HeaderUtil.createAlert(HeaderConstants.APPLICATION_NAME, buildDeployedMessage(result), result.getCamundaProcessDefinitionId()))
                .build();
    }

    private String buildDeployedMessage(ProcessDeploymentDTO processDeployment) {
        return "Process deployed successfully: " + processDeployment.getCamundaProcessDefinitionId();
    }

    /**
     * {@code GET  /process-definition-deployment/:id} : get the processDeployment.
     *
     * @param id the id of the processDeployment to retrieve.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @GetMapping("/process-deployment/{id}")
    public ProcessDeploymentDTO getProcessDeployment(@PathVariable("id") Long id) {
        log.debug("REST request to get ProcessDeployment : {}", id);
        return processDeploymentService.findOne(id).orElseThrow();
    }

    /**
     * {@code GET  /process-definition-deployment/:id} : get the processDeployment.
     *
     * @param id the id of the processDeployment to retrieve.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @GetMapping("/process-deployment/{id}/bpmnModel")
    public ProcessDeploymentBpmnModelDTO getProcessDeploymentBpmnModel(@PathVariable("id") Long id) {
        log.debug("REST request to get ProcessDeployment : {}", id);
        return processDeploymentService.findBpmnModel(id).orElseThrow();
    }

    /**
     * {@code GET  /process-definition-deployment/:id/active} : active the "id" processDeployment.
     *
     * @param id the id of the processDeployment to inactive.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @GetMapping("/process-deployment/{id}/active")
    public ResponseEntity<Void> activeProcessDeployment(@PathVariable("id") Long id) {
        log.debug("REST request to inactive ProcessDeployment : {}", id);
        processDeploymentService.activate(id);
        return ResponseEntity
            .noContent()
            .build();
    }

    /**
     * {@code GET  /process-definition-deployment/:id/inactive} : inactive the "id" processDeployment.
     *
     * @param id the id of the processDeployment to inactive.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @GetMapping("/process-deployment/{id}/inactive")
    public ResponseEntity<Void> inactiveProcessDeployment(@PathVariable("id") Long id) {
        log.debug("REST request to inactive ProcessDeployment : {}", id);
        processDeploymentService.inactivate(id);
        return ResponseEntity
            .noContent()
            .build();
    }

    @PutMapping("/process-deployment/{id}/properties")
    public ResponseEntity<Void> saveProperties(@PathVariable("id") Long id, @RequestBody Map<String, String> properties) {
        log.debug("REST request to save ProcessDeployment properties: {}", id);
        processDeploymentService.saveProperties(id, properties);
        return ResponseEntity
                .noContent()
                .headers(HeaderUtil.createAlert(HeaderConstants.APPLICATION_NAME, MESSAGE_PROPERTIES_SAVED, id.toString()))
                .build();
    }

    @GetMapping("/process-deployment/{tenantId}/{processDefinitionId}")
    public Optional<ProcessDeployment> getProcessDeployment(@PathVariable Long tenantId, @PathVariable Long processDefinitionId) {
        return processDeploymentService.getProcessDeployment(tenantId, processDefinitionId);
    }

    @GetMapping("/process-deployment-without-tenant/{processDefinitionId}")
    public Optional<ProcessDeployment> getProcessDeploymentWithoutTenant(@PathVariable Long processDefinitionId) {
        return processDeploymentService.getProcessDeploymentWithoutTenant(processDefinitionId);
    }
}
