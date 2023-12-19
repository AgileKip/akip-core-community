package org.akip.service.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.akip.domain.ProcessDefinition;
import org.akip.domain.TaskDefinition;
import org.akip.service.dto.ProcessDefinitionDTO;
import org.akip.service.dto.TaskDefinitionDTO;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Collections;
import java.util.Map;

@Mapper(componentModel = "spring", uses = {})
public interface TaskDefinitionMapper extends EntityMapper<TaskDefinitionDTO, TaskDefinition> {
    ObjectMapper objectMapper = new ObjectMapper();

    @Named("name")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "documentation", source = "documentation")
    @Mapping(target = "bpmnProcessDefinitionId", source = "bpmnProcessDefinitionId")
    TaskDefinitionDTO toDtoName(TaskDefinition processDefinition);

    TaskDefinitionDTO toDto(TaskDefinition s);

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
