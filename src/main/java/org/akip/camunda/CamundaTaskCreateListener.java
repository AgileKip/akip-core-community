package org.akip.camunda;

import org.akip.camunda.form.CamundaFormFieldDef;
import org.akip.camunda.form.CamundaFormFieldValidationConstraintDef;
import org.akip.domain.ProcessDefinition;
import org.akip.domain.enumeration.StatusTaskInstance;
import org.akip.domain.enumeration.TypeTaskInstance;
import org.akip.repository.ProcessDefinitionRepository;
import org.akip.service.TaskInstanceService;
import org.akip.service.dto.ProcessInstanceDTO;
import org.akip.service.dto.TaskInstanceDTO;
import org.akip.service.mapper.ProcessDefinitionMapper;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.FormFieldValidationConstraint;
import org.camunda.bpm.engine.impl.form.type.EnumFormType;
import org.camunda.bpm.engine.impl.persistence.entity.TaskEntity;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class CamundaTaskCreateListener implements TaskListener {

    private final TaskInstanceService taskInstanceService;

    private final ProcessDefinitionRepository processDefinitionRepository;

    private final ProcessDefinitionMapper processDefinitionMapper;

    public CamundaTaskCreateListener(
        TaskInstanceService taskInstanceService,
        ProcessDefinitionRepository processDefinitionRepository,
        ProcessDefinitionMapper processDefinitionMapper
    ) {
        this.taskInstanceService = taskInstanceService;
        this.processDefinitionRepository = processDefinitionRepository;
        this.processDefinitionMapper = processDefinitionMapper;
    }

    public void notify(DelegateTask delegateTask) {
        TaskInstanceDTO taskInstanceDTO = delegateTaskToTaskInstanceDTO(delegateTask);
        taskInstanceService.save(taskInstanceDTO);
    }

    private TaskInstanceDTO delegateTaskToTaskInstanceDTO(DelegateTask delegateTask) {
        TaskInstanceDTO taskInstanceDTO = new TaskInstanceDTO();
        taskInstanceDTO.setTaskId(delegateTask.getId());
        taskInstanceDTO.setName(delegateTask.getName());
        taskInstanceDTO.setStatus(StatusTaskInstance.NEW);
        taskInstanceDTO.setType(TypeTaskInstance.USER_TASK);
        taskInstanceDTO.setDescription(delegateTask.getDescription());
        taskInstanceDTO.setCreateDate(Instant.now());
        taskInstanceDTO.setCreateTime(Instant.now());
        taskInstanceDTO.setAssignee(delegateTask.getAssignee());
        taskInstanceDTO.setExecutionId(delegateTask.getExecutionId());
        taskInstanceDTO.setTaskDefinitionKey(delegateTask.getTaskDefinitionKey());
        //taskInstanceDTO.setSuspended(delegateTask.getCaseExecution().isSuspended());
        taskInstanceDTO.setPriority(delegateTask.getPriority());

        ((TaskEntity) delegateTask).getTaskDefinition()
            .getCandidateGroupIdExpressions()
            .stream()
            .forEach(
                expression -> {
                    taskInstanceDTO.getCandidateGroups().add(expression.getExpressionText());
                }
            );

        ProcessInstanceDTO processInstance = new ProcessInstanceDTO();
        processInstance.setId(1L);
        processInstance.setCamundaProcessInstanceId(delegateTask.getProcessInstanceId());
        taskInstanceDTO.setProcessInstance(processInstance);

        Optional<ProcessDefinition> optionalProcessDefinition = processDefinitionRepository.findByBpmnProcessDefinitionId(
            delegateTask.getProcessDefinitionId().split(":")[0]
        );
        if (optionalProcessDefinition.isPresent()) {
            taskInstanceDTO.setProcessDefinition(processDefinitionMapper.toDto(optionalProcessDefinition.get()));
        }

        taskInstanceDTO.setFormFields(extractFormFields(delegateTask));

        return taskInstanceDTO;
    }

    private List<CamundaFormFieldDef> extractFormFields(DelegateTask delegateTask) {
        TaskEntity taskEntity = (TaskEntity) delegateTask;
        if (taskEntity.getTaskDefinition().getTaskFormHandler() == null) {
            return Collections.emptyList();
        }
        return taskEntity
                .getTaskDefinition()
                .getTaskFormHandler()
                .createTaskForm(taskEntity)
                .getFormFields()
                .stream()
                .map(this::toCamundaFormFieldDef)
                .collect(Collectors.toList());
    }

    private CamundaFormFieldDef toCamundaFormFieldDef(FormField formField) {
        CamundaFormFieldDef camundaFormFieldDef = new CamundaFormFieldDef();
        camundaFormFieldDef.setId(formField.getId());
        camundaFormFieldDef.setLabel(formField.getLabel());
        camundaFormFieldDef.setType(formField.getTypeName());
        if (formField.getValue() != null) {
            camundaFormFieldDef.setDefaultValue(formField.getValue().getValue());
        }
        camundaFormFieldDef.setValidationConstraints(
                formField.getValidationConstraints().stream().map(this::toCamundaFormFieldValidationConstraintDef).collect(Collectors.toList())
        );
        camundaFormFieldDef.setProperties(formField.getProperties());
        if (formField.getType() instanceof EnumFormType) {
            camundaFormFieldDef.setValues(((EnumFormType) formField.getType()).getValues());
        }
        return camundaFormFieldDef;
    }

    private CamundaFormFieldValidationConstraintDef toCamundaFormFieldValidationConstraintDef(
            FormFieldValidationConstraint formFieldValidationConstraint
    ) {
        CamundaFormFieldValidationConstraintDef camundaFormFieldValidationConstraintDef = new CamundaFormFieldValidationConstraintDef();
        camundaFormFieldValidationConstraintDef.setName(formFieldValidationConstraint.getName());
        camundaFormFieldValidationConstraintDef.setConfiguration(formFieldValidationConstraint.getConfiguration());
        return camundaFormFieldValidationConstraintDef;
    }
}
