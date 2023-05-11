package org.akip.delegate;


import org.akip.camunda.CamundaConstants;
import org.akip.delegate.executor.AkipEmailConnectorExecutor;
import org.akip.delegate.executor.AkipEmailConnectorMessageDTO;
import org.akip.domain.enumeration.StatusTaskInstance;
import org.akip.domain.enumeration.TypeTaskInstance;
import org.akip.exception.BadRequestErrorException;
import org.akip.repository.AkipEmailConnectorConfigRepository;
import org.akip.repository.ProcessDefinitionRepository;
import org.akip.service.TaskInstanceService;
import org.akip.service.dto.*;
import org.akip.service.mapper.AkipEmailConnectorConfigMapper;
import org.akip.service.mapper.ProcessDefinitionMapper;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.Expression;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class AkipEmailConnectorDelegate implements JavaDelegate, RedoableDelegate {

    private final Logger log = LoggerFactory.getLogger(AkipEmailConnectorDelegate.class);

    private final ProcessDefinitionRepository processDefinitionRepository;

    private final ProcessDefinitionMapper processDefinitionMapper;

    private final TaskInstanceService taskInstanceService;

    private final AkipEmailConnectorConfigRepository akipEmailConnectorConfigRepository;

    private final AkipEmailConnectorConfigMapper akipEmailConnectorConfigMapper;

    private final AkipEmailConnectorExecutor akipEmailConnectorExecutor;

    private final RuntimeService runtimeService;

    private Expression akipEmailConnectorConfigName;

    public AkipEmailConnectorDelegate(
        ProcessDefinitionRepository processDefinitionRepository,
        ProcessDefinitionMapper processDefinitionMapper,
        TaskInstanceService taskInstanceService,
        AkipEmailConnectorConfigRepository akipEmailConnectorConfigRepository,
        AkipEmailConnectorConfigMapper akipEmailConnectorConfigMapper,
        AkipEmailConnectorExecutor akipEmailConnectorExecutor,
        RuntimeService runtimeService
    ) {
        this.processDefinitionRepository = processDefinitionRepository;
        this.processDefinitionMapper = processDefinitionMapper;
        this.taskInstanceService = taskInstanceService;
        this.akipEmailConnectorConfigRepository = akipEmailConnectorConfigRepository;
        this.akipEmailConnectorConfigMapper = akipEmailConnectorConfigMapper;
        this.akipEmailConnectorExecutor = akipEmailConnectorExecutor;
        this.runtimeService = runtimeService;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        AkipEmailConnectorMessageDTO akipEmailConnectorMessage = buildAndSendEmailFromDelegateExecution(delegateExecution);
        TaskInstanceDTO taskInstanceDTO = delegateExecutionToTaskInstanceDTO(delegateExecution);
        taskInstanceDTO.setDescription(akipEmailConnectorMessage.toMarkdown());
        taskInstanceDTO.setEndTime(Instant.now());
        taskInstanceService.save(taskInstanceDTO);
    }

    private TaskInstanceDTO delegateExecutionToTaskInstanceDTO(DelegateExecution delegateExecution) {
        log.debug("Inicio de genericSendTaskDelegate");
        TaskInstanceDTO taskInstance = new TaskInstanceDTO();
        taskInstance.setTaskId(delegateExecution.getId());
        taskInstance.setName(delegateExecution.getCurrentActivityName());
        taskInstance.setType(TypeTaskInstance.SEND_TASK);
        taskInstance.setExecutionId(delegateExecution.getProcessInstanceId());
        taskInstance.setConnectorName(StringUtils.uncapitalize(this.getClass().getSimpleName()));
        taskInstance.setConnectorConfigName((String) akipEmailConnectorConfigName.getValue(delegateExecution));
        taskInstance.setStatus(StatusTaskInstance.COMPLETED);
        taskInstance.setCreateDate(Instant.now());
        taskInstance.setCreateTime(Instant.now());
        taskInstance.setTaskDefinitionKey(delegateExecution.getActivityInstanceId());
        ProcessInstanceDTO processInstance = new ProcessInstanceDTO();
        processInstance.setId(1L);
        processInstance.setCamundaProcessInstanceId(delegateExecution.getProcessInstanceId());
        taskInstance.setProcessInstance(processInstance);

        ProcessDefinitionDTO processDefinition = processDefinitionRepository
            .findByBpmnProcessDefinitionId(delegateExecution.getProcessDefinitionId().split(":")[0])
            .map(processDefinitionMapper::toDto)
            .orElseThrow();
        taskInstance.setProcessDefinition(processDefinition);

        return taskInstance;
    }

    public void setAkipEmailConnectorConfigName(Expression akipEmailConnectorConfigName) {
        this.akipEmailConnectorConfigName = akipEmailConnectorConfigName;
    }

    public void redo(TaskInstanceDTO taskInstance) {
        if (StringUtils.isBlank(taskInstance.getConnectorConfigName())) {
            throw new BadRequestErrorException("loginProcessosApp.TaskInstance.noActionConfigNameInTheTaskInstance");
        }

        AkipEmailConnectorConfigDTO akipEmailConnectorConfig = akipEmailConnectorConfigRepository
            .findByName(taskInstance.getConnectorConfigName())
            .map(akipEmailConnectorConfigMapper::toDto)
            .orElseThrow();
        IProcessEntity processEntity = (IProcessEntity) runtimeService.getVariable(
            taskInstance.getExecutionId(),
            CamundaConstants.PROCESS_ENTITY
        );
        AkipEmailConnectorMessageDTO akipEmailConnectorMessage = akipEmailConnectorExecutor.buildAndSendMessageFromProcessEntity(akipEmailConnectorConfig, processEntity);
        taskInstance.setDescription(akipEmailConnectorMessage.toMarkdown());
        taskInstance.setEndTime(Instant.now());
        taskInstanceService.save(taskInstance);
    }

    private AkipEmailConnectorMessageDTO buildAndSendEmailFromDelegateExecution(DelegateExecution delegateExecution) {
        AkipEmailConnectorConfigDTO akipEmailConnectorConfig = akipEmailConnectorConfigRepository
            .findByName((String) akipEmailConnectorConfigName.getValue(delegateExecution))
            .map(akipEmailConnectorConfigMapper::toDto)
            .orElseThrow();

        //TODO: The cast for the original class is causing a weird error.
        Object processEntity = delegateExecution.getVariable(CamundaConstants.PROCESS_ENTITY);
        if (processEntity != null) {
            return akipEmailConnectorExecutor.buildAndSendMessageFromProcessEntity(akipEmailConnectorConfig, processEntity);
        }

        //TODO: The cast for the original class is causing a weird error.
        Object processInstance = delegateExecution.getVariable(CamundaConstants.PROCESS_INSTANCE);
        return akipEmailConnectorExecutor.buildAndSendMessageFromProcessInstance(akipEmailConnectorConfig, processInstance);
    }
}
