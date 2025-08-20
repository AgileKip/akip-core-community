package org.akip.service.mapper;

import org.akip.domain.DomainEntityDefinition;
import org.akip.domain.DomainEntityFieldDefinition;
import org.akip.domain.DomainEntityHasARelationDefinition;
import org.akip.domain.DomainEntityHasManyRelationDefinition;
import org.akip.service.dto.DomainEntityDefinitionDTO;
import org.akip.service.dto.DomainEntityFieldDefinitionDTO;
import org.akip.service.dto.DomainEntityHasARelationDefinitionDTO;
import org.akip.service.dto.DomainEntityHasManyRelationDefinitionDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * Mapper for the entity {@link DomainEntityDefinition} and its DTO {@link DomainEntityDefinitionDTO}.
 */
@Mapper(componentModel = "spring")
public interface DomainEntityDefinitionMapper extends EntityMapper<DomainEntityDefinitionDTO, DomainEntityDefinition> {

    @Mapping(target = "domainEntityDefinition", ignore = true)
    DomainEntityFieldDefinitionDTO toDtoDomainEntityFieldDefinition(DomainEntityFieldDefinition s);

    @Named("domainEntityDefinitionIdAndName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "version", source = "version")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "executionDate", source = "executionDate")
    DomainEntityDefinitionDTO toSimpleDto(DomainEntityDefinition entity);

    @Mapping(target = "domainEntityDefinition", ignore = true)
    DomainEntityHasARelationDefinitionDTO toDtoDomainEntityHasARelationDefinition(DomainEntityHasARelationDefinition s);

    @Mapping(target = "domainEntityDefinition", ignore = true)
    DomainEntityHasManyRelationDefinitionDTO toDtoDomainEntityHasManyRelationDefinition(DomainEntityHasManyRelationDefinition s);

    @Mapping(target = "domainEntityDefinition", source = "domainEntityDefinition", qualifiedByName = "domainEntityDefinitionId")
    DomainEntityFieldDefinition toEntityDomainEntityFieldDefinition(DomainEntityFieldDefinitionDTO s);

    @Mapping(target = "domainEntityDefinition", source = "domainEntityDefinition", qualifiedByName = "domainEntityDefinitionId")
    DomainEntityHasARelationDefinition toEntityDomainEntityHasARelationDefinition(DomainEntityHasARelationDefinitionDTO s);

    @Mapping(target = "domainEntityDefinition", source = "domainEntityDefinition", qualifiedByName = "domainEntityDefinitionId")
    DomainEntityHasManyRelationDefinition toEntityDomainEntityHasManyRelationDefinition(DomainEntityHasManyRelationDefinitionDTO s);

    @Named("domainEntityDefinitionId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DomainEntityDefinition toEntityWithId(DomainEntityDefinitionDTO dto);



}
