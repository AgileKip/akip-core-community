package org.akip.web.rest;

import org.akip.service.ProcessDefinitionService;
import org.akip.service.ProcessInstanceService;
import org.akip.service.ProcessTimelineDefinitionService;
import org.akip.service.ProcessTimelineService;
import org.akip.service.dto.ProcessInstanceDTO;
import org.akip.service.dto.ProcessTimelineDTO;
import org.akip.service.dto.ProcessTimelineDefinitionDTO;
import org.akip.service.dto.TaskInstanceDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ProcessTimelineController {

    private final Logger log = LoggerFactory.getLogger(ProcessTimelineController.class);

    private final ProcessTimelineDefinitionService processTimelineDefinitionService;

    private final ProcessTimelineService processTimelineService;

    private final ProcessInstanceService processInstanceService;

    private final ProcessDefinitionService processDefinitionService;

    public ProcessTimelineController(
            ProcessTimelineDefinitionService processTimelineDefinitionService,
            ProcessTimelineService processTimelineService,
            ProcessInstanceService processInstanceService, ProcessDefinitionService processDefinitionService) {
        this.processTimelineDefinitionService = processTimelineDefinitionService;
        this.processTimelineService = processTimelineService;
        this.processInstanceService = processInstanceService;
        this.processDefinitionService = processDefinitionService;
    }

    @GetMapping("/process-definitions/{bpmnProcessDefinitionId}/process-instances/{processInstanceId}/timeline")
    public ProcessTimelineDTO getTimeline(@PathVariable String bpmnProcessDefinitionId, @PathVariable Long processInstanceId) {
        log.debug("REST request to get a timeline");
        ProcessInstanceDTO processInstance = processInstanceService.findOne(processInstanceId).orElseThrow();
        return processTimelineService.buildTimeline(bpmnProcessDefinitionId, processInstance);
    }

    @PostMapping("/process-definitions/{bpmnProcessDefinitionId}/timeline")
    public ResponseEntity<ProcessTimelineDefinitionDTO> create(
            @PathVariable String bpmnProcessDefinitionId,
            @RequestBody ProcessTimelineDefinitionDTO processTimelineDefinition
    ) throws URISyntaxException {
        log.debug("REST request creating new ProcessTimelineDefinition");
        ProcessTimelineDefinitionDTO result = processTimelineDefinitionService.save(
                bpmnProcessDefinitionId,
                processTimelineDefinition
        );

        return ResponseEntity
                .created(new URI("/api/process-definitions/" + bpmnProcessDefinitionId + "/timeline/" + result.getId()))
                .headers(HeaderUtil.createAlert(HeaderConstants.APPLICATION_NAME, buildInitMessage(result.getId()), bpmnProcessDefinitionId))
                .body(result);
    }

    private String buildInitMessage(Long processTimelineDefinitionId) {
        return "Process Timeline Definition successfully created: " + processTimelineDefinitionId;
    }

    @GetMapping("/process-definitions/{bpmnProcessDefinitionId}/timeline")
    public List<ProcessTimelineDefinitionDTO> findAll(@PathVariable String bpmnProcessDefinitionId) {
        log.debug("REST request to list ProcessTimelineDefinition");
        return processTimelineDefinitionService.findByProcessDefinitionBpmnProcessDefinitionId(bpmnProcessDefinitionId);
    }

    @GetMapping("/process-definitions/{bpmnProcessDefinitionId}/timeline/tasks")
    public List<TaskInstanceDTO> getBpmnUserTasks(@PathVariable String bpmnProcessDefinitionId) {
        log.debug("REST request to list process tasks");
        return processDefinitionService.getBpmnUserTasks(bpmnProcessDefinitionId);
    }

    @GetMapping("/process-definitions/{bpmnProcessDefinitionId}/timeline/{processTimelineDefinitionId}")
    public ResponseEntity<ProcessTimelineDefinitionDTO> findOne(
            @PathVariable String bpmnProcessDefinitionId,
            @PathVariable Long processTimelineDefinitionId
    ) {
        log.debug("REST request to find one ProcessTimelineDefinition");
        Optional<ProcessTimelineDefinitionDTO> processTimelineDefinition = processTimelineDefinitionService.findOne(processTimelineDefinitionId);
        return ResponseUtil.wrapOrNotFound(processTimelineDefinition);
    }

    @PutMapping("/process-definitions/{bpmnProcessDefinitionId}/timeline/edit")
    public ResponseEntity<ProcessTimelineDefinitionDTO> updateTimeline(
            @PathVariable String bpmnProcessDefinitionId,
            @RequestBody ProcessTimelineDefinitionDTO processTimelineDefinitionDTO
    ) {
        log.debug("REST request to update a ProcessTimelineDefinition");
        ProcessTimelineDefinitionDTO result = processTimelineDefinitionService.save(
                bpmnProcessDefinitionId,
                processTimelineDefinitionDTO
        );
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/process-definitions/{bpmnProcessDefinitionId}/timeline/{processTimelineDefinitionId}")
    public ResponseEntity<Void> deleteProcessTimeline(
            @PathVariable String bpmnProcessDefinitionId,
            @PathVariable Long processTimelineDefinitionId
    ) {
        log.debug("REST request to delete a ProcessTimelineDefinition : {}", processTimelineDefinitionId);
        processTimelineDefinitionService.delete(processTimelineDefinitionId);
        return ResponseEntity.noContent().build();
    }
}