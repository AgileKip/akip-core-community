package org.akip.service.mapper;

import camundajar.impl.com.google.gson.Gson;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.akip.form.camundaForm8.CamundaForm8Def;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface CamundaForm8Mapper {

    ObjectMapper objectMapper = new ObjectMapper();

    default String camundaForm8ToString(CamundaForm8Def camundaForm8Def) throws JsonProcessingException {
        if (camundaForm8Def == null) {
            return null;
        }
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return objectMapper.writeValueAsString(camundaForm8Def);
    }

    default CamundaForm8Def stringToCamundaForm8(String s) throws JsonProcessingException {
        if (s == null) {
            return null;
        }
        Gson gson = new Gson();
        return gson.fromJson(s, CamundaForm8Def.class);
    }

}
