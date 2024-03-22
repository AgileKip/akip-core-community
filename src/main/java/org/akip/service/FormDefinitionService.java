package org.akip.service;

import org.akip.repository.FormDefinitionRepository;
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

    private final FormDefinitionMapper formDefinitionMapper;

    public FormDefinitionService(FormDefinitionRepository formDefinitionRepository, FormDefinitionMapper formDefinitionMapper) {
        this.formDefinitionRepository = formDefinitionRepository;
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

    public FormDefinitionDTO save(FormDefinitionDTO formDefinitionDTO){
        calculateFormVersion(formDefinitionDTO);
        return formDefinitionMapper.toDto(formDefinitionRepository.save(formDefinitionMapper.toEntity(formDefinitionDTO)));
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
