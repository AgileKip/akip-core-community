package org.akip.service.mapper;

import org.akip.domain.AkipEmailConnectorConfig;
import org.akip.service.dto.AkipEmailConnectorConfigDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link AkipEmailConnectorConfig} and its DTO {@link AkipEmailConnectorConfigDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AkipEmailConnectorConfigMapper extends EntityMapper<AkipEmailConnectorConfigDTO, AkipEmailConnectorConfig> {}
