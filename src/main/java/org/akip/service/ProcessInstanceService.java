package org.akip.service;


import org.akip.camunda.CamundaConstants;
import org.akip.domain.ProcessDefinition;
import org.akip.domain.ProcessDeployment;
import org.akip.domain.ProcessInstance;
import org.akip.domain.enumeration.StatusProcessInstance;
import org.akip.domain.enumeration.StatusTaskInstance;
import org.akip.repository.ProcessDefinitionRepository;
import org.akip.repository.ProcessDeploymentRepository;
import org.akip.repository.ProcessInstanceRepository;
import org.akip.service.dto.ProcessDefinitionDTO;
import org.akip.service.dto.ProcessInstanceBpmnModelDTO;
import org.akip.service.dto.ProcessInstanceDTO;
import org.akip.service.dto.TaskInstanceDTO;
import org.akip.service.mapper.ProcessInstanceMapper;
import org.camunda.bpm.engine.RuntimeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link ProcessInstance}.
 */
@Service
@Transactional
public class ProcessInstanceService {

    private final Logger log = LoggerFactory.getLogger(ProcessInstanceService.class);

    private final ProcessDefinitionService processDefinitionService;

    private final ProcessDeploymentService processDeploymentService;

    private final TaskInstanceService taskInstanceService;

    private final ProcessDefinitionRepository processDefinitionRepository;

    private final ProcessDeploymentRepository processDeploymentRepository;

    private final ProcessInstanceRepository processInstanceRepository;

    private final ProcessInstanceMapper processInstanceMapper;

    private final RuntimeService runtimeService;

    public ProcessInstanceService(
        ProcessDefinitionService processDefinitionService,
        ProcessDeploymentService processDeploymentService,
        TaskInstanceService taskInstanceService,
        ProcessDefinitionRepository processDefinitionRepository,
        ProcessDeploymentRepository processDeploymentRepository,
        ProcessInstanceRepository processInstanceRepository,
        ProcessInstanceMapper processInstanceMapper,
        RuntimeService runtimeService
    ) {
        this.processDefinitionService = processDefinitionService;
        this.processDeploymentService = processDeploymentService;
        this.taskInstanceService = taskInstanceService;
        this.processDefinitionRepository = processDefinitionRepository;
        this.processDeploymentRepository = processDeploymentRepository;
        this.processInstanceRepository = processInstanceRepository;
        this.processInstanceMapper = processInstanceMapper;
        this.runtimeService = runtimeService;
    }

    public ProcessInstanceDTO create(ProcessInstanceDTO processInstanceDTO) {
        log.debug("Request to create processInstance : {}", processInstanceDTO);

        ProcessDefinition processDefinition = processDefinitionRepository
            .findById(processInstanceDTO.getProcessDefinition().getId())
            .orElseThrow();
        ProcessDeployment processDeployment = processDeploymentRepository
            .findByProcessDefinitionIdAndStatusIsActive(processDefinition.getId())
            .orElseThrow();

        ProcessInstance processInstance = processInstanceMapper.toEntity(processInstanceDTO);
        processInstance.setProcessDefinition(processDefinition);
        processInstance.setCamundaProcessDefinitionId(processDeployment.getCamundaProcessDefinitionId());
        processInstance.setCamundaDeploymentId(processDeployment.getCamundaDeploymentId());
        processInstance.setStartDate(LocalDateTime.now());
        processInstance.setStatus(StatusProcessInstance.RUNNING);

        org.camunda.bpm.engine.runtime.ProcessInstance camundaProcessInstance = runtimeService
            .createProcessInstanceById(processDeployment.getCamundaProcessDefinitionId())
            .businessKey(processInstance.getBusinessKey())
            .execute();

        processInstance.setCamundaProcessInstanceId(camundaProcessInstance.getProcessInstanceId());
        return processInstanceMapper.toDto(processInstanceRepository.save(processInstance));
    }

    public ProcessInstance create(String bpmnProcessDefinitionId, String businessKey, Object processEntity) {
        log.debug("Request to create a processInstance by bpmnProcessDefinitionId: {}", bpmnProcessDefinitionId);

        ProcessDefinition processDefinition = processDefinitionRepository
            .findByBpmnProcessDefinitionId(bpmnProcessDefinitionId)
            .orElseThrow();
        ProcessDeployment processDeployment = processDeploymentRepository
            .findByProcessDefinitionIdAndStatusIsActive(processDefinition.getId())
            .orElseThrow();

        ProcessInstance processInstance = new ProcessInstance();
        processInstance.setBusinessKey(businessKey);
        processInstance.setProcessDefinition(processDefinition);
        processInstance.setCamundaProcessDefinitionId(processDeployment.getCamundaProcessDefinitionId());
        processInstance.setCamundaDeploymentId(processDeployment.getCamundaDeploymentId());
        processInstance.setStartDate(LocalDateTime.now());
        processInstance.setStatus(StatusProcessInstance.RUNNING);

        Map<String, Object> params = new HashMap<>();
        params.put(CamundaConstants.PROCESS_INSTANCE, processEntity);
        params.put(CamundaConstants.PROCESS_INSTANCE_INITIALS, processEntity);

        org.camunda.bpm.engine.runtime.ProcessInstance camundaProcessInstance = runtimeService
            .createProcessInstanceById(processDeployment.getCamundaProcessDefinitionId())
            .businessKey(businessKey)
            .setVariables(params)
            .execute();

        processInstance.setCamundaProcessInstanceId(camundaProcessInstance.getProcessInstanceId());
        return processInstanceRepository.save(processInstance);
    }

    /**
     * Get all the processInstances.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ProcessInstanceDTO> findAll() {
        log.debug("Request to get all ProcessInstances");
        return processInstanceRepository
            .findAll()
            .stream()
            .map(processInstanceMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one processInstance by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProcessInstanceDTO> findOne(Long id) {
        log.debug("Request to get ProcessInstance : {}", id);
        return processInstanceRepository.findById(id).map(processInstanceMapper::toDto);
    }

    public Optional<ProcessInstanceBpmnModelDTO> findBpmnModel(Long id) {
        ProcessInstanceDTO processInstance = findOne(id).orElseThrow();
        ProcessInstanceBpmnModelDTO processInstanceBpmnModel = new ProcessInstanceBpmnModelDTO();

        ProcessDeployment processDeployment = processDeploymentRepository
            .findByCamundaProcessDefinitionId(processInstance.getCamundaProcessDefinitionId())
            .orElseThrow();
        processInstanceBpmnModel.setProcessDeploymentBpmnModel(processDeploymentService.findBpmnModel(processDeployment.getId()).get());

        List<TaskInstanceDTO> processInstanceTasks = taskInstanceService.findByProcessInstance(id);

        processInstanceBpmnModel.setRunningTasksDefinitionKeys(
            processInstanceTasks
                .stream()
                .filter(
                    taskInstanceDTO ->
                        taskInstanceDTO.getStatus() == StatusTaskInstance.NEW || taskInstanceDTO.getStatus() == StatusTaskInstance.ASSIGNED
                )
                .map(TaskInstanceDTO::getTaskDefinitionKey)
                .collect(Collectors.toList())
        );

        processInstanceBpmnModel.setCompletedTasksDefinitionKeys(
            processInstanceTasks
                .stream()
                .filter(taskInstanceDTO -> taskInstanceDTO.getStatus() == StatusTaskInstance.COMPLETED)
                .map(TaskInstanceDTO::getTaskDefinitionKey)
                .collect(Collectors.toList())
        );

        return Optional.of(processInstanceBpmnModel);
    }

    public List<ProcessInstanceDTO> findByProcessDefinition(String idOrBpmnProcessDefinitionId) {
        ProcessDefinitionDTO processDefinitionDTO = processDefinitionService
            .findByIdOrBpmnProcessDefinitionId(idOrBpmnProcessDefinitionId)
            .orElseThrow();
        return processInstanceRepository
            .findByProcessDefinitionId(processDefinitionDTO.getId())
            .stream()
            .map(processInstanceMapper::toDto)
            .collect(Collectors.toList());
    }
}
