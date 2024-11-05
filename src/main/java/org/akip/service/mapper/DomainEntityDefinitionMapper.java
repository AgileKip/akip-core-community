package org.akip.service.mapper;

import org.akip.domain.DomainEntityDefinition;
import org.akip.service.dto.DomainEntityDefinitionDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * Mapper for the entity {@link DomainEntityDefinition} and its DTO {@link DomainEntityDefinitionDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DomainEntityDefinitionMapper extends EntityMapper<DomainEntityDefinitionDTO, DomainEntityDefinition> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DomainEntityDefinitionDTO toDtoId(DomainEntityDefinition domainEntityDefinition);

    @Named("identifier")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "version", source = "version")
    DomainEntityDefinitionDTO toDto(DomainEntityDefinition domainEntityDefinition);
}
