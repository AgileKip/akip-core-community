package org.akip.camunda.form7;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.akip.camunda.CamundaConstants;
import org.akip.service.FormDefinitionService;
import org.akip.service.dto.FormDefinitionDTO;
import org.akip.service.dto.ProcessDefinitionDTO;
import org.akip.service.dto.TaskDefinitionDTO;
import org.akip.service.mapper.CamundaForm7FieldDefMapper;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.StartEvent;
import org.camunda.bpm.model.bpmn.instance.UserTask;
import org.camunda.bpm.model.bpmn.instance.camunda.*;
import org.camunda.bpm.model.xml.type.ModelElementType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CamundaForm7Service {

    private final Logger log = LoggerFactory.getLogger(CamundaForm7Service.class);

    private final CamundaForm7FieldDefMapper camundaForm7FieldDefMapper;

    private final FormDefinitionService formDefinitionService;
    public CamundaForm7Service(CamundaForm7FieldDefMapper camundaForm7FieldDefMapper, FormDefinitionService formDefinitionService) {
        this.camundaForm7FieldDefMapper = camundaForm7FieldDefMapper;
        this.formDefinitionService = formDefinitionService;
    }

    public Optional<FormDefinitionDTO> createOrUpdateStartFormDefinition(ProcessDefinitionDTO processDefinition, BpmnModelInstance bpmnModelInstance) {
        StartEvent startEvent = extracStartEventFromModel(bpmnModelInstance);
        if (startEvent.getExtensionElements() == null) {
            return Optional.empty();
        }

        Optional<CamundaFormData> optionalCamundaFormData = startEvent
                .getExtensionElements()
                .getChildElementsByType(CamundaFormData.class)
                .stream()
                .findFirst();

        if (optionalCamundaFormData.isEmpty()) {
            return Optional.empty();
        }

        FormDefinitionDTO formDefinition = findByProcessDefinitionOrCreateANew(processDefinition);

        populateFormDefinition(optionalCamundaFormData.get(), formDefinition);

        return Optional.of(formDefinitionService.save(formDefinition));
    }

    public Optional<FormDefinitionDTO> createOrUpdateTaskFormDefinition(TaskDefinitionDTO taskDefinition, UserTask userTask) {
        if (userTask.getExtensionElements() == null) {
            return Optional.empty();
        }

        Optional<CamundaFormData> optionalCamundaFormData = userTask
                .getExtensionElements()
                .getChildElementsByType(CamundaFormData.class)
                .stream()
                .findFirst();

        if (optionalCamundaFormData.isEmpty()) {
            return Optional.empty();
        }

        FormDefinitionDTO formDefinition = findByTaskDefinitionOrCreateANew(taskDefinition);

        populateFormDefinition(optionalCamundaFormData.get(), formDefinition);

        return Optional.of(formDefinitionService.save(formDefinition));
    }
    private FormDefinitionDTO findByProcessDefinitionOrCreateANew(ProcessDefinitionDTO processDefinition) {
        if (processDefinition.getId() == null) {
            return new FormDefinitionDTO();
        }

        return formDefinitionService
                .findByProcessDefinitionId(processDefinition.getId())
                .orElse(new FormDefinitionDTO());
    }

    private FormDefinitionDTO findByTaskDefinitionOrCreateANew(TaskDefinitionDTO taskDefinition) {
        if (taskDefinition.getId() == null) {
            return new FormDefinitionDTO();
        }

        return formDefinitionService
                .findByTaskDefinitionId(taskDefinition.getId())
                .orElse(new FormDefinitionDTO());
    }

    private void populateFormDefinition(CamundaFormData camundaFormData, FormDefinitionDTO formDefinition) {
        formDefinition.setFormBuilder(CamundaConstants.CAMUNDA_FORM_7_BUILDER);
        formDefinition.setFormSchema(toFormSchema(camundaFormData));
    }



    private StartEvent extracStartEventFromModel(BpmnModelInstance modelInstance) {
        ModelElementType startEventType = modelInstance.getModel().getType(StartEvent.class);
        StartEvent startEvent = (StartEvent) modelInstance.getModelElementsByType(startEventType).iterator().next();
        return startEvent;
    }

    private String toFormSchema(CamundaFormData camundaFormData) {
        try {
            return camundaForm7FieldDefMapper.listFormFieldToString(camundaFormData
                    .getCamundaFormFields()
                    .stream()
                    .map(this::toCamundaFormFieldDef)
                    .collect(Collectors.toList()));
        } catch (JsonProcessingException e) {
            log.error("Error parsing form to JSON: " + e);
            return "";
        }
    }

    private CamundaForm7FieldDef toCamundaFormFieldDef(CamundaFormField formField) {
        CamundaForm7FieldDef camundaFormFieldDef = new CamundaForm7FieldDef();
        camundaFormFieldDef.setId(formField.getCamundaId());
        camundaFormFieldDef.setLabel(formField.getCamundaLabel());
        camundaFormFieldDef.setType(formField.getCamundaType());
        if (formField.getCamundaValues() != null) {
            camundaFormFieldDef.setValues(formField.getCamundaValues().stream().collect(Collectors.toMap(CamundaValue::getCamundaId, CamundaValue::getCamundaName)));
        }

        if (formField.getCamundaDefaultValue() != null) {
            camundaFormFieldDef.setDefaultValue(formField.getCamundaDefaultValue());
        }
        if (formField.getCamundaValidation() != null) {
            camundaFormFieldDef.setValidationConstraints(
                    formField.getCamundaValidation().getCamundaConstraints().stream().map(this::toCamundaFormFieldValidationConstraintDef).collect(Collectors.toList())
            );
        }

        if (formField.getCamundaProperties() != null) {
            camundaFormFieldDef.setProperties(
                    formField.getCamundaProperties().getCamundaProperties().stream().collect(Collectors.toMap(CamundaProperty::getCamundaId, CamundaProperty::getCamundaValue))
            );
        }
        return camundaFormFieldDef;
    }


    private CamundaForm7FieldValidationConstraintDef toCamundaFormFieldValidationConstraintDef(
            CamundaConstraint camundaConstraint
    ) {
        CamundaForm7FieldValidationConstraintDef camundaFormFieldValidationConstraintDef = new CamundaForm7FieldValidationConstraintDef();
        camundaFormFieldValidationConstraintDef.setName(camundaConstraint.getCamundaName());
        camundaFormFieldValidationConstraintDef.setConfiguration(camundaConstraint.getCamundaConfig());
        return camundaFormFieldValidationConstraintDef;
    }



}
