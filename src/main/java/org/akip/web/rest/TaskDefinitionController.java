package org.akip.web.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.akip.service.TaskDefinitionService;
import org.akip.service.dto.TaskDefinitionDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class TaskDefinitionController {

    private final Logger log = LoggerFactory.getLogger(ProcessDefinitionController.class);
    private final TaskDefinitionService taskDefinitionService;

    public TaskDefinitionController(TaskDefinitionService taskDefinitionService) {
        this.taskDefinitionService = taskDefinitionService;
    }

    @GetMapping("/task-definition/{bpmnProcessDefinitionId}/{taskDefinitionId}")
    public TaskDefinitionDTO getTaskDefinition(@PathVariable String bpmnProcessDefinitionId, @PathVariable String taskDefinitionId) {
        log.debug("REST request to get TaskDefinition of the BpmnProcessDefinitionId and TaskDefinitionId : {} ", bpmnProcessDefinitionId, taskDefinitionId);
        return taskDefinitionService.findTaskByBpmnProcessDefinitionIdAndTaskId(bpmnProcessDefinitionId, taskDefinitionId);
    }

    @GetMapping("/task-definition/{id}")
    public TaskDefinitionDTO find(@PathVariable Long id) {
        log.debug("REST request to get TaskDefinition of the TaskDefinitionId : {} ", id);
        return taskDefinitionService.findTaskDefinitionById(id);
    }

    @GetMapping("/task-definition")
    public List<TaskDefinitionDTO> findAll() {
        log.debug("REST request to find all TaskDefinition");
        return taskDefinitionService.findAll();
    }
    @DeleteMapping("/task-definition/{id}")
    public ResponseEntity<Void> deleteTaskDefinition(@PathVariable("id") Long id) {
        log.debug("REST request to delete ProcessDefinition : {}", id);
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

    @PostMapping("/task-definition/update")
    public ResponseEntity<TaskDefinitionDTO> update(@RequestBody TaskDefinitionDTO taskDefinitionDTO) throws URISyntaxException {
        log.debug("REST request to save TaskDefinition : {} ", taskDefinitionDTO);
        TaskDefinitionDTO result = taskDefinitionService.save(taskDefinitionDTO);
        return ResponseEntity
                .created(new URI("/api/task-definition/" + result.getBpmnProcessDefinitionId() + "/" + result.getTaskId()))
                .headers(HeaderUtil.createAlert(HeaderConstants.APPLICATION_NAME, buildUpdateMessage(result.getTaskId()), taskDefinitionDTO.getTaskId()))
                .body(result);
    }

    private String buildCreateMessage(String taskDefinitionId) {
        return "Task Definition successfully created: " + taskDefinitionId;
    }

    private String buildUpdateMessage(String taskDefinitionId) {
        return "Task Definition successfully created: " + taskDefinitionId;
    }

}
