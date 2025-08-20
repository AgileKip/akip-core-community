package org.akip.web.rest;

import org.akip.domain.ProcessDefinition;
import org.akip.domain.TaskDefinition;
import org.akip.domain.enumeration.StatusProcessDefinition;
import org.akip.service.*;
import org.akip.service.dto.*;
import org.simpleframework.xml.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;

import java.util.List;

/**
 * REST controller for managing {@link org.akip.domain.ProcessDefinition}.
 */
@RestController
@RequestMapping("/api")
public class ProcessDefinitionController {

    private final Logger log = LoggerFactory.getLogger(ProcessDefinitionController.class);

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProcessDefinitionService processDefinitionService;

    private final ProcessDeploymentService processDeploymentService;

    private final TaskInstanceService taskInstanceService;

    private final TaskDefinitionService taskDefinitionService;

    private final FormDefinitionService formDefinitionService;

    public ProcessDefinitionController(
            ProcessDefinitionService processDefinitionService,
            ProcessDeploymentService processDeploymentService,
            TaskInstanceService taskInstanceService, TaskDefinitionService taskDefinitionService, FormDefinitionService formDefinitionService
    ) {
        this.processDefinitionService = processDefinitionService;
        this.processDeploymentService = processDeploymentService;
        this.taskInstanceService = taskInstanceService;
        this.taskDefinitionService = taskDefinitionService;
        this.formDefinitionService = formDefinitionService;
    }

    /**
     * {@code GET  /process-definitions} : get all the processDefinitions.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of processDefinitions in body.
     */
    @GetMapping("/process-definitions")
    public List<ProcessDefinitionDTO> getAllProcessDefinitions() {
        log.debug("REST request to get all ProcessDefinitions");
        return processDefinitionService.findAll();
    }

    /**
     * {@code GET  /process-definitions/:id} : get the "id" processDefinition.
     *
     * @param idOrBpmnProcessDefinitionId the id of the processDefinitionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the processDefinitionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/process-definitions/{idOrBpmnProcessDefinitionId}")
    public ProcessDefinitionDTO getProcessDefinition(@PathVariable("idOrBpmnProcessDefinitionId") String idOrBpmnProcessDefinitionId) {
        log.debug("REST request to get ProcessDefinition : {}", idOrBpmnProcessDefinitionId);
        return processDefinitionService
                .findByIdOrBpmnProcessDefinitionId(idOrBpmnProcessDefinitionId)
                .orElseThrow();
    }

    /**
     * {@code GET  /process-definitions/:bpmnProcessDefinitionId} : get the "bpmnProcessDefinitionId" processDefinition.
     *
     * @param idOrBpmnProcessDefinitionId the id of the processDefinitionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the processDefinitionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/process-definitions/{idOrBpmnProcessDefinitionId}/view")
    public ProcessDefinitionDTO getByBpmnProcessDefinitionId(@PathVariable("idOrBpmnProcessDefinitionId") String idOrBpmnProcessDefinitionId) {
        log.debug("REST request to get ProcessDefinitionByBpmnProcessDefinitionId : {}", idOrBpmnProcessDefinitionId);
        return processDefinitionService
                .findByIdOrBpmnProcessDefinitionId(idOrBpmnProcessDefinitionId)
                .orElseThrow();
    }

    /**
     * {@code GET  /process-definitions/:bpmnProcessDefinitionId} : get the "bpmnProcessDefinitionId" processDefinition.
     *
     * @param idOrBpmnProcessDefinitionId the id of the processDefinitionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the processDefinitionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/process-definitions/{idOrBpmnProcessDefinitionId}/instances")
    public List<ProcessInstanceDTO> getInstances(@PathVariable("idOrBpmnProcessDefinitionId") String idOrBpmnProcessDefinitionId) {
        log.debug("REST request to get instances by ProcessDefinition : {}", idOrBpmnProcessDefinitionId);
        return processDefinitionService
                .findByProcessDefinition(idOrBpmnProcessDefinitionId);
    }

    /**
     * {@code GET  /process-definitions/:idOrBpmnProcessDefinitionId/deployments} : get the "idOrBpmnProcessDefinitionId" processDefinition.
     *
     * @param idOrBpmnProcessDefinitionId the id of the processDefinitionDTO owner of the ProcessDeployments.
     * @return the list of processInstanceDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/process-definitions/{idOrBpmnProcessDefinitionId}/deployments")
    public List<ProcessDeploymentDTO> getProcessDeployments(@PathVariable("idOrBpmnProcessDefinitionId") String idOrBpmnProcessDefinitionId) {
        log.debug("REST request to get ProcessDeployments of the ProcessDefinition : {}", idOrBpmnProcessDefinitionId);
        return processDeploymentService.findByProcessDefinition(idOrBpmnProcessDefinitionId);
    }


    /**
     * {@code GET  /process-definition/:idOrBpmnProcessDefinitionId/tasks} : get the "id" processInstance.
     *
     * @param idOrBpmnProcessDefinitionId the id or bpmnProcessDefinitionId of the processDefinition owner of the TaskInstances.
     * @return the list of processInstanceDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/process-definition/{idOrBpmnProcessDefinitionId}/tasks")
    public List<TaskInstanceDTO> getTaskInstances(@PathVariable("idOrBpmnProcessDefinitionId") String idOrBpmnProcessDefinitionId) {
        log.debug("REST request to get TaskInstances of the ProcessDefinition : {}", idOrBpmnProcessDefinitionId);
        return taskInstanceService.findByProcessDefinition(idOrBpmnProcessDefinitionId);
    }

    @GetMapping("/process-definitions/{bpmnProcessDefinitionId}/tasks-definitions")
    public List<TaskDefinitionDTO> getTasksDefinition(@PathVariable("bpmnProcessDefinitionId") String bpmnProcessDefinitionId) {
        log.debug("REST request to get TaskInstances of the ProcessDefinition : {}", bpmnProcessDefinitionId);
        return taskDefinitionService.findByProcessDefinition(bpmnProcessDefinitionId);
    }


    @GetMapping("/process-definitions/{bpmnProcessDefinitionId}/forms-definitions")
    public List<FormDefinitionDTO> getFormsDefinition(@PathVariable("bpmnProcessDefinitionId") String bpmnProcessDefinitionId) {
        log.debug("REST request to get TaskInstances of the ProcessDefinition : {}", bpmnProcessDefinitionId);
        return formDefinitionService.findByProcessDefinitionId(bpmnProcessDefinitionId);
    }

    @GetMapping("/process-definitions/{bpmnProcessDefinitionId}/domain-entity-definition")
    public DomainEntityDefinitionDTO getDomainEntityDefinition(@PathVariable("bpmnProcessDefinitionId") String bpmnProcessDefinitionId) {
        log.debug("REST request to get DomainEntityDefinition of the ProcessDefinition : {}", bpmnProcessDefinitionId);
        return processDefinitionService.findDomainEntityDefinitionByProcessDefinitionId(bpmnProcessDefinitionId);
    }

    @PostMapping("/process-definitions/{bpmnProcessDefinitionId}/domain-entity-definition")
    public ResponseEntity<Void>  setDomainEntityDefinition(@PathVariable("bpmnProcessDefinitionId") String bpmnProcessDefinitionId, @RequestBody DomainEntityDefinitionDTO domainEntityDefinition) {
        log.debug("REST request to set DomainEntityDefinition of the ProcessDefinition : {}", bpmnProcessDefinitionId);
        processDefinitionService.setDomainEntityDefinition(domainEntityDefinition, bpmnProcessDefinitionId);
        return ResponseEntity.noContent()
                .headers(HeaderUtil.createAlert(applicationName, "domainEntityDefinition.updated", bpmnProcessDefinitionId))
                .build();
    }

    @DeleteMapping("/process-definitions/{bpmnProcessDefinitionId}/domain-entity-definition")
    public ResponseEntity<Void> removeDomainEntityDefinition(@PathVariable("bpmnProcessDefinitionId") String bpmnProcessDefinitionId) {
        log.debug("REST request to remove DomainEntityDefinition of the ProcessDefinition : {}", bpmnProcessDefinitionId);
        processDefinitionService.removeDomainEntityDefinition(bpmnProcessDefinitionId);
        return ResponseEntity.noContent()
                .headers(HeaderUtil.createAlert(applicationName, "domainEntityDefinition.deleted", bpmnProcessDefinitionId))
                .build();
    }

    /**
     * {@code DELETE  /process-definitions/:id} : delete the "id" processDefinition.
     *
     * @param id the id of the processDefinitionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/process-definitions/{id}")
    public ResponseEntity<Void> deleteProcessDefinition(@PathVariable("id") Long id) {
        log.debug("REST request to delete ProcessDefinition : {}", id);
        processDefinitionService.delete(id);
        return ResponseEntity
            .noContent()
            .build();
    }

    @PostMapping("/suspend/{processDefinitionKey}")
    public ResponseEntity<String> suspendProcessDefinition(@PathVariable("processDefinitionKey") String processDefinitionKey) {
        processDefinitionService.suspendProcess(processDefinitionKey);
        return ResponseEntity.ok("Process definition suspended successfully");
    }

    @PostMapping("/activate/{processDefinitionKey}")
    public ResponseEntity<String> activateProcessDefinition(@PathVariable("processDefinitionKey") String processDefinitionKey) {
        processDefinitionService.activateProcess(processDefinitionKey);
        return ResponseEntity.ok("Process definition activated successfully");
    }


}
