package org.akip.service.mapper;

import org.akip.domain.DomainEntityDefinition;
import org.akip.domain.DomainEntityHasARelationDefinition;
import org.akip.service.dto.DomainEntityDefinitionDTO;
import org.akip.service.dto.DomainEntityHasARelationDefinitionDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * Mapper for the entity {@link DomainEntityHasARelationDefinition} and its DTO {@link DomainEntityHasARelationDefinitionDTO}.
 */
@Mapper(componentModel = "spring")
public interface DomainEntityHasARelationDefinitionMapper
    extends EntityMapper<DomainEntityHasARelationDefinitionDTO, DomainEntityHasARelationDefinition> {
    @Mapping(target = "domainEntityDefinition", source = "domainEntityDefinition", qualifiedByName = "domainEntityDefinitionId")
    DomainEntityHasARelationDefinitionDTO toDto(DomainEntityHasARelationDefinition s);

    @Named("domainEntityDefinitionId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DomainEntityDefinitionDTO toDtoDomainEntityDefinitionId(DomainEntityDefinition domainEntityDefinition);
}
