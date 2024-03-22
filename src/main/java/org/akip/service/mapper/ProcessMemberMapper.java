package org.akip.service.mapper;

import org.akip.domain.ProcessMember;
import org.akip.service.dto.ProcessMemberDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link ProcessMember} and its DTO {@link ProcessMemberDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProcessMemberMapper extends EntityMapper<ProcessMemberDTO, ProcessMember> {

    @Mapping(target = "processDefinition")
    ProcessMemberDTO toDto(ProcessMember entity);
}
