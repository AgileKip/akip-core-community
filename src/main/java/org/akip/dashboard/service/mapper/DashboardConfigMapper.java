package org.akip.dashboard.service.mapper;

import org.akip.dashboard.domain.DashboardConfig;
import org.akip.dashboard.service.dto.DashboardConfigDTO;
import org.akip.service.mapper.EntityMapper;
import org.akip.service.mapper.ProcessDefinitionMapper;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link DashboardConfig} and its DTO {@link DashboardConfigDTO}.
 */
@Mapper(componentModel = "spring", uses = { ProcessDefinitionMapper.class, DashboardGroupConfigMapper.class})
public interface DashboardConfigMapper extends EntityMapper<DashboardConfigDTO, DashboardConfig> {
    DashboardConfigDTO toDto(DashboardConfig dashboardConfig);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DashboardConfigDTO toDtoId(DashboardConfig dashboardConfig);
}
