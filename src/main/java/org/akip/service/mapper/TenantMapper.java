package org.akip.service.mapper;

import org.akip.domain.Tenant;
import org.akip.service.dto.TenantDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link Tenant} and its DTO {@link TenantDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TenantMapper extends EntityMapper<TenantDTO, Tenant> {}
