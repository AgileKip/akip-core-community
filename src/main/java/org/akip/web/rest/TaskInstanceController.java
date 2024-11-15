package org.akip.web.rest;

import org.akip.service.TaskInstanceService;
import org.akip.service.dto.TaskInstanceDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;

import java.util.List;

/**
 * REST controller for managing {@link org.akip.domain.TaskInstance}.
 */
@RestController
@RequestMapping("/api")
public class TaskInstanceController {

    private final Logger log = LoggerFactory.getLogger(TaskInstanceController.class);

    private final TaskInstanceService taskInstanceService;

    public TaskInstanceController(TaskInstanceService taskInstanceService) {
        this.taskInstanceService = taskInstanceService;
    }


    /**
     * {@code GET  /my-candidate-tasks} : get my candidate taskInstances.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of taskInstances in body.
     */
    @GetMapping("/my-candidate-tasks")
    public List<TaskInstanceDTO> getMyCadidateTaskInstances() {
        log.debug("REST request to getMyCandidateTaskInstances");
        return taskInstanceService.getMyCandidateTaskInstances();
    }

    /**
     * {@code GET  /my-assigned-tasks} : get my assigned taskInstances.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of taskInstances in body.
     */
    @GetMapping("/my-assigned-tasks")
    public List<TaskInstanceDTO> getMyAssignedTaskInstances() {
        log.debug("REST request to get myAssignedTaskInstances");
        return taskInstanceService.getMyAssignedTaskInstances();
    }

    /**
     * {@code GET  /task-instances/:id} : get the "id" taskInstance.
     *
     * @param id the id of the taskInstanceDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the taskInstanceDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/task-instances/{id}")
    public TaskInstanceDTO getTaskInstance(@PathVariable("id") Long id) {
        log.debug("REST request to get TaskInstance : {}", id);
        return taskInstanceService.findOne(id).orElseThrow();
    }

    @GetMapping("/task-instances/{id}/claim")
    public TaskInstanceDTO claimTaskInstance(@PathVariable("id") Long id) {
        log.debug("REST request to get TaskInstance : {}", id);
        return taskInstanceService.claim(id).orElseThrow();
    }

    @PostMapping("/task-instances/complete")
    public ResponseEntity<Void> completeTaskInstance(@RequestBody TaskInstanceDTO taskInstanceDTO) {
        log.debug("REST request to complete TaskInstance: Id {}, TaskId {}", taskInstanceDTO.getId(), taskInstanceDTO.getTaskId());
        taskInstanceService.complete(taskInstanceDTO);
        return ResponseEntity
            .noContent()
            .build();
    }

    @GetMapping("/task-instances/{id}/redo")
    public ResponseEntity<Void> redoTaskInstance(@PathVariable Long id) {
        log.debug("REST request to redo TaskInstance : {}", id);
        taskInstanceService.redo(id);
        return ResponseEntity
                .noContent()
                .headers(HeaderUtil.createAlert(HeaderConstants.APPLICATION_NAME, buildRedoMessage(id), "OK"))
                .build();
    }

    private String buildRedoMessage(Long id) {
        return "Task " + id + " re-executed successfully";
    }
}
