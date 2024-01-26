package org.akip.service;

import org.akip.domain.TaskDefinition;
import org.akip.repository.ProcessDefinitionRepository;
import org.akip.repository.TaskDefinitionRepository;
import org.akip.service.dto.TaskDefinitionDTO;
import org.akip.service.mapper.TaskDefinitionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TaskDefinitionService {

    private final Logger log = LoggerFactory.getLogger(ProcessInstanceService.class);

    private final TaskDefinitionMapper taskDefinitionMapper;

    private final ProcessDefinitionRepository processDefinitionRepository;

    private final TaskDefinitionRepository taskDefinitionRepository;

    public TaskDefinitionService(TaskDefinitionMapper taskDefinitionMapper, ProcessDefinitionRepository processDefinitionRepository, TaskDefinitionRepository taskDefinitionRepository) {
        this.taskDefinitionMapper = taskDefinitionMapper;
        this.processDefinitionRepository = processDefinitionRepository;
        this.taskDefinitionRepository = taskDefinitionRepository;
    }

    public List<TaskDefinitionDTO> findTasksDefinitionByBpmnProcessDefinitionId(String bpmnProcessDefinitionId){
        return taskDefinitionMapper.toDto(taskDefinitionRepository.findByBpmnProcessDefinitionId(bpmnProcessDefinitionId));
    }

    public TaskDefinitionDTO findTaskByBpmnProcessDefinitionIdAndTaskId(String bpmnProcessDefinitionId, String taskDefinitionId){
        return taskDefinitionMapper.toDto(taskDefinitionRepository.findByBpmnProcessDefinitionIdAndTaskId(bpmnProcessDefinitionId, taskDefinitionId).orElseThrow());
    }

    public TaskDefinitionDTO findTaskDefinitionById(Long id){
        return taskDefinitionMapper.toDto(taskDefinitionRepository.findById(id).orElseThrow());
    }

    public TaskDefinitionDTO save(TaskDefinitionDTO taskDefinitionDTO){
        taskDefinitionDTO.setFormVersion(calculeFormVersion(taskDefinitionDTO));
        return taskDefinitionMapper.toDto(taskDefinitionRepository.save(taskDefinitionMapper.toEntity(taskDefinitionDTO)));
    }

    public String calculeFormVersion(TaskDefinitionDTO taskDefinitionDTO){
        if (taskDefinitionDTO.getFormSchema() == null){
            return "0";
        }
        if (taskDefinitionDTO.getId() != null) {
            TaskDefinition taskDefinition = taskDefinitionRepository.findById(taskDefinitionDTO.getId()).get();
            if (taskDefinitionDTO.getFormVersion() != null && !taskDefinitionDTO.getFormSchema().equals(taskDefinition.getFormSchema())){
                return String.valueOf(Integer.parseInt(taskDefinitionDTO.getFormVersion())+1);
            }
            return String.valueOf(Integer.parseInt(taskDefinitionDTO.getFormVersion()));
        }
        return "1";
    }

    public void delete(Long id){
        log.debug("Request to delete TaskDefinition : {}", id);
        taskDefinitionRepository.deleteById(id);
    }
    public List<TaskDefinitionDTO> findAll(){
        return taskDefinitionMapper.toDto(taskDefinitionRepository.findAll());
    }
}
