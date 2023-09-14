package org.akip.service.mapper;

import org.akip.domain.ProcessTimelineItemDefinition;
import org.akip.service.dto.ProcessTimelineItemDefinitionDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link ProcessTimelineItemDefinition} and its DTO {@link ProcessTimelineItemDefinitionDTO}.
 */
@Mapper(componentModel = "spring", uses = { ProcessTimelineDefinitionMapper.class })
public interface ProcessTimelineItemDefinitionMapper extends EntityMapper<ProcessTimelineItemDefinitionDTO, ProcessTimelineItemDefinition> {
    ProcessTimelineItemDefinitionDTO toDto(ProcessTimelineItemDefinition processTimelineItemDefinition);

    ProcessTimelineItemDefinition toEntity(ProcessTimelineItemDefinitionDTO processTimelineItemDefinitionDTO);
}
