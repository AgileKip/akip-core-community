package org.akip.service.mapper;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.akip.camunda.form.CamundaFormFieldDef;
import org.mapstruct.Mapper;

import java.util.Collections;
import java.util.List;

@Mapper(componentModel = "spring", uses = {})
public interface CamundaFormFieldDefMapper {

    ObjectMapper objectMapper = new ObjectMapper();

    default String listFormFieldToString(List<CamundaFormFieldDef> formFields) throws JsonProcessingException {
        if (formFields == null) {
            return null;
        }
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return objectMapper.writeValueAsString(formFields);
    }

    default List<CamundaFormFieldDef> stringToListFormField(String s) throws JsonProcessingException {
        if (s == null) {
            return Collections.emptyList();
        }

        return objectMapper.readValue(s, new TypeReference<List<CamundaFormFieldDef>>() {});
    }
}
