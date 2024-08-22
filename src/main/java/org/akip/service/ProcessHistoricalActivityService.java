package org.akip.service;

import org.akip.domain.ProcessInstance;
import org.akip.repository.ProcessInstanceRepository;
import org.akip.service.dto.ProcessHistoricalActivityDTO;
import org.akip.service.mapper.ProcessHistoricalActivityMapper;
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.history.HistoricActivityInstance;
import org.camunda.bpm.engine.history.HistoricActivityInstanceQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProcessHistoricalActivityService {

    private final Logger log = LoggerFactory.getLogger(ProcessHistoricalActivityService.class);

    private final HistoryService historyService;

    private final ProcessHistoricalActivityMapper processHistoricalActivityMapper;

    private final ProcessInstanceRepository processInstanceRepository;

    public ProcessHistoricalActivityService(HistoryService historyService, ProcessHistoricalActivityMapper processHistoricalActivityMapper, ProcessInstanceRepository processInstanceRepository) {
        this.historyService = historyService;
        this.processHistoricalActivityMapper = processHistoricalActivityMapper;
        this.processInstanceRepository = processInstanceRepository;
    }

    @GetMapping("/process-instance/{processInstanceId}/process-historical-activities")
    public List<ProcessHistoricalActivityDTO> getByProcessInstanceId(@PathVariable Long processInstanceId) {
        log.debug("REST request to get CamundaHistoricActivities by process instance id: {}", processInstanceId);
        try {
            ProcessInstance processInstance = processInstanceRepository.findById(processInstanceId).orElseThrow();

            HistoricActivityInstanceQuery query = historyService
                .createHistoricActivityInstanceQuery()
                .processInstanceId(processInstance.getCamundaProcessInstanceId())
                .orderByHistoricActivityInstanceEndTime()
                .asc();

            List<HistoricActivityInstance> historicActivityInstances = query.list();

            return historicActivityInstances
                    .stream()
                    .map(processHistoricalActivityMapper::camundaHistoricActivityToProcessHistoricalActivityDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while fetching historical activities.", e);
        }
    }

}
