package org.akip.web.rest;


import org.akip.service.ProcessInstanceService;
import org.akip.service.TaskInstanceService;
import org.akip.service.dto.ProcessInstanceBpmnModelDTO;
import org.akip.service.dto.ProcessInstanceDTO;
import org.akip.service.dto.TaskInstanceDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/**
 * REST controller for managing {@link org.akip.domain.ProcessInstance}.
 */
@RestController
@RequestMapping("/api")
public class ProcessInstanceController {

    private final Logger log = LoggerFactory.getLogger(ProcessInstanceController.class);

    private final ProcessInstanceService processInstanceService;

    private final TaskInstanceService taskInstanceService;

    public ProcessInstanceController(ProcessInstanceService processInstanceService, TaskInstanceService taskInstanceService) {
        this.processInstanceService = processInstanceService;
        this.taskInstanceService = taskInstanceService;
    }

    /**
     * {@code POST  /process-definitions} : Create a new processDefinition.
     *
     * @param processInstanceDTO the processInstanceDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new processInstanceDTO
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/process-instances")
    public ResponseEntity<ProcessInstanceDTO> create(@RequestBody ProcessInstanceDTO processInstanceDTO) throws URISyntaxException {
        log.debug("REST request to init a ProcessDefinition : {}", processInstanceDTO.getProcessDefinition());
        ProcessInstanceDTO result = processInstanceService.create(processInstanceDTO);
        return ResponseEntity
                .created(new URI("/api/process-definitions/" + result.getId()))
                .headers(HeaderUtil.createAlert(HeaderConstants.APPLICATION_NAME, buildInitMessage(result), result.getCamundaProcessDefinitionId()))
                .body(result);
    }

    private String buildInitMessage(ProcessInstanceDTO processInstance) {
        return "Process initialized successfully: " + processInstance.getCamundaProcessInstanceId();
    }

    /**
     * {@code GET  /process-instances/:id/tasks} : get the "id" processInstance.
     *
     * @param id the id of the processInstance owner of the TaskInstances.
     * @return the list of processInstanceDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/process-instances/{id}/tasks")
    public List<TaskInstanceDTO> getTaskInstances(@PathVariable("id") Long id) {
        log.debug("REST request to get TaskInstances of ProcessInstance : {}", id);
        return taskInstanceService.findByProcessInstance(id);
    }

    /**
     * {@code GET  /process-instances/:id} : get the "id" processInstance.
     *
     * @param id the id of the processInstanceDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the processInstanceDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/process-instances/{id}")
    public ProcessInstanceDTO getProcessInstance(@PathVariable("id") Long id) {
        log.debug("REST request to get ProcessInstance : {}", id);
        return processInstanceService.findOne(id).orElseThrow();
    }

    /**
     * {@code GET  /process-instances/:id/bpmnModel} : get the bpmn model for the "id" processInstance.
     *
     * @param id the id of the processInstanceDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the processInstanceDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/process-instances/{id}/bpmnModel")
    public ProcessInstanceBpmnModelDTO getProcessInstanceBpmnModel(@PathVariable("id") Long id) {
        log.debug("REST request to get the BPMNModel of the ProcessInstance : {}", id);
        return processInstanceService.findBpmnModel(id).orElseThrow();
    }
}
