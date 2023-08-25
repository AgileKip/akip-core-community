package org.akip.service;

import org.akip.domain.ProcessDefinition;
import org.akip.domain.ProcessTimelineDefinition;
import org.akip.exception.BadRequestErrorException;
import org.akip.repository.ProcessDefinitionRepository;
import org.akip.repository.ProcessDeploymentRepository;
import org.akip.repository.ProcessTimelineDefinitionRepository;
import org.akip.service.dto.ProcessTimelineDefinitionDTO;
import org.akip.service.dto.TaskInstanceDTO;
import org.akip.service.mapper.ProcessTimelineDefinitionMapper;
import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.UserTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProcessTimelineDefinitionService {

    @Autowired
    private ProcessDefinitionRepository processDefinitionRepository;

    @Autowired
    private ProcessDeploymentRepository processDeploymentRepository;

    @Autowired
    private ProcessTimelineDefinitionRepository processTimelineDefinitionRepository;

    private final ProcessTimelineDefinitionMapper processTimelineDefinitionMapper;

    public ProcessTimelineDefinitionService(ProcessTimelineDefinitionMapper processTimelineDefinitionMapper) {
        this.processTimelineDefinitionMapper = processTimelineDefinitionMapper;
    }

    public List<ProcessTimelineDefinitionDTO> getByBpmnProcessDefinitionId(String bpmnProcessDefinitionId) {
        List<ProcessTimelineDefinitionDTO> processTimelineDefinitions = findAllByProcessDefinitionBpmnProcessDefinitionId(
            bpmnProcessDefinitionId
        );

        if (processTimelineDefinitions.isEmpty()) {
            throw new BadRequestErrorException("error.timeline.undefinedTimeline");
        }

        return processTimelineDefinitions;
    }

    public ProcessTimelineDefinitionDTO save(
        String processDefinitionBusinessKey,
        ProcessTimelineDefinitionDTO processTimelineDefinitionDTO
    ) {
        AtomicInteger stepCount = new AtomicInteger();
        Optional<ProcessDefinition> processDefinition = processDefinitionRepository.findByBpmnProcessDefinitionId(
            processDefinitionBusinessKey
        );
        ProcessTimelineDefinition processTimelineDefinition = processTimelineDefinitionMapper.toEntity(processTimelineDefinitionDTO);
        processTimelineDefinition
            .getItems()
            .forEach(
                item -> {
                    item.setProcessTimelineDefinition(processTimelineDefinition);
                    item.setStep(stepCount.getAndIncrement());
                }
            );
        processTimelineDefinition.setProcessDefinition(processDefinition.get());
        return processTimelineDefinitionMapper.toDto(processTimelineDefinitionRepository.save(processTimelineDefinition));
    }

    @Transactional(readOnly = true)
    public List<ProcessTimelineDefinitionDTO> findAllByProcessDefinitionBpmnProcessDefinitionId(String processDefinitionBusinessKey) {
        return processTimelineDefinitionRepository
            .findAllByProcessDefinitionBpmnProcessDefinitionId(processDefinitionBusinessKey)
            .stream()
            .map(processTimelineDefinitionMapper::toDto)
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<ProcessTimelineDefinitionDTO> findOne(Long timelineDefinitionId) {
        return processTimelineDefinitionRepository.findById(timelineDefinitionId).map(processTimelineDefinitionMapper::toDto);
    }

    public void delete(Long timelineDefinitionId) {
        processTimelineDefinitionRepository.deleteById(timelineDefinitionId);
    }

    public List<TaskInstanceDTO> getBpmnUserTasks(String processDefinitionBusinessKey) {
        ProcessDefinition processDefinition = processDefinitionRepository
            .findByBpmnProcessDefinitionId(processDefinitionBusinessKey)
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
}
