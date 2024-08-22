package org.akip.service;

import org.akip.exception.BadRequestErrorException;
import org.akip.form.camundaForm7.CamundaForm7Service;
import org.akip.domain.ProcessDefinition;
import org.akip.domain.enumeration.StatusProcessDefinition;
import org.akip.repository.ProcessDefinitionRepository;
import org.akip.repository.ProcessDeploymentRepository;
import org.akip.service.dto.FormDefinitionDTO;
import org.akip.service.dto.ProcessDefinitionDTO;
import org.akip.service.dto.TaskDefinitionDTO;
import org.akip.service.dto.TaskInstanceDTO;
import org.akip.service.mapper.ProcessDefinitionMapper;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.*;
import org.camunda.bpm.model.bpmn.instance.Process;
import org.camunda.bpm.model.bpmn.instance.UserTask;
import org.camunda.bpm.model.xml.type.ModelElementType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link org.akip.domain.ProcessDefinition}.
 */
@Service
@Transactional
public class ProcessDefinitionService {

    private final Logger log = LoggerFactory.getLogger(ProcessDefinitionService.class);

    private final ProcessDefinitionRepository processDefinitionRepository;

    private final ProcessDefinitionMapper processDefinitionMapper;

    private final ProcessDeploymentRepository processDeploymentRepository;

    private final TaskDefinitionService taskDefinitionService;

    private final CamundaForm7Service camundaForm7Service;


    public ProcessDefinitionService(
            ProcessDefinitionRepository processDefinitionRepository,
            ProcessDefinitionMapper processDefinitionMapper,
            ProcessDeploymentRepository processDeploymentRepository,
            TaskDefinitionService taskDefinitionService,
            CamundaForm7Service camundaForm7Service) {
        this.processDefinitionRepository = processDefinitionRepository;
        this.processDefinitionMapper = processDefinitionMapper;
        this.processDeploymentRepository = processDeploymentRepository;
        this.taskDefinitionService = taskDefinitionService;
        this.camundaForm7Service = camundaForm7Service;
    }

    public ProcessDefinition createOrUpdateProcessDefinition(BpmnModelInstance bpmnModelInstance) {
        Process process = extractAndValidProcessFromModel(bpmnModelInstance);
        Optional<ProcessDefinition> optionalProcessDefinition = processDefinitionRepository.findByBpmnProcessDefinitionId(process.getId());

        if (optionalProcessDefinition.isPresent()) {
            return updateProcessDefinition(process, bpmnModelInstance);
        }

        return createProcessDefinition(process, bpmnModelInstance);
    }

    private Process extractAndValidProcessFromModel(BpmnModelInstance modelInstance) {
        ModelElementType processType = modelInstance.getModel().getType(Process.class);
        Process process = (Process) modelInstance.getModelElementsByType(processType).iterator().next();

        if (!process.isExecutable()) {
            throw new BadRequestErrorException("Model is not executable");
        }

        if (StringUtils.isBlank(process.getName())) {
            throw new BadRequestErrorException("Process name is not provided");
        }

        return process;
    }



    private ProcessDefinition createProcessDefinition(Process process, BpmnModelInstance bpmnModelInstance) {
        ProcessDefinitionDTO processDefinition = new ProcessDefinitionDTO();
        processDefinition.setBpmnProcessDefinitionId(process.getId());
        processDefinition.setName(process.getName());
        processDefinition.setCanBeManuallyStarted(process.isCamundaStartableInTasklist());
        processDefinition.setStatus(StatusProcessDefinition.ACTIVE);
        if (!process.getDocumentations().isEmpty()) {
            processDefinition.setDescription(process.getDocumentations().iterator().next().getRawTextContent());
        }

        /**
         * If the start form definition comes in the BPMN, we consider it a CamundaForm7Builder.
         * Other builders are configured in the ProcessDefinition edit view.
         */

        setupCamundaForm7(processDefinition, bpmnModelInstance);

        ProcessDefinition processDefinitionSaved = processDefinitionRepository.save(processDefinitionMapper.toEntity(processDefinition));

        extractAndSaveTaskDefinitions(bpmnModelInstance, processDefinitionSaved);

        return processDefinitionSaved;
    }

    private ProcessDefinition updateProcessDefinition(Process process, BpmnModelInstance bpmnModelInstance) {
        ProcessDefinitionDTO processDefinition = processDefinitionMapper.toDto(processDefinitionRepository.findByBpmnProcessDefinitionId(process.getId()).orElseThrow());
        processDefinition.setName(process.getName());
        processDefinition.setCanBeManuallyStarted(process.isCamundaStartableInTasklist());
        processDefinition.setStatus(StatusProcessDefinition.ACTIVE);
        if (!process.getDocumentations().isEmpty()) {
            processDefinition.setDescription(process.getDocumentations().iterator().next().getRawTextContent());
        }

        setupCamundaForm7(processDefinition, bpmnModelInstance);

        ProcessDefinition processDefinitionUpdated = processDefinitionRepository.save(processDefinitionMapper.toEntity(processDefinition));

        extractAndSaveTaskDefinitions(bpmnModelInstance, processDefinitionUpdated);

        return processDefinitionUpdated;
    }

    /**
     * Get all the processDefinitions.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ProcessDefinitionDTO> findAll() {
        log.debug("Request to get all ProcessDefinitions");
        return processDefinitionRepository
                .findAll()
                .stream()
                .map(processDefinitionMapper::toDto)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one processDefinition by id.
     *
     * @param idOrBpmnProcessDefinitionId the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProcessDefinitionDTO> findByIdOrBpmnProcessDefinitionId(String idOrBpmnProcessDefinitionId) {
        log.debug("Request to get ProcessDefinition : {}", idOrBpmnProcessDefinitionId);
        if (StringUtils.isNumeric(idOrBpmnProcessDefinitionId)) {
            return processDefinitionRepository.findById(Long.parseLong(idOrBpmnProcessDefinitionId)).map(processDefinitionMapper::toDto);
        }
        return processDefinitionRepository.findByBpmnProcessDefinitionId(idOrBpmnProcessDefinitionId).map(processDefinitionMapper::toDto);
    }

    /**
     * Delete the processDefinition by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ProcessDefinition : {}", id);
        processDefinitionRepository.deleteById(id);
    }


    public List<TaskInstanceDTO> getBpmnUserTasks(String bpmnProcessDefinitionId) {

        ProcessDefinition processDefinition = processDefinitionRepository
                .findByBpmnProcessDefinitionId(bpmnProcessDefinitionId)
                .orElseThrow();

        BpmnModelInstance bpmnModelInstance = Bpmn.readModelFromStream(
                new ByteArrayInputStream(
                        processDeploymentRepository
                                .findByProcessDefinitionIdAndStatusIsActive(processDefinition.getId())
                                .get()
                                .getSpecificationFile()
                )
        );

        return bpmnModelInstance
                .getModelElementsByType(UserTask.class)
                .stream()
                .map(
                        userTask -> {
                            TaskInstanceDTO taskInstance = new TaskInstanceDTO();
                            taskInstance.setTaskDefinitionKey(userTask.getId());
                            taskInstance.setName(userTask.getName());
                            return taskInstance;
                        }
                )
                .collect(Collectors.toList());
    }

    public List<String> getBpmnSignalEvents(String camundaDeploymentId) {

        BpmnModelInstance bpmnModelInstance = Bpmn.readModelFromStream(
                new ByteArrayInputStream(
                        processDeploymentRepository
                                .findByCamundaDeploymentId(camundaDeploymentId)
                                .get()
                                .getSpecificationFile()
                )
        );

        List<String> signals = new ArrayList<>();
        for (IntermediateCatchEvent intermediateCatchEvent : bpmnModelInstance
                .getModelElementsByType(IntermediateCatchEvent.class)) {
            SignalEventDefinition signalEventDefinition = intermediateCatchEvent.getChildElementsByType(SignalEventDefinition.class).iterator().next();
            String signalName = signalEventDefinition.getSignal().getName();
            signals.add(signalName);
        }

        return signals;
    }

    private void extractAndSaveTaskDefinitions(BpmnModelInstance bpmnModelInstance, ProcessDefinition processDefinition){

        bpmnModelInstance
                .getModelElementsByType(UserTask.class)
                .forEach(
                        userTask -> {
                            createAndSaveTaskDefinition(processDefinition, userTask);
                        }
                );

    }

    private void createAndSaveTaskDefinition(ProcessDefinition processDefinition, UserTask userTask) {
        TaskDefinitionDTO taskDefinition = taskDefinitionService
                .findByBpmnProcessDefinitionIdAndTaskId(processDefinition.getBpmnProcessDefinitionId(), userTask.getId())
                .orElse(new TaskDefinitionDTO(processDefinition.getBpmnProcessDefinitionId(), userTask.getId()));
        taskDefinition.setName(userTask.getName());
        taskDefinition.setAssignee(userTask.getCamundaAssignee());
        taskDefinition.setCandidateGroups(userTask.getCamundaCandidateGroups());
        taskDefinition.setCandidateUsers(userTask.getCamundaCandidateUsers());

        if (!userTask.getDocumentations().isEmpty() && StringUtils.isBlank(taskDefinition.getDocumentation())) {
            taskDefinition.setDocumentation(userTask.getDocumentations().iterator().next().getTextContent());
        }

        /**
         * If the form definition comes in the BPMN, we consider it a CamundaForm7Builder.
         * Other builders are configured in the TaskDefinition edit view.
         */
        setupCamundaForm7(taskDefinition, userTask);

        taskDefinitionService.save(taskDefinition);
    }

    private void setupCamundaForm7(ProcessDefinitionDTO processDefinition, BpmnModelInstance bpmnModelInstance) {
        Optional<FormDefinitionDTO> optionalFormDefinition = camundaForm7Service.createOrUpdateStartFormDefinition(processDefinition, bpmnModelInstance);
        if (optionalFormDefinition.isEmpty()) {
            processDefinition.setStartFormIsEnabled(Boolean.FALSE);
            processDefinition.setStartFormDefinition(null);
            return;
        }
        processDefinition.setStartFormIsEnabled(Boolean.TRUE);
        processDefinition.setStartFormDefinition(optionalFormDefinition.get());
    }

    private void setupCamundaForm7(TaskDefinitionDTO taskDefinition, UserTask userTask) {
        Optional<FormDefinitionDTO> optionalFormDefinition = camundaForm7Service.createOrUpdateTaskFormDefinition(taskDefinition, userTask);
        if (optionalFormDefinition.isEmpty()) {
            taskDefinition.setDynamicFormIsEnabled(Boolean.FALSE);
            return;
        }
        taskDefinition.setDynamicFormIsEnabled(Boolean.TRUE);
        taskDefinition.setFormDefinition(optionalFormDefinition.get());
    }

}
