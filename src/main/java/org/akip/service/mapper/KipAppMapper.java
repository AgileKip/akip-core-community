package org.akip.service.mapper;

import org.akip.domain.KipApp;
import org.akip.service.dto.KipAppDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * Mapper for the entity {@link KipApp} and its DTO {@link KipAppDTO}.
 */
@Mapper(componentModel = "spring", uses = { })
public interface KipAppMapper extends EntityMapper<KipAppDTO, KipApp> {
    @Named("name")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    KipAppDTO toDtoName(KipApp kipApp);

    @Named("default")
    KipAppDTO toDto(KipApp entity);
}
