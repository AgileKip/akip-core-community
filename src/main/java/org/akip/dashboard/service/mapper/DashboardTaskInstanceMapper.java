package org.akip.dashboard.service.mapper;

import org.akip.dashboard.service.dto.DashboardTaskInstanceDTO;
import org.akip.domain.TaskInstance;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface DashboardTaskInstanceMapper {
    DashboardTaskInstanceDTO toDTO(TaskInstance taskInstance);
}
