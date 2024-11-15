package org.akip.form.camundaForm8;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.akip.domain.FormDefinition;
import org.akip.repository.FormDefinitionRepository;
import org.akip.service.mapper.CamundaForm8Mapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CamundaForm8Service {

    private final FormDefinitionRepository formDefinitionRepository;

    private final CamundaForm8Mapper camundaForm8Mapper;

    public CamundaForm8Service(FormDefinitionRepository formDefinitionRepository, CamundaForm8Mapper camundaForm8Mapper) {
        this.formDefinitionRepository = formDefinitionRepository;
        this.camundaForm8Mapper = camundaForm8Mapper;
    }

    public CamundaForm8Def getCamundaForm8(Long formDefinitionId) throws JsonProcessingException {
        FormDefinition formDefinition = formDefinitionRepository.findById(formDefinitionId).orElse(null);
        if (formDefinition == null) {
            return null;
        }
        return camundaForm8Mapper.stringToCamundaForm8(formDefinition.getFormSchema());
    }

}
