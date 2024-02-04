package org.akip.web.rest;

import org.akip.domain.ProcessInstance;
import org.akip.repository.ProcessInstanceRepository;
import org.akip.service.dto.CamundaHistoryActivityDTO;
import org.akip.service.mapper.CamundaHistoryActivityMapper;
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.history.HistoricActivityInstance;
import org.camunda.bpm.engine.history.HistoricActivityInstanceQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class CamundaHistoryActivityController {

    private final Logger log = LoggerFactory.getLogger(CamundaHistoryActivityController.class);

    private final HistoryService historyService;

    private final CamundaHistoryActivityMapper camundaHistoryActivityMapper;

    private final ProcessInstanceRepository processInstanceRepository;

    public CamundaHistoryActivityController(HistoryService historyService, CamundaHistoryActivityMapper camundaHistoryActivityMapper, ProcessInstanceRepository processInstanceRepository) {
        this.historyService = historyService;
        this.camundaHistoryActivityMapper = camundaHistoryActivityMapper;
        this.processInstanceRepository = processInstanceRepository;
    }

    @GetMapping("/process-instance/{processInstanceId}/camunda-history-activities")
    public List<CamundaHistoryActivityDTO> getCamundaHistoricActivityByProcessInstanceId(@PathVariable Long processInstanceId) {
        log.debug("REST request to get CamundaHistoricActivities by process instance id: {}", processInstanceId);
        try {
            ProcessInstance processInstance = processInstanceRepository.findById(processInstanceId).orElseThrow();

            HistoricActivityInstanceQuery query = historyService
                .createHistoricActivityInstanceQuery()
                .processInstanceId(processInstance.getCamundaProcessInstanceId())
                .orderByHistoricActivityInstanceEndTime()
                .asc();

            List<HistoricActivityInstance> historicActivityInstances = query.list();

            return historicActivityInstances.stream().map(camundaHistoryActivityMapper::mapToCamundaHistoryActivityDTO).collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while fetching historic activity signal events.", e);
        }
    }

}
