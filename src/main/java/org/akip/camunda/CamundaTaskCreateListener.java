package org.akip.camunda;

import org.akip.domain.ProcessDefinition;
import org.akip.domain.ProcessDeployment;
import org.akip.domain.enumeration.ProcessVisibilityType;
import org.akip.domain.enumeration.StatusTaskInstance;
import org.akip.domain.enumeration.TypeTaskInstance;
import org.akip.repository.*;
import org.akip.service.TaskInstanceService;
import org.akip.service.dto.ProcessDefinitionDTO;
import org.akip.service.dto.ProcessInstanceDTO;
import org.akip.service.dto.TaskInstanceDTO;
import org.akip.service.mapper.ProcessDefinitionMapper;
import org.akip.service.mapper.TaskDefinitionMapper;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class CamundaTaskCreateListener implements TaskListener {

    private final TaskInstanceService taskInstanceService;

    private final ProcessDefinitionRepository processDefinitionRepository;

    private final ProcessDefinitionMapper processDefinitionMapper;

    private final TaskDefinitionMapper taskDefinitionMapper;

    private final TaskDefinitionRepository taskDefinitionRepository;

    private final ProcessRoleRepository processRoleRepository;

    private final ProcessDeploymentRepository processDeploymentRepository;

    private final TenantRoleRepository tenantRoleRepository;

    public CamundaTaskCreateListener(
            TaskInstanceService taskInstanceService,
            ProcessDefinitionRepository processDefinitionRepository,
            ProcessDefinitionMapper processDefinitionMapper,
            TaskDefinitionRepository taskDefinitionRepository, TaskDefinitionMapper taskDefinitionMapper,
            ProcessRoleRepository processRoleRepository,
            ProcessDeploymentRepository processDeploymentRepository,
            TenantRoleRepository tenantRoleRepository
    ) {
        this.taskInstanceService = taskInstanceService;
        this.processDefinitionRepository = processDefinitionRepository;
        this.processDefinitionMapper = processDefinitionMapper;
        this.taskDefinitionRepository = taskDefinitionRepository;
        this.taskDefinitionMapper = taskDefinitionMapper;
        this.processRoleRepository = processRoleRepository;
        this.processDeploymentRepository = processDeploymentRepository;
        this.tenantRoleRepository = tenantRoleRepository;
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
        taskInstanceDTO.setCreateDate(Instant.now());
        taskInstanceDTO.setCreateTime(Instant.now());
        taskInstanceDTO.setAssignee(delegateTask.getAssignee());
        taskInstanceDTO.setExecutionId(delegateTask.getExecutionId());
        taskInstanceDTO.setTaskDefinitionKey(delegateTask.getTaskDefinitionKey());
        taskInstanceDTO.setPriority(delegateTask.getPriority());

        delegateTask.getCandidates()
                .stream()
                .filter(identityLink -> identityLink.getGroupId() != null)
                .forEach(identityLink -> taskInstanceDTO.getCandidateGroups().add(identityLink.getGroupId()));

        ProcessInstanceDTO processInstance = new ProcessInstanceDTO();
        processInstance.setId(1L);
        processInstance.setCamundaProcessInstanceId(delegateTask.getProcessInstanceId());
        taskInstanceDTO.setProcessInstance(processInstance);

        ProcessDefinitionDTO processDefinitionDTO = processDefinitionRepository
                .findByBpmnProcessDefinitionId(delegateTask.getProcessDefinitionId().split(":")[0])
                .map(processDefinitionMapper::toDto)
                .get();
        mountComputedCandidateGroups(processDefinitionDTO, taskInstanceDTO);
        taskInstanceDTO.setProcessDefinition(processDefinitionDTO);
        taskInstanceDTO.setTaskDefinition(taskDefinitionMapper.toDto(taskDefinitionRepository.findByBpmnProcessDefinitionIdAndTaskId(processDefinitionDTO.getBpmnProcessDefinitionId(), delegateTask.getTaskDefinitionKey()).orElseThrow()));

        return taskInstanceDTO;
    }

    private void mountComputedCandidateGroups(ProcessDefinitionDTO processDefinitionDTO ,TaskInstanceDTO taskInstanceDTO){
        if (taskInstanceDTO.getCandidateGroups().isEmpty()){
            taskInstanceDTO.getCandidateGroups().add("*");
        }

        taskInstanceDTO.getComputedCandidateGroups().addAll(calculateCandidateGroups(processDefinitionMapper.toEntity(processDefinitionDTO), taskInstanceDTO.getCandidateGroups()));
    }

    private List<String> calculateCandidateGroups(ProcessDefinition processDefinition, List<String> candidateGroups){

        if (processDefinition.getProcessVisibilityType() == ProcessVisibilityType.PRIVATE){
            return candidateGroups
                    .stream()
                    .map(candidateGroup -> processDefinition.getBpmnProcessDefinitionId() + "." + candidateGroup)
                    .collect(Collectors.toList());
        }

        if (processDefinition.getProcessVisibilityType() == ProcessVisibilityType.INTERNAL){

            ProcessDeployment processDeployment = processDeploymentRepository.findByProcessDefinitionIdAndStatusIsActiveAndTenantIsNotNull(processDefinition.getId()).get();

            return candidateGroups
                    .stream()
                    .map(candidateGroup -> processDeployment.getTenant().getIdentifier() + "." + candidateGroup)
                    .collect(Collectors.toList());
        }

        return candidateGroups;
    }
}
