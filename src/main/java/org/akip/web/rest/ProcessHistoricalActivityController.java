package org.akip.web.rest;

import org.akip.service.ProcessHistoricalActivityService;
import org.akip.service.dto.ProcessHistoricalActivityDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProcessHistoricalActivityController {

    private final Logger log = LoggerFactory.getLogger(ProcessHistoricalActivityController.class);

    private final ProcessHistoricalActivityService processHistoricalActivityService;

    public ProcessHistoricalActivityController(ProcessHistoricalActivityService processHistoricalActivityService) {
        this.processHistoricalActivityService = processHistoricalActivityService;
    }


    @GetMapping("/process-instance/{processInstanceId}/process-historical-activities")
    public List<ProcessHistoricalActivityDTO> getByProcessInstanceId(@PathVariable("processInstanceId") Long processInstanceId) {
        log.debug("REST request to get CamundaHistoricActivities by process instance id: {}", processInstanceId);
        return processHistoricalActivityService.getByProcessInstanceId(processInstanceId);
    }

}
