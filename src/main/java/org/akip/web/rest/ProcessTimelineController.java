package org.akip.web.rest;

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

    public ProcessTimelineController(
            ProcessTimelineDefinitionService processTimelineDefinitionService,
            ProcessTimelineService processTimelineService,
            ProcessInstanceService processInstanceService
    ) {
        this.processTimelineDefinitionService = processTimelineDefinitionService;
        this.processTimelineService = processTimelineService;
        this.processInstanceService = processInstanceService;
    }

    @GetMapping("/process-definitions/{processDefinitionBusinessKey}/process-instances/{processInstanceId}/timeline")
    public ProcessTimelineDTO getTimeline(@PathVariable String processDefinitionBusinessKey, @PathVariable Long processInstanceId) {
        log.debug("REST request to get a timeline");
        ProcessInstanceDTO processInstance = processInstanceService.findOne(processInstanceId).orElseThrow();
        return processTimelineService.buildTimeline(processDefinitionBusinessKey, processInstance);
    }

    @PostMapping("/process-definitions/{processDefinitionBusinessKey}/timeline")
    public ResponseEntity<ProcessTimelineDefinitionDTO> create(
            @PathVariable String processDefinitionBusinessKey,
            @RequestBody ProcessTimelineDefinitionDTO processTimelineDefinition
    ) throws URISyntaxException {

        log.debug("REST request creating new ProcessTimelineDefinition");
        ProcessTimelineDefinitionDTO result = processTimelineDefinitionService.save(
                processDefinitionBusinessKey,
                processTimelineDefinition
        );

        return ResponseEntity
                .created(new URI("/api/process-definitions/" + processDefinitionBusinessKey + "/timeline/" + result.getId()))
                .headers(HeaderUtil.createAlert(HeaderConstants.APPLICATION_NAME, buildInitMessage(result.getId()), processDefinitionBusinessKey))
                .body(result);
    }

    private String buildInitMessage(Long processTimelineDefinitionId) {
        return "Process Timeline Definition successfully created: " + processTimelineDefinitionId;
    }

    @GetMapping("/process-definitions/{processDefinitionBusinessKey}/timeline")
    public List<ProcessTimelineDefinitionDTO> findAll(@PathVariable String processDefinitionBusinessKey) {
        log.debug("REST request to list ProcessTimelineDefinition");
        return processTimelineDefinitionService.findAllByProcessDefinitionBpmnProcessDefinitionId(processDefinitionBusinessKey);
    }

    @GetMapping("/process-definitions/{processDefinitionBusinessKey}/timeline/tasks")
    public List<TaskInstanceDTO> getBpmnUserTasks(@PathVariable String processDefinitionBusinessKey) {
        log.debug("REST request to list process tasks");
        return processTimelineDefinitionService.getBpmnUserTasks(processDefinitionBusinessKey);
    }

    @GetMapping("/process-definitions/{processDefinitionBusinessKey}/timeline/{timelineDefinitionId}")
    public ResponseEntity<ProcessTimelineDefinitionDTO> findOne(
            @PathVariable String processDefinitionBusinessKey,
            @PathVariable Long timelineDefinitionId
    ) {
        log.debug("REST request to find one ProcessTimelineDefinition");
        Optional<ProcessTimelineDefinitionDTO> processTimelineDefinition = processTimelineDefinitionService.findOne(timelineDefinitionId);
        return ResponseUtil.wrapOrNotFound(processTimelineDefinition);
    }

    @PutMapping("/process-definitions/{processDefinitionBusinessKey}/timeline/edit")
    public ResponseEntity<ProcessTimelineDefinitionDTO> updateTimeline(
            @PathVariable String processDefinitionBusinessKey,
            @RequestBody ProcessTimelineDefinitionDTO processTimelineDefinitionDTO
    ) {
        log.debug("REST request to update a ProcessTimelineDefinition");
        ProcessTimelineDefinitionDTO result = processTimelineDefinitionService.save(
                processDefinitionBusinessKey,
                processTimelineDefinitionDTO
        );

        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/process-definitions/{processDefinitionBusinessKey}/timeline/{timelineDefinitionId}")
    public ResponseEntity<Void> deleteProcessTimeline(
            @PathVariable String processDefinitionBusinessKey,
            @PathVariable Long timelineDefinitionId
    ) {
        log.debug("REST request to delete a ProcessTimelineDefinition : {}", timelineDefinitionId);
        processTimelineDefinitionService.delete(timelineDefinitionId);
        return ResponseEntity.noContent().build();
    }
}
