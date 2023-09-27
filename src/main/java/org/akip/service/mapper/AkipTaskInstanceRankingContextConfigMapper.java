package org.akip.service.mapper;

import org.akip.domain.AkipTaskInstanceRankingContextConfig;
import org.akip.service.dto.AkipTaskInstanceRankingContextConfigDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link AkipTaskInstanceRankingContextConfig} and its DTO {@link AkipTaskInstanceRankingContextConfigDTO}.
 */
@Mapper(componentModel = "spring", uses = { ProcessDefinitionMapper.class })
public interface AkipTaskInstanceRankingContextConfigMapper extends EntityMapper<AkipTaskInstanceRankingContextConfigDTO, AkipTaskInstanceRankingContextConfig> {

    @Mapping(source = "processDefinition", target = "processDefinition")
    AkipTaskInstanceRankingContextConfig toEntity(AkipTaskInstanceRankingContextConfigDTO dto);

    @Mapping(source = "processDefinition", target = "processDefinition", qualifiedByName = "name")
    AkipTaskInstanceRankingContextConfigDTO toDto(AkipTaskInstanceRankingContextConfig entity);

}
