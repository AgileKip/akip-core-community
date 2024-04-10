package org.akip.web.rest;

import org.akip.service.FormDefinitionService;
import org.akip.service.dto.FormDefinitionDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;

import java.net.URISyntaxException;

@RestController
@RequestMapping("/api")
public class FormDefinitionController {

    private final Logger log = LoggerFactory.getLogger(FormDefinitionController.class);
    private final FormDefinitionService formDefinitionService;

    public FormDefinitionController(FormDefinitionService formDefinitionService) {
        this.formDefinitionService = formDefinitionService;
    }

    @GetMapping("/form-definition/{id}")
    public FormDefinitionDTO find(@PathVariable Long id) {
        log.debug("REST request to get FormDefinition of the FormDefinitionId : {} ", id);
        return formDefinitionService.findById(id).get();
    }

    @GetMapping("/form-definition/process-definition/{processDefinitionId}")
    public FormDefinitionDTO findByProcessDefinition(@PathVariable Long processDefinitionId) {
        log.debug("REST request to get FormDefinition of the ProcessDefinitionId : {} ", processDefinitionId);
        return formDefinitionService.findByProcessDefinitionId(processDefinitionId).get();
    }

    @GetMapping("/form-definition/task-definition/{taskDefinitionId}")
    public FormDefinitionDTO findByTaskDefinition(@PathVariable Long taskDefinitionId) {
        log.debug("REST request to get FormDefinition of the TaskDefinitionId : {} ", taskDefinitionId);
        return formDefinitionService.findByTaskDefinitionId(taskDefinitionId).get();
    }

    @DeleteMapping("/form-definition/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        log.debug("REST request to delete FormDefinition : {}", id);
        formDefinitionService.delete(id);
        return ResponseEntity
                .noContent()
                .headers(HeaderUtil.createAlert(HeaderConstants.APPLICATION_NAME, buildDeleteMessage(), String.valueOf(id)))
                .build();
    }

    @PostMapping("/form-definition")
    public ResponseEntity<FormDefinitionDTO> save(@RequestBody FormDefinitionDTO formDefinitionDTO) throws URISyntaxException {
        log.debug("REST request to save FormDefinition : {} ", formDefinitionDTO);
        FormDefinitionDTO result = formDefinitionService.save(formDefinitionDTO);
        return ResponseEntity
                .noContent()
                .headers(HeaderUtil.createAlert(HeaderConstants.APPLICATION_NAME, buildCreateMessage(result.getName()), formDefinitionDTO.getName()))
                .build();
    }

    @PostMapping("/form-definition/create/task-definition/{taskDefinitionId}")
    public ResponseEntity<FormDefinitionDTO> createByTaskDefinition(@PathVariable("taskDefinitionId") Long taskDefinitionId, @RequestBody FormDefinitionDTO formDefinitionDTO) throws URISyntaxException {
        log.debug("REST request to save FormDefinition : {} ", formDefinitionDTO);
        FormDefinitionDTO result = formDefinitionService.saveWithTaskDefinition(taskDefinitionId, formDefinitionDTO);
        return ResponseEntity
                .ok()
                .headers(HeaderUtil.createAlert(HeaderConstants.APPLICATION_NAME, buildCreateMessage(result.getName()), formDefinitionDTO.getName()))
                .body(result);
    }

    @PostMapping("/form-definition/create/process-definition/{processDefinitionId}")
    public ResponseEntity<FormDefinitionDTO> createByProcessDefinition(@PathVariable("processDefinitionId") Long processDefinitionId, @RequestBody FormDefinitionDTO formDefinitionDTO) throws URISyntaxException {
        log.debug("REST request to save FormDefinition : {} ", formDefinitionDTO);
        FormDefinitionDTO result = formDefinitionService.saveWithProcessDefinition(processDefinitionId, formDefinitionDTO);
        return ResponseEntity
                .ok()
                .headers(HeaderUtil.createAlert(HeaderConstants.APPLICATION_NAME, buildCreateMessage(result.getName()), formDefinitionDTO.getName()))
                .body(result);
    }

    @PutMapping("/form-definition")
    public ResponseEntity<FormDefinitionDTO> update(@RequestBody FormDefinitionDTO formDefinitionDTO) throws URISyntaxException {
        log.debug("REST request to save FormDefinition : {} ", formDefinitionDTO);
        FormDefinitionDTO result = formDefinitionService.save(formDefinitionDTO);
        return ResponseEntity
                .noContent()
                .headers(HeaderUtil.createAlert(HeaderConstants.APPLICATION_NAME, buildUpdateMessage(String.valueOf(result.getId())), String.valueOf(formDefinitionDTO.getName())))
                .build();
    }

    private String buildCreateMessage(String formDefinitionId) {
        return "Form Definition successfully created: " + formDefinitionId;
    }

    private String buildUpdateMessage(String formDefinitionId) {
        return "Form Definition successfully updated: " + formDefinitionId;
    }

    private String buildDeleteMessage() {
        return "Form Definition successfully deleted";
    }

}
