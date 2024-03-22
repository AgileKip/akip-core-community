package org.akip.service.mapper;

import org.akip.domain.TaskDefinition;
import org.akip.service.dto.TaskDefinitionDTO;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring", uses = {FormDefinitionMapper.class, MapUtilMapper.class})
public interface TaskDefinitionMapper extends EntityMapper<TaskDefinitionDTO, TaskDefinition> {

    @Mapping(target = "formDefinition", source = "formDefinition", qualifiedByName = "name")
    TaskDefinitionDTO toDto(TaskDefinition s);


}
