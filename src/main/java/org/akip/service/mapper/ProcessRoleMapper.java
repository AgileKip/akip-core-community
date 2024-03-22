package org.akip.service.mapper;

import org.akip.domain.ProcessRole;
import org.akip.service.dto.ProcessRoleDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link ProcessRole} and its DTO {@link ProcessRoleDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProcessRoleMapper extends EntityMapper<ProcessRoleDTO, ProcessRole> {

    @Mapping(target = "processDefinition", ignore = true)
    ProcessRoleDTO toDto(ProcessRole entity);
}
