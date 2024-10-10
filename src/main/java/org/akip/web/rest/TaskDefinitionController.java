package org.akip.web.rest;

import org.akip.service.TaskDefinitionService;
import org.akip.service.dto.TaskDefinitionDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api")
public class TaskDefinitionController {

    private final Logger log = LoggerFactory.getLogger(TaskDefinitionController.class);
    private final TaskDefinitionService taskDefinitionService;

    public TaskDefinitionController(TaskDefinitionService taskDefinitionService) {
        this.taskDefinitionService = taskDefinitionService;
    }

    @GetMapping("/task-definition/{id}")
    public TaskDefinitionDTO find(@PathVariable("id") Long id) {
        log.debug("REST request to get TaskDefinition of the TaskDefinitionId : {} ", id);
        return taskDefinitionService.findById(id);
    }

    @DeleteMapping("/task-definition/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        log.debug("REST request to delete TaskDefinition : {}", id);
        taskDefinitionService.delete(id);
        return ResponseEntity
                .noContent()
                .build();
    }

    @PostMapping("/task-definition")
    public ResponseEntity<TaskDefinitionDTO> save(@RequestBody TaskDefinitionDTO taskDefinitionDTO) throws URISyntaxException {
        log.debug("REST request to save TaskDefinition : {} ", taskDefinitionDTO);
        TaskDefinitionDTO result = taskDefinitionService.save(taskDefinitionDTO);
        return ResponseEntity
                .created(new URI("/api/task-definition/" + result.getBpmnProcessDefinitionId() + "/" + result.getTaskId()))
                .headers(HeaderUtil.createAlert(HeaderConstants.APPLICATION_NAME, buildCreateMessage(result.getTaskId()), taskDefinitionDTO.getTaskId()))
                .body(result);
    }

    @PutMapping("/task-definition")
    public ResponseEntity<TaskDefinitionDTO> update(@RequestBody TaskDefinitionDTO taskDefinitionDTO) throws URISyntaxException {
        log.debug("REST request to update Task Definition : {} ", taskDefinitionDTO);
        TaskDefinitionDTO result = taskDefinitionService.update(taskDefinitionDTO);
        return ResponseEntity
                .created(new URI("/api/task-definition/" + result.getBpmnProcessDefinitionId() + "/" + result.getTaskId()))
                .headers(HeaderUtil.createAlert(HeaderConstants.APPLICATION_NAME, buildUpdateMessage(result.getTaskId()), taskDefinitionDTO.getTaskId()))
                .body(result);
    }

    @GetMapping("/task-definition/{id}/documentation")
    public String getDocumentation(@PathVariable("id") Long id) {
        log.debug("REST request to Task Definition Documentation : {} ", id);
        return taskDefinitionService.findDocumentationById(id);
    }

    @PutMapping("/task-definition/{id}/documentation")
    public void updateDocumentation(@PathVariable("id") Long id, @RequestBody TaskDefinitionDTO taskDefinition) {
        log.debug("REST request to update Task Definition Documentation : {} ", id);
        taskDefinitionService.updateDocumentation(taskDefinition.getDocumentation(), id);
    }

    private String buildCreateMessage(String taskDefinitionId) {
        return "Task Definition successfully created: " + taskDefinitionId;
    }

    private String buildUpdateMessage(String taskDefinitionId) {
        return "Task Definition successfully created: " + taskDefinitionId;
    }

}
