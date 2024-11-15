package org.akip.service;

import org.akip.domain.ProcessDefinition;
import org.akip.domain.TaskDefinition;
import org.akip.repository.FormDefinitionRepository;
import org.akip.repository.ProcessDefinitionRepository;
import org.akip.repository.TaskDefinitionRepository;
import org.akip.service.dto.FormDefinitionDTO;
import org.akip.service.mapper.FormDefinitionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class FormDefinitionService {

    private final Logger log = LoggerFactory.getLogger(FormDefinitionService.class);

    private final FormDefinitionRepository formDefinitionRepository;

    private final ProcessDefinitionRepository processDefinitionRepository;

    private final TaskDefinitionRepository taskDefinitionRepository;

    private final FormDefinitionMapper formDefinitionMapper;

    public FormDefinitionService(FormDefinitionRepository formDefinitionRepository, ProcessDefinitionRepository processDefinitionRepository, TaskDefinitionRepository taskDefinitionRepository, FormDefinitionMapper formDefinitionMapper) {
        this.formDefinitionRepository = formDefinitionRepository;
        this.processDefinitionRepository = processDefinitionRepository;
        this.taskDefinitionRepository = taskDefinitionRepository;
        this.formDefinitionMapper = formDefinitionMapper;
    }


    public Optional<FormDefinitionDTO> findById(Long id){
        return formDefinitionRepository.findById(id).map(formDefinitionMapper::toDto);
    }

    public Optional<FormDefinitionDTO> findByProcessDefinitionId(Long processDefinitionId) {
        return formDefinitionRepository.findByProcessDefinitionId(processDefinitionId).map(formDefinitionMapper::toDto);
    }

    public Optional<FormDefinitionDTO> findByTaskDefinitionId(Long taskDefinitionId) {
        return formDefinitionRepository.findByTaskDefinitionId(taskDefinitionId).map(formDefinitionMapper::toDto);
    }

    public FormDefinitionDTO saveWithProcessDefinition(Long processDefinitionId, FormDefinitionDTO formDefinitionDTO){
        calculateFormVersion(formDefinitionDTO);
        FormDefinitionDTO formDefinitionSaved = formDefinitionMapper.toDto(formDefinitionRepository.save(formDefinitionMapper.toEntity(formDefinitionDTO)));
        Optional<ProcessDefinition> processDefinition = processDefinitionRepository.findById(processDefinitionId);
        if (processDefinition.isPresent()){
            processDefinition.get().setStartFormDefinition(formDefinitionMapper.toEntity(formDefinitionSaved));
        }
        return formDefinitionSaved;
    }

    public FormDefinitionDTO save(FormDefinitionDTO formDefinitionDTO){
        calculateFormVersion(formDefinitionDTO);
        return formDefinitionMapper.toDto(formDefinitionRepository.save(formDefinitionMapper.toEntity(formDefinitionDTO)));
    }

    public FormDefinitionDTO saveWithTaskDefinition(Long taskDefinitionId, FormDefinitionDTO formDefinitionDTO){
        calculateFormVersion(formDefinitionDTO);
        FormDefinitionDTO formDefinitionSaved = formDefinitionMapper.toDto(formDefinitionRepository.save(formDefinitionMapper.toEntity(formDefinitionDTO)));
        Optional<TaskDefinition> taskDefinition = taskDefinitionRepository.findById(taskDefinitionId);
        if (taskDefinition.isPresent()){
            taskDefinition.get().setFormDefinition(formDefinitionMapper.toEntity(formDefinitionSaved));
        }
        return formDefinitionSaved;
    }

    public void delete(Long id){
        Optional<ProcessDefinition> processDefinition = processDefinitionRepository.findByStartFormDefinitionId(id);
        if (processDefinition.isPresent()){
            processDefinition.get().setStartFormDefinition(null);
            processDefinitionRepository.save(processDefinition.get());
        }

        Optional<TaskDefinition> taskDefinition = taskDefinitionRepository.findByFormDefinitionId(id);
        if (taskDefinition.isPresent()){
            taskDefinition.get().setFormDefinition(null);
            taskDefinitionRepository.save(taskDefinition.get());
        }

        formDefinitionRepository.deleteById(id);
    }

    public void calculateFormVersion(FormDefinitionDTO formDefinition){
        if (formDefinition.getFormVersion() == null || formDefinition.getId() == null){
            formDefinition.setFormVersion("0");
            return;
        }

        FormDefinitionDTO formDefinitionSaved = findById(formDefinition.getId()).get();

        if (!formDefinition.getFormBuilder().equals(formDefinitionSaved.getFormBuilder())) {
            formDefinition.setFormVersion(String.valueOf(Integer.parseInt(formDefinition.getFormVersion()) + 1));
            return;
        }

        if (!formDefinition.getFormSchema().equals(formDefinitionSaved.getFormSchema())) {
            formDefinition.setFormVersion(String.valueOf(Integer.parseInt(formDefinition.getFormVersion()) + 1));
            return;
        }

        formDefinition.setFormVersion("0");
    }

}
