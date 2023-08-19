package org.akip.service.mapper;

import org.akip.domain.AkipTaskInstanceRankingContextConfig;
import org.akip.domain.ProcessDefinition;
import org.akip.service.dto.AkipTaskInstanceRankingContextConfigDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link AkipTaskInstanceRankingContextConfig} and its DTO {@link AkipTaskInstanceRankingContextConfigDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AkipTaskInstanceRankingContextConfigMapper extends EntityMapper<AkipTaskInstanceRankingContextConfigDTO, AkipTaskInstanceRankingContextConfig> {

    @Mapping(source = "processDefinitionId", target = "processDefinition")
    AkipTaskInstanceRankingContextConfig toEntity(AkipTaskInstanceRankingContextConfigDTO dto);

    @Mapping(source = "processDefinition", target = "processDefinitionId")
    AkipTaskInstanceRankingContextConfigDTO toDto(AkipTaskInstanceRankingContextConfig entity);

    default ProcessDefinition toProcessDefinition(Long id) {
        ProcessDefinition processDefinition = new ProcessDefinition();
        processDefinition.setId(id);
        return processDefinition;
    }

    default Long toProcessDefinitionId(ProcessDefinition processDefinition) {
        return processDefinition.getId();
    }
}
