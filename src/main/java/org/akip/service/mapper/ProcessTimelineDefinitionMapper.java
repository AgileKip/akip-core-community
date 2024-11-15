package org.akip.service.mapper;

import org.akip.domain.ProcessTimelineDefinition;
import org.akip.service.dto.ProcessTimelineDefinitionDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link ProcessTimelineDefinition} and its DTO {@link ProcessTimelineDefinitionDTO}.
 */
@Mapper(componentModel = "spring", uses = { ProcessTimelineItemDefinitionMapper.class })
public interface ProcessTimelineDefinitionMapper extends EntityMapper<ProcessTimelineDefinitionDTO, ProcessTimelineDefinition> {
    @Mapping(target = "items", source = "items")
    ProcessTimelineDefinitionDTO toDto(ProcessTimelineDefinition processTimelineDefinition);

    ProcessTimelineDefinition toEntity(ProcessTimelineDefinitionDTO processTimelineDefinitionDTO);
}
