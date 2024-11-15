package org.akip.dashboard.service.mapper;

import org.akip.dashboard.domain.DashboardGroupConfig;
import org.akip.dashboard.service.dto.DashboardGroupConfigDTO;
import org.akip.service.mapper.EntityMapper;
import org.akip.service.mapper.ProcessDefinitionMapper;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link DashboardGroupConfig} and its DTO {@link DashboardGroupConfigDTO}.
 */
@Mapper(componentModel = "spring", uses = {ProcessDefinitionMapper.class})
public interface DashboardGroupConfigMapper extends EntityMapper<DashboardGroupConfigDTO, DashboardGroupConfig> {
    @Mapping(target = "dashboardConfig", ignore = true)
    DashboardGroupConfigDTO toDto(DashboardGroupConfig s);
}
