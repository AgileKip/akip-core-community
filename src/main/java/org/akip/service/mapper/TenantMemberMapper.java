package org.akip.service.mapper;

import org.akip.domain.TenantMember;
import org.akip.service.dto.TenantMemberDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link TenantMember} and its DTO {@link TenantMemberDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TenantMemberMapper extends EntityMapper<TenantMemberDTO, TenantMember> {

    @Mapping(target = "tenant", ignore = true)
    TenantMemberDTO toDto(TenantMember entity);
}
