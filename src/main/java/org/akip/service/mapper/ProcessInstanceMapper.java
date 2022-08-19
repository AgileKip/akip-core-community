package org.akip.service.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.akip.domain.ProcessInstance;
import org.akip.service.dto.ProcessInstanceDTO;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Collections;
import java.util.Map;

/**
 * Mapper for the entity {@link ProcessInstance} and its DTO {@link ProcessInstanceDTO}.
 */
@Mapper(componentModel = "spring", uses = { ProcessDefinitionMapper.class })
public interface ProcessInstanceMapper extends EntityMapper<ProcessInstanceDTO, ProcessInstance> {
    ObjectMapper objectMapper = new ObjectMapper();

    @Mapping(target = "processDefinition", source = "processDefinition", qualifiedByName = "name")
    ProcessInstanceDTO toDto(ProcessInstance s);

    @Named("businessKey")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "businessKey", source = "businessKey")
    ProcessInstanceDTO toDtoBusinessKey(ProcessInstance processInstance);

    @Named("loadTaskContext")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "businessKey", source = "businessKey")
    @Mapping(target = "camundaDeploymentId", source = "camundaDeploymentId")
    @Mapping(target = "camundaProcessDefinitionId", source = "camundaProcessDefinitionId")
    @Mapping(target = "camundaProcessInstanceId", source = "camundaProcessInstanceId")
    @Mapping(target = "startDate", source = "startDate")
    @Mapping(target = "endDate", source = "endDate")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "data", source = "data")
    @Mapping(target = "processDefinition", source = "processDefinition", qualifiedByName = "loadTaskContext")
    ProcessInstanceDTO toDTOLoadTaskContext(ProcessInstance processInstance);

    default String mapToString(Map<String, String> map) throws JsonProcessingException {
        if (map == null) {
            return null;
        }

        return objectMapper.writeValueAsString(map);
    }

    default Map<String, String> stringToMap(String s) throws JsonProcessingException {
        if (s == null) {
            return Collections.emptyMap();
        }

        return objectMapper.readValue(s, new TypeReference<Map<String, String>>() {});
    }
}
