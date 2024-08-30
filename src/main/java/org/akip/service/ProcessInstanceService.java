package org.akip.service;


import org.akip.camunda.CamundaConstants;
import org.akip.domain.*;
import org.akip.domain.enumeration.StatusProcessInstance;
import org.akip.domain.enumeration.StatusTaskInstance;
import org.akip.repository.*;
import org.akip.security.SecurityUtils;
import org.akip.service.dto.*;
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

    private final ProcessDeploymentService processDeploymentService;

    private final TaskInstanceService taskInstanceService;

    private final ProcessDefinitionRepository processDefinitionRepository;

    private final ProcessDeploymentRepository processDeploymentRepository;

    private final ProcessInstanceRepository processInstanceRepository;

    private final ProcessInstanceMapper processInstanceMapper;

    private final RuntimeService runtimeService;

    private final AttachmentEntityRepository attachmentEntityRepository;

    private final AttachmentRepository attachmentRepository;
    private final NoteRepository noteRepository;

    private final NoteEntityRepository noteEntityRepository;

    private final TemporaryProcessInstanceRepository temporaryProcessInstanceRepository;

    public ProcessInstanceService(
        ProcessDeploymentService processDeploymentService,
        TaskInstanceService taskInstanceService,
        ProcessDefinitionRepository processDefinitionRepository,
        ProcessDeploymentRepository processDeploymentRepository,
        ProcessInstanceRepository processInstanceRepository,
        ProcessInstanceMapper processInstanceMapper,
        RuntimeService runtimeService,
        AttachmentEntityRepository attachmentEntityRepository,
        AttachmentRepository attachmentRepository,
        NoteRepository noteRepository,
        NoteEntityRepository noteEntityRepository,
        TemporaryProcessInstanceRepository temporaryProcessInstanceRepository) {
        this.processDeploymentService = processDeploymentService;
        this.taskInstanceService = taskInstanceService;
        this.processDefinitionRepository = processDefinitionRepository;
        this.processDeploymentRepository = processDeploymentRepository;
        this.processInstanceRepository = processInstanceRepository;
        this.processInstanceMapper = processInstanceMapper;
        this.runtimeService = runtimeService;
        this.attachmentEntityRepository = attachmentEntityRepository;
        this.attachmentRepository = attachmentRepository;
        this.noteRepository = noteRepository;
        this.noteEntityRepository = noteEntityRepository;
        this.temporaryProcessInstanceRepository = temporaryProcessInstanceRepository;
    }

    public ProcessInstanceDTO create(ProcessInstanceDTO processInstanceDTO) {
        log.debug("Request to create processInstance : {}", processInstanceDTO);
        if (processInstanceDTO.getTenant() == null) {
            return createWithoutTenant(processInstanceDTO);
        }
        return createWithTenant(processInstanceDTO);
    }

    public ProcessInstance create(String bpmnProcessDefinitionId, String businessKey, IProcessEntity processEntity) {
        return create(bpmnProcessDefinitionId, businessKey, processEntity, null);
    }

    public ProcessInstance create(String bpmnProcessDefinitionId, String businessKey, IProcessEntity processEntity, Tenant tenant) {
        if (tenant == null) {
            return createWithoutTenant(bpmnProcessDefinitionId, businessKey, processEntity);
        }

        return createWithTenant(bpmnProcessDefinitionId, businessKey, processEntity, tenant);
    }

    private ProcessInstanceDTO createWithTenant(ProcessInstanceDTO processInstanceDTO) {
        log.debug("Request to create processInstance : {}", processInstanceDTO);
        ProcessInstance processInstance = processInstanceMapper.toEntity(processInstanceDTO);

        ProcessDefinition processDefinition = processDefinitionRepository
                .findById(processInstanceDTO.getProcessDefinition().getId())
                .orElseThrow();
        processInstance.setProcessDefinition(processDefinition);

        ProcessDeployment processDeployment = processDeploymentRepository
                .findByProcessDefinitionIdAndStatusIsActiveAndTenantId(processDefinition.getId(), processInstance.getTenant().getId())
                .orElse(processDeploymentRepository
                        .findByProcessDefinitionIdAndStatusIsActiveAndTenantIsNull(processDefinition.getId())
                        .orElseThrow());

        processInstance.setUsername(SecurityUtils.getCurrentUserLogin().orElseThrow());
        processInstance.setCamundaProcessDefinitionId(processDeployment.getCamundaProcessDefinitionId());
        processInstance.setCamundaDeploymentId(processDeployment.getCamundaDeploymentId());
        processInstance.setProps(processDeployment.getProps());
        processInstance.setStartDate(LocalDateTime.now());
        processInstance.setStatus(StatusProcessInstance.RUNNING);

        Map<String, Object> params = new HashMap<>();
        params.put(CamundaConstants.PROCESS_INSTANCE, processInstanceMapper.toDto(processInstance));

        org.camunda.bpm.engine.runtime.ProcessInstance camundaProcessInstance = runtimeService
                .createProcessInstanceById(processDeployment.getCamundaProcessDefinitionId())
                .businessKey(processInstance.getBusinessKey())
                .setVariables(params)
                .execute();


        processInstance.setCamundaProcessInstanceId(camundaProcessInstance.getProcessInstanceId());
        ProcessInstanceDTO processInstanceSaved = processInstanceMapper.toDto(processInstanceRepository.save(processInstance));
        synchronizeAttachmentsAndNotesAndUpdateTemporaryProcessInstance(processInstanceDTO.getTemporaryProcessInstance(), processInstanceSaved);
        runtimeService.setVariable(camundaProcessInstance.getProcessInstanceId(), CamundaConstants.PROCESS_INSTANCE, processInstanceSaved);
        return processInstanceSaved;
    }

    private ProcessInstanceDTO createWithoutTenant(ProcessInstanceDTO processInstanceDTO) {
        log.debug("Request to create processInstance : {}", processInstanceDTO);
        ProcessDefinition processDefinition = processDefinitionRepository
                .findById(processInstanceDTO.getProcessDefinition().getId())
                .orElseThrow();
        ProcessDeployment processDeployment = processDeploymentRepository
                .findByProcessDefinitionIdAndStatusIsActiveAndTenantIsNull(processDefinition.getId())
                .orElseThrow();

        ProcessInstance processInstance = processInstanceMapper.toEntity(processInstanceDTO);
        processInstance.setProcessDefinition(processDefinition);
        processInstance.setUsername(SecurityUtils.getCurrentUserLogin().orElseThrow());
        processInstance.setCamundaProcessDefinitionId(processDeployment.getCamundaProcessDefinitionId());
        processInstance.setCamundaDeploymentId(processDeployment.getCamundaDeploymentId());
        processInstance.setProps(processDeployment.getProps());
        processInstance.setStartDate(LocalDateTime.now());
        processInstance.setStatus(StatusProcessInstance.RUNNING);

        Map<String, Object> params = new HashMap<>();
        params.put(CamundaConstants.PROCESS_INSTANCE, processInstanceMapper.toDto(processInstance));

        org.camunda.bpm.engine.runtime.ProcessInstance camundaProcessInstance = runtimeService
                .createProcessInstanceById(processDeployment.getCamundaProcessDefinitionId())
                .businessKey(processInstance.getBusinessKey())
                .setVariables(params)
                .execute();


        processInstance.setCamundaProcessInstanceId(camundaProcessInstance.getProcessInstanceId());
        ProcessInstanceDTO processInstanceSaved = processInstanceMapper.toDto(processInstanceRepository.save(processInstance));
        synchronizeAttachmentsAndNotesAndUpdateTemporaryProcessInstance(processInstanceDTO.getTemporaryProcessInstance(), processInstanceSaved);
        runtimeService.setVariable(camundaProcessInstance.getProcessInstanceId(), CamundaConstants.PROCESS_INSTANCE, processInstanceSaved);
        return processInstanceSaved;
    }

    private ProcessInstance createWithoutTenant(String bpmnProcessDefinitionId, String businessKey, IProcessEntity processEntity) {
        log.debug("Request to create a processInstance by bpmnProcessDefinitionId: {}", bpmnProcessDefinitionId);

        ProcessDefinition processDefinition = processDefinitionRepository
                .findByBpmnProcessDefinitionId(bpmnProcessDefinitionId)
                .orElseThrow();
        ProcessDeployment processDeployment = processDeploymentRepository
                .findByProcessDefinitionIdAndStatusIsActiveAndTenantIsNull(processDefinition.getId())
                .orElseThrow();

        ProcessInstance processInstance = new ProcessInstance();
        processInstance.setProcessDefinition(processDefinition);
        processInstance.setBusinessKey(businessKey);
        processInstance.setUsername(SecurityUtils.getCurrentUserLogin().orElseThrow());
        processInstance.setCamundaProcessDefinitionId(processDeployment.getCamundaProcessDefinitionId());
        processInstance.setCamundaDeploymentId(processDeployment.getCamundaDeploymentId());
        processInstance.setProps(processDeployment.getProps());
        processInstance.setStartDate(LocalDateTime.now());
        processInstance.setStatus(StatusProcessInstance.RUNNING);

        processEntity.setProcessInstance(processInstanceMapper.toDto(processInstance));

        Map<String, Object> params = new HashMap<>();
        params.put(CamundaConstants.PROCESS_ENTITY, processEntity);

        org.camunda.bpm.engine.runtime.ProcessInstance camundaProcessInstance = runtimeService
                .createProcessInstanceById(processDeployment.getCamundaProcessDefinitionId())
                .businessKey(businessKey)
                .setVariables(params)
                .execute();

        processInstance.setCamundaProcessInstanceId(camundaProcessInstance.getProcessInstanceId());

        ProcessInstance processInstanceSaved = processInstanceRepository.save(processInstance);
        synchronizeAttachmentsAndNotesAndUpdateTemporaryProcessInstance(processEntity.getProcessInstance().getTemporaryProcessInstance(), processInstanceMapper.toDto(processInstanceSaved));

        return processInstanceSaved;
    }

    private ProcessInstance createWithTenant(
            String bpmnProcessDefinitionId,
            String businessKey,
            IProcessEntity processEntity,
            Tenant tenant
    ) {
        log.debug("Request to create a processInstance by bpmnProcessDefinitionId: {}", bpmnProcessDefinitionId);

        ProcessDefinition processDefinition = processDefinitionRepository
                .findByBpmnProcessDefinitionId(bpmnProcessDefinitionId)
                .orElseThrow();

        ProcessDeployment processDeployment = processDeploymentRepository
                .findByProcessDefinitionIdAndStatusIsActiveAndTenantId(processDefinition.getId(), tenant.getId())
                .orElse(processDeploymentRepository
                        .findByProcessDefinitionIdAndStatusIsActiveAndTenantIsNull(processDefinition.getId())
                        .orElseThrow());

        ProcessInstance processInstance = new ProcessInstance();
        processInstance.setProcessDefinition(processDefinition);
        processInstance.setBusinessKey(businessKey);
        processInstance.setUsername(SecurityUtils.getCurrentUserLogin().orElseThrow());
        processInstance.setTenant(tenant);
        processInstance.setCamundaProcessDefinitionId(processDeployment.getCamundaProcessDefinitionId());
        processInstance.setCamundaDeploymentId(processDeployment.getCamundaDeploymentId());
        processInstance.setProps(processDeployment.getProps());
        processInstance.setStartDate(LocalDateTime.now());
        processInstance.setStatus(StatusProcessInstance.RUNNING);

        processEntity.setProcessInstance(processInstanceMapper.toDto(processInstance));

        Map<String, Object> params = new HashMap<>();
        params.put(CamundaConstants.PROCESS_ENTITY, processEntity);

        org.camunda.bpm.engine.runtime.ProcessInstance camundaProcessInstance = runtimeService
                .createProcessInstanceById(processDeployment.getCamundaProcessDefinitionId())
                .businessKey(businessKey)
                .setVariables(params)
                .execute();

        processInstance.setCamundaProcessInstanceId(camundaProcessInstance.getProcessInstanceId());

        ProcessInstance processInstanceSaved = processInstanceRepository.save(processInstance);
        synchronizeAttachmentsAndNotesAndUpdateTemporaryProcessInstance(processEntity.getProcessInstance().getTemporaryProcessInstance(), processInstanceMapper.toDto(processInstanceSaved));

        return processInstanceSaved;
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

    public ProcessInstance findAndUpdateProcessInstance(String processDefinitionIdNew, String processInstanceId) {
        Optional<ProcessInstance> processInstanceOpt = processInstanceRepository.findByCamundaProcessInstanceId(processInstanceId);
        ProcessInstance processInstance = processInstanceOpt.get();
        processInstance.setCamundaProcessDefinitionId(processDefinitionIdNew);
        return processInstanceRepository.save(processInstance);
    }

    private void synchronizeAttachments(
            Long temporaryProcessInstanceId,
            Long processInstanceId
    ) {
        List<Attachment> attachments = attachmentRepository.findByEntityNameAndEntityId(
                TemporaryProcessInstance.class.getSimpleName(),
                temporaryProcessInstanceId
        );
        if (attachments.isEmpty()) {
            return;
        }

        attachments.forEach(
                attachment -> {
                    AttachmentEntity attachmentEntityProcessInstance = new AttachmentEntity();
                    attachmentEntityProcessInstance.setAttachment(attachment);
                    attachmentEntityProcessInstance.setEntityName(ProcessInstance.class.getSimpleName());
                    attachmentEntityProcessInstance.setEntityId(processInstanceId);
                    attachmentEntityRepository.save(attachmentEntityProcessInstance);
                }
        );
    }

    private void synchronizeNotes(
            Long temporaryProcessInstanceId,
            Long processInstanceId
    ) {
        List<Note> notes = noteRepository.findByEntityNameAndEntityId(
                TemporaryProcessInstance.class.getSimpleName(),
                temporaryProcessInstanceId
        );
        if (notes.isEmpty()) {
            return;
        }
        notes.forEach(
                note -> {
                    NoteEntity noteEntityProcessInstance = new NoteEntity();
                    noteEntityProcessInstance.setNote(note);
                    noteEntityProcessInstance.setEntityName(ProcessInstance.class.getSimpleName());
                    noteEntityProcessInstance.setEntityId(processInstanceId);
                    noteEntityRepository.save(noteEntityProcessInstance);
                }
        );
    }

    private void synchronizeAttachmentsAndNotesAndUpdateTemporaryProcessInstance(TemporaryProcessInstanceDTO temporaryProcessInstanceDTO, ProcessInstanceDTO processInstanceSaved) {
        if (temporaryProcessInstanceDTO == null) {
            return;
        }
        synchronizeAttachments(temporaryProcessInstanceDTO.getId(), processInstanceSaved.getId());
        synchronizeNotes(temporaryProcessInstanceDTO.getId(), processInstanceSaved.getId());
        TemporaryProcessInstance temporaryProcessInstance = temporaryProcessInstanceRepository.findById(temporaryProcessInstanceDTO.getId()).get();
        temporaryProcessInstance.setProcessInstance(new ProcessInstance());
        temporaryProcessInstance.getProcessInstance().setId(processInstanceSaved.getId());
        temporaryProcessInstanceRepository.save(temporaryProcessInstance);
    }

}
