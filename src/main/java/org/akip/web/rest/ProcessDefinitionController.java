package org.akip.web.rest;

import org.akip.domain.TaskDefinition;
import org.akip.service.*;
import org.akip.service.dto.*;
import org.simpleframework.xml.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing {@link org.akip.domain.ProcessDefinition}.
 */
@RestController
@RequestMapping("/api")
public class ProcessDefinitionController {

    private final Logger log = LoggerFactory.getLogger(ProcessDefinitionController.class);

    private final ProcessDefinitionService processDefinitionService;

    private final ProcessDeploymentService processDeploymentService;

    private final TaskInstanceService taskInstanceService;

    private final TaskDefinitionService taskDefinitionService;

    public ProcessDefinitionController(
            ProcessDefinitionService processDefinitionService,
            ProcessDeploymentService processDeploymentService,
            TaskInstanceService taskInstanceService, TaskDefinitionService taskDefinitionService
    ) {
        this.processDefinitionService = processDefinitionService;
        this.processDeploymentService = processDeploymentService;
        this.taskInstanceService = taskInstanceService;
        this.taskDefinitionService = taskDefinitionService;
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
    public List<TaskDefinition> getTasksDefinition(@PathVariable String bpmnProcessDefinitionId) {
        log.debug("REST request to get TaskInstances of the ProcessDefinition : {}", bpmnProcessDefinitionId);
        return taskDefinitionService.findByProcessDefinition(bpmnProcessDefinitionId);
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
}
