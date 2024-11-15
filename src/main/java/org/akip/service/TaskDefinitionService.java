package org.akip.service;

import org.akip.domain.TaskDefinition;
import org.akip.repository.TaskDefinitionRepository;
import org.akip.service.dto.TaskDefinitionDTO;
import org.akip.service.mapper.TaskDefinitionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TaskDefinitionService {

    private final Logger log = LoggerFactory.getLogger(TaskDefinitionService.class);

    private final TaskDefinitionMapper taskDefinitionMapper;

    private final TaskDefinitionRepository taskDefinitionRepository;

    public TaskDefinitionService(TaskDefinitionMapper taskDefinitionMapper, TaskDefinitionRepository taskDefinitionRepository) {
        this.taskDefinitionMapper = taskDefinitionMapper;
        this.taskDefinitionRepository = taskDefinitionRepository;
    }

    public List<TaskDefinition> findByProcessDefinition(String bpmnProcessDefinitionId){
        log.debug("Request to get TaskDefinitions of the ProcessDefinition : {}", bpmnProcessDefinitionId);
        return taskDefinitionRepository.findByBpmnProcessDefinitionId(bpmnProcessDefinitionId);
        }

    public Optional<TaskDefinitionDTO> findByBpmnProcessDefinitionIdAndTaskId(String bpmnProcessDefinitionId, String taskDefinitionId) {
        return taskDefinitionRepository
                .findByBpmnProcessDefinitionIdAndTaskId(bpmnProcessDefinitionId, taskDefinitionId)
                .map(taskDefinitionMapper::toDto);
    }

    public TaskDefinitionDTO findById(Long id){
        return taskDefinitionMapper.toDto(taskDefinitionRepository.findById(id).orElseThrow());
    }

    public String findDocumentationById(Long id){
        return taskDefinitionRepository.findDocumentationById(id).orElseThrow();
    }

    public TaskDefinitionDTO save(TaskDefinitionDTO taskDefinitionDTO){
        return taskDefinitionMapper.toDto(taskDefinitionRepository.save(taskDefinitionMapper.toEntity(taskDefinitionDTO)));
    }

    public TaskDefinitionDTO update(TaskDefinitionDTO taskDefinitionDTO){
        TaskDefinition taskDefinition = taskDefinitionRepository.findById(taskDefinitionDTO.getId()).orElseThrow();
        taskDefinition.setTaskId(taskDefinitionDTO.getTaskId());
        taskDefinition.setBpmnProcessDefinitionId(taskDefinitionDTO.getBpmnProcessDefinitionId());
        taskDefinition.setName(taskDefinitionDTO.getName());
        return taskDefinitionMapper.toDto(taskDefinitionRepository.save(taskDefinition));
    }

    public void updateDocumentation(String documentation, Long id){
        log.debug("Request to update documentation of TaskDefinition : {}", id);
        taskDefinitionRepository.updateDocumentationById(documentation, id);
    }

    public void delete(Long id){
        log.debug("Request to delete TaskDefinition : {}", id);
        taskDefinitionRepository.deleteById(id);
    }
}
