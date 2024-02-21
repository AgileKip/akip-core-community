package org.akip.service;

import org.akip.camunda.form.CamundaFormFieldDef;
import org.akip.camunda.form.CamundaFormFieldValidationConstraintDef;
import org.akip.domain.ProcessDefinition;
import org.akip.domain.enumeration.StatusProcessDefinition;
import org.akip.repository.ProcessDefinitionRepository;
import org.akip.repository.ProcessDeploymentRepository;
import org.akip.service.dto.ProcessDefinitionDTO;
import org.akip.service.dto.TaskInstanceDTO;
import org.akip.service.mapper.ProcessDefinitionMapper;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.*;
import org.camunda.bpm.model.bpmn.instance.Process;
import org.camunda.bpm.model.bpmn.instance.camunda.*;
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

    private static final String ENTITY_NAME = "processDefinition";

    private final Logger log = LoggerFactory.getLogger(ProcessDefinitionService.class);

    private final ProcessDefinitionRepository processDefinitionRepository;

    private final ProcessDefinitionMapper processDefinitionMapper;

    private final ProcessDeploymentRepository processDeploymentRepository;

    public ProcessDefinitionService(
            ProcessDefinitionRepository processDefinitionRepository,
            ProcessDefinitionMapper processDefinitionMapper,
            ProcessDeploymentRepository processDeploymentRepository) {
        this.processDefinitionRepository = processDefinitionRepository;
        this.processDefinitionMapper = processDefinitionMapper;
        this.processDeploymentRepository = processDeploymentRepository;
    }

    public ProcessDefinition createOrUpdateProcessDefinition(BpmnModelInstance bpmnModelInstance) {
        Process process = extracAndValidProcessFromModel(bpmnModelInstance);
        Optional<ProcessDefinition> optionalProcessDefinition = processDefinitionRepository.findByBpmnProcessDefinitionId(process.getId());

        if (optionalProcessDefinition.isPresent()) {
            return updateProcessDefinition(process, bpmnModelInstance);
        }

        return createProcessDefinition(process, bpmnModelInstance);
    }

    private Process extracAndValidProcessFromModel(BpmnModelInstance modelInstance) {
        ModelElementType processType = modelInstance.getModel().getType(Process.class);
        Process process = (Process) modelInstance.getModelElementsByType(processType).iterator().next();

        if (!process.isExecutable()) {
            throw new RuntimeException("Model is not executable");
        }

        if (StringUtils.isBlank(process.getName())) {
            throw new RuntimeException("Process name is not provided");
        }

        return process;
    }

    private StartEvent extracStartEventFromModel(BpmnModelInstance modelInstance) {
        ModelElementType startEventType = modelInstance.getModel().getType(StartEvent.class);
        StartEvent startEvent = (StartEvent) modelInstance.getModelElementsByType(startEventType).iterator().next();

        return startEvent;
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

        processDefinition.setStartFormFields(extractFormFields(bpmnModelInstance));

        return processDefinitionRepository.save(processDefinitionMapper.toEntity(processDefinition));
    }

    private ProcessDefinition updateProcessDefinition(Process process, BpmnModelInstance bpmnModelInstance) {
        ProcessDefinitionDTO processDefinition = processDefinitionMapper.toDto(processDefinitionRepository.findByBpmnProcessDefinitionId(process.getId()).orElseThrow());
        processDefinition.setName(process.getName());
        processDefinition.setCanBeManuallyStarted(process.isCamundaStartableInTasklist());
        processDefinition.setStatus(StatusProcessDefinition.ACTIVE);
        if (!process.getDocumentations().isEmpty()) {
            processDefinition.setDescription(process.getDocumentations().iterator().next().getRawTextContent());
        }

        processDefinition.setStartFormFields(extractFormFields(bpmnModelInstance));

        return processDefinitionRepository.save(processDefinitionMapper.toEntity(processDefinition));
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

    public List<CamundaFormFieldDef> extractFormFields(BpmnModelInstance bpmnModelInstance) {
        StartEvent startEvent = extracStartEventFromModel(bpmnModelInstance);
        if (startEvent.getExtensionElements() == null
                ||
                startEvent.getExtensionElements()
                        .getElementsQuery()
                        .filterByType(CamundaFormData.class)
                        .list()
                        .isEmpty()) {
            return Collections.emptyList();
        }
        return startEvent
                .getExtensionElements()
                .getElementsQuery()
                .filterByType(CamundaFormData.class)
                .singleResult()
                .getCamundaFormFields()
                .stream()
                .map(this::toCamundaFormFieldDef)
                .collect(Collectors.toList());
    }

    private CamundaFormFieldDef toCamundaFormFieldDef(CamundaFormField formField) {
        CamundaFormFieldDef camundaFormFieldDef = new CamundaFormFieldDef();
        camundaFormFieldDef.setId(formField.getCamundaId());
        camundaFormFieldDef.setLabel(formField.getCamundaLabel());
        camundaFormFieldDef.setType(formField.getCamundaType());
        if (formField.getCamundaValues() != null) {
            camundaFormFieldDef.setValues(formField.getCamundaValues().stream().collect(Collectors.toMap(CamundaValue::getCamundaId, CamundaValue::getCamundaName)));
        }

        if (formField.getCamundaDefaultValue() != null) {
            camundaFormFieldDef.setDefaultValue(formField.getCamundaDefaultValue());
        }
        if (formField.getCamundaValidation() != null) {
            camundaFormFieldDef.setValidationConstraints(
                    formField.getCamundaValidation().getCamundaConstraints().stream().map(this::toCamundaFormFieldValidationConstraintDef).collect(Collectors.toList())
            );
        }

        if (formField.getCamundaProperties() != null) {
            camundaFormFieldDef.setProperties(
                    formField.getCamundaProperties().getCamundaProperties().stream().collect(Collectors.toMap(CamundaProperty::getCamundaId, CamundaProperty::getCamundaValue))
            );
        }
        return camundaFormFieldDef;
    }

    private CamundaFormFieldValidationConstraintDef toCamundaFormFieldValidationConstraintDef(
            CamundaConstraint camundaConstraint
    ) {
        CamundaFormFieldValidationConstraintDef camundaFormFieldValidationConstraintDef = new CamundaFormFieldValidationConstraintDef();
        camundaFormFieldValidationConstraintDef.setName(camundaConstraint.getCamundaName());
        camundaFormFieldValidationConstraintDef.setConfiguration(camundaConstraint.getCamundaConfig());
        return camundaFormFieldValidationConstraintDef;
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

}
