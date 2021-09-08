package org.akip.service.mapper;

import org.akip.domain.ProcessDefinition;
import org.akip.service.dto.ProcessDefinitionDTO;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * Mapper for the entity {@link ProcessDefinition} and its DTO {@link ProcessDefinitionDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProcessDefinitionMapper extends EntityMapper<ProcessDefinitionDTO, ProcessDefinition> {
    @Named("name")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "bpmnProcessDefinitionId", source = "bpmnProcessDefinitionId")
    ProcessDefinitionDTO toDtoName(ProcessDefinition processDefinition);

    @Named("loadTaskContext")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "bpmnProcessDefinitionId", source = "bpmnProcessDefinitionId")
    ProcessDefinitionDTO toDTOLoadTaskContext(ProcessDefinition processDefinition);
}
