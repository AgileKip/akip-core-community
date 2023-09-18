package org.akip.web.rest;

import org.akip.service.DecisionDeploymentService;
import org.akip.service.TemporaryProcessInstanceService;
import org.akip.service.dto.TemporaryProcessInstanceDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ProcessInstanceResource {

    private final Logger log = LoggerFactory.getLogger(ProcessInstanceResource.class);

    private final TemporaryProcessInstanceService temporaryProcessInstanceService ;

    public ProcessInstanceResource(TemporaryProcessInstanceService temporaryProcessInstanceService) {
        this.temporaryProcessInstanceService = temporaryProcessInstanceService;
    }

    @GetMapping("process-instances/{processBpmnId}/create-temporary-process-instance")
    public ResponseEntity<TemporaryProcessInstanceDTO> createTemporaryProcessInstance(@PathVariable String processBpmnId) {
        log.debug("REST request to create a temporary process instance for the process ProcessInstance");
        TemporaryProcessInstanceDTO result = temporaryProcessInstanceService.create(processBpmnId);
        return ResponseEntity.ok(result);
    }
}
