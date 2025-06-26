package org.akip.service.mapper;

import org.akip.domain.DomainEntityDefinition;
import org.akip.domain.DomainEntityHasManyRelationDefinition;
import org.akip.service.dto.DomainEntityDefinitionDTO;
import org.akip.service.dto.DomainEntityHasManyRelationDefinitionDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * Mapper for the entity {@link DomainEntityHasManyRelationDefinition} and its DTO {@link DomainEntityHasManyRelationDefinitionDTO}.
 */
@Mapper(componentModel = "spring")
public interface DomainEntityHasManyRelationDefinitionMapper
    extends EntityMapper<DomainEntityHasManyRelationDefinitionDTO, DomainEntityHasManyRelationDefinition> {
    @Mapping(target = "domainEntityDefinition", source = "domainEntityDefinition", qualifiedByName = "domainEntityDefinitionId")
    DomainEntityHasManyRelationDefinitionDTO toDto(DomainEntityHasManyRelationDefinition s);

    @Named("domainEntityDefinitionId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DomainEntityDefinitionDTO toDtoDomainEntityDefinitionId(DomainEntityDefinition domainEntityDefinition);
}
