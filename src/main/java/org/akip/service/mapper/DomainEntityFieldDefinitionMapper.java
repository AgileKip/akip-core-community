package org.akip.service.mapper;

import org.akip.domain.DomainEntityDefinition;
import org.akip.domain.DomainEntityFieldDefinition;
import org.akip.service.dto.DomainEntityDefinitionDTO;
import org.akip.service.dto.DomainEntityFieldDefinitionDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * Mapper for the entity {@link DomainEntityFieldDefinition} and its DTO {@link DomainEntityFieldDefinitionDTO}.
 */
@Mapper(componentModel = "spring")
public interface DomainEntityFieldDefinitionMapper extends EntityMapper<DomainEntityFieldDefinitionDTO, DomainEntityFieldDefinition> {
    @Mapping(target = "domainEntityDefinition", source = "domainEntityDefinition", qualifiedByName = "domainEntityDefinitionId")
    DomainEntityFieldDefinitionDTO toDto(DomainEntityFieldDefinition s);

    @Named("domainEntityDefinitionId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DomainEntityDefinitionDTO toDtoDomainEntityDefinitionId(DomainEntityDefinition domainEntityDefinition);
}
