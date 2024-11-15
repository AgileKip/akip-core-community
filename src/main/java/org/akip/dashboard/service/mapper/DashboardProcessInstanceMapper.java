package org.akip.dashboard.service.mapper;

import org.akip.dashboard.service.dto.DashboardProcessInstanceDTO;
import org.akip.domain.ProcessInstance;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {})
public interface DashboardProcessInstanceMapper {

    @Mapping(source = "tenant.name", target = "tenantName")
    DashboardProcessInstanceDTO toDTO(ProcessInstance processInstance);
}
