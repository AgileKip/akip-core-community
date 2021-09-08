package org.akip.service;

import org.akip.domain.ProcessDefinition;
import org.akip.domain.ProcessDeployment;
import org.akip.domain.enumeration.StatusProcessDeployment;
import org.akip.repository.ProcessDeploymentRepository;
import org.akip.service.dto.ProcessDefinitionDTO;
import org.akip.service.dto.ProcessDeploymentBpmnModelDTO;
import org.akip.service.dto.ProcessDeploymentDTO;
import org.akip.service.mapper.ProcessDeploymentMapper;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.ExtensionElements;
import org.camunda.bpm.model.bpmn.instance.Process;
import org.camunda.bpm.model.bpmn.instance.UserTask;
import org.camunda.bpm.model.bpmn.instance.camunda.CamundaExecutionListener;
import org.camunda.bpm.model.bpmn.instance.camunda.CamundaTaskListener;
import org.camunda.bpm.model.xml.instance.ModelElementInstance;
import org.camunda.bpm.model.xml.type.ModelElementType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link ProcessDeployment}.
 */
@Service
@Transactional
public class ProcessDeploymentService {

    private final Logger log = LoggerFactory.getLogger(ProcessDeploymentService.class);

    private final ProcessDefinitionService processDefinitionService;

    private final ProcessDeploymentRepository processDeploymentRepository;

    private final RepositoryService repositoryService;

    private final ProcessDeploymentMapper processDeploymentMapper;

    public ProcessDeploymentService(
        ProcessDefinitionService processDefinitionService,
        ProcessDeploymentRepository processDeploymentRepository,
        RepositoryService repositoryService,
        ProcessDeploymentMapper processDeploymentMapper
    ) {
        this.processDefinitionService = processDefinitionService;
        this.processDeploymentRepository = processDeploymentRepository;
        this.repositoryService = repositoryService;
        this.processDeploymentMapper = processDeploymentMapper;
    }

    public ProcessDeploymentDTO deploy(ProcessDeploymentDTO processDeploymentDTO) {
        BpmnModelInstance bpmnModelInstance = Bpmn.readModelFromStream(
            new ByteArrayInputStream(processDeploymentDTO.getSpecificationFile())
        );
        ProcessDefinition processDefinition = processDefinitionService.createOrUpdateProcessDefinition(bpmnModelInstance);

        org.camunda.bpm.engine.repository.Deployment camundaDeployment = deployInCamunda(processDefinition, bpmnModelInstance);

        org.camunda.bpm.engine.repository.ProcessDefinition camundaProcessDefinition = repositoryService
            .createProcessDefinitionQuery()
            .deploymentId(camundaDeployment.getId())
            .singleResult();

        ProcessDeployment processDeployment = processDeploymentMapper.toEntity(processDeploymentDTO);
        processDeployment.setProcessDefinition(processDefinition);
        processDeployment.setCamundaDeploymentId(camundaDeployment.getId());
        processDeployment.setCamundaProcessDefinitionId(camundaProcessDefinition.getId());
        processDeployment.setStatus(StatusProcessDeployment.ACTIVE);
        processDeployment.setDeployDate(LocalDateTime.now());
        processDeployment.setActivationDate(processDeployment.getDeployDate());

        inactivatePreviousProcessDeployments(processDeployment);

        return processDeploymentMapper.toDto(processDeploymentRepository.save(processDeployment));
    }

    public Optional<ProcessDeploymentDTO> findOne(Long id) {
        return processDeploymentRepository.findById(id).map(processDeploymentMapper::toDto);
    }

    public Optional<ProcessDeploymentBpmnModelDTO> findBpmnModel(Long id) {
        return processDeploymentRepository.findById(id).map(processDeploymentMapper::toBpmnModelDto);
    }

    public void activate(Long processDeploymentId) {
        ProcessDeployment processDeployment = processDeploymentRepository.findById(processDeploymentId).orElseThrow();
        inactivatePreviousProcessDeployments(processDeployment);
        processDeploymentRepository.updateStatusById(StatusProcessDeployment.ACTIVE, processDeploymentId);
        processDeploymentRepository.updateActivationDateById(LocalDateTime.now(), processDeploymentId);
    }

    public void inactivate(Long processDeploymentId) {
        processDeploymentRepository.updateStatusById(StatusProcessDeployment.INACTIVE, processDeploymentId);
        processDeploymentRepository.updateInactivationDateById(LocalDateTime.now(), processDeploymentId);
    }

    public List<ProcessDeploymentDTO> findByProcessDefinition(String idOrBpmnProcessDefinitionId) {
        ProcessDefinitionDTO processDefinitionDTO = processDefinitionService
            .findByIdOrBpmnProcessDefinitionId(idOrBpmnProcessDefinitionId)
            .orElseThrow();
        return processDeploymentRepository
            .findByProcessDefinitionId(processDefinitionDTO.getId())
            .stream()
            .map(processDeploymentMapper::toDto)
            .collect(Collectors.toList());
    }

    public List<ProcessDeploymentDTO> findActiveByProcessDefinition(String idOrBpmnProcessDefinitionId) {
        ProcessDefinitionDTO processDefinitionDTO = processDefinitionService
            .findByIdOrBpmnProcessDefinitionId(idOrBpmnProcessDefinitionId)
            .orElseThrow();
        return processDeploymentRepository
            .findByProcessDefinitionIdAndStatusIsActive(processDefinitionDTO.getId())
            .stream()
            .map(processDeploymentMapper::toDto)
            .collect(Collectors.toList());
    }

    private org.camunda.bpm.engine.repository.Deployment deployInCamunda(
        ProcessDefinition processDefinition,
        BpmnModelInstance bpmnModelInstance
    ) {
        configureListeners(bpmnModelInstance);
        return repositoryService
            .createDeployment()
            .addModelInstance(processDefinition.getBpmnProcessDefinitionId() + ".bpmn", bpmnModelInstance)
            .name(processDefinition.getBpmnProcessDefinitionId())
            .deploy();
    }

    private void inactivatePreviousProcessDeployments(ProcessDeployment processDeployment) {
        Optional<ProcessDeployment> previousProcessDeployment = processDeploymentRepository.findByProcessDefinitionIdAndStatusIsActive(
            processDeployment.getProcessDefinition().getId()
        );
        if (previousProcessDeployment.isPresent()) {
            inactivate(previousProcessDeployment.get().getId());
        }
    }

    private void configureListeners(BpmnModelInstance modelInstance) {
        ModelElementType processType = modelInstance.getModel().getType(Process.class);
        Process process = (Process) modelInstance.getModelElementsByType(processType).iterator().next();

        if (process.getExtensionElements() == null) {
            process.setExtensionElements(modelInstance.newInstance(ExtensionElements.class));
        }

        {
            CamundaExecutionListener processInstanceEndListener = process
                .getExtensionElements()
                .addExtensionElement(CamundaExecutionListener.class);
            processInstanceEndListener.setAttributeValue("event", "end");
            processInstanceEndListener.setAttributeValue("delegateExpression", "${camundaProcessInstanceEndListener}");
        }

        ModelElementType userTaskType = modelInstance.getModel().getType(UserTask.class);
        Collection<ModelElementInstance> userTaskInstances = modelInstance.getModelElementsByType(userTaskType);

        if (userTaskInstances == null) {
            return;
        }

        userTaskInstances
            .stream()
            .forEach(
                modelElementInstance -> {
                    UserTask userTask = (UserTask) modelElementInstance;

                    if (userTask.getExtensionElements() == null) {
                        userTask.setExtensionElements(modelInstance.newInstance(ExtensionElements.class));
                    }

                    {
                        CamundaTaskListener createListener = userTask.getExtensionElements().addExtensionElement(CamundaTaskListener.class);
                        createListener.setAttributeValue("event", "create");
                        createListener.setAttributeValue("delegateExpression", "${camundaTaskCreateListener}");
                    }

                    {
                        CamundaTaskListener assigmentListener = userTask
                            .getExtensionElements()
                            .addExtensionElement(CamundaTaskListener.class);
                        assigmentListener.setAttributeValue("event", "assignment");
                        assigmentListener.setAttributeValue("delegateExpression", "${camundaTaskAssignmentListener}");
                        userTask.getExtensionElements().getElements().add(assigmentListener);
                    }

                    {
                        CamundaTaskListener completeListener = userTask
                            .getExtensionElements()
                            .addExtensionElement(CamundaTaskListener.class);
                        completeListener.setAttributeValue("event", "complete");
                        completeListener.setAttributeValue("delegateExpression", "${camundaTaskCompleteListener}");
                        userTask.getExtensionElements().getElements().add(completeListener);
                    }

                    {
                        CamundaTaskListener deleteListener = userTask.getExtensionElements().addExtensionElement(CamundaTaskListener.class);
                        deleteListener.setAttributeValue("event", "delete");
                        deleteListener.setAttributeValue("delegateExpression", "${camundaTaskDeleteListener}");
                        userTask.getExtensionElements().getElements().add(deleteListener);
                    }

                    {
                        CamundaTaskListener updateListener = userTask.getExtensionElements().addExtensionElement(CamundaTaskListener.class);
                        updateListener.setAttributeValue("event", "update");
                        updateListener.setAttributeValue("delegateExpression", "${camundaTaskUpdateListener}");
                        userTask.getExtensionElements().getElements().add(updateListener);
                    }
                }
            );
    }
}
