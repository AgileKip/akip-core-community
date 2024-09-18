package org.akip.service.mapper;

import org.akip.domain.TenantRole;
import org.akip.service.dto.TenantRoleDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


/**
 * Mapper for the entity {@link TenantRole} and its DTO {@link TenantRoleDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TenantRoleMapper extends EntityMapper<TenantRoleDTO, TenantRole> {

    @Mapping(target = "tenant", ignore = true)
    TenantRoleDTO toDto(TenantRole entity);
}