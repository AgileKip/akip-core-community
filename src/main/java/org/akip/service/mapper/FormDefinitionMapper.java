package org.akip.service.mapper;

import org.akip.domain.FormDefinition;
import org.akip.service.dto.FormDefinitionDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", uses = {})
public interface FormDefinitionMapper extends EntityMapper<FormDefinitionDTO, FormDefinition> {
    @Named("name")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "formBuilder", source = "formBuilder")
    FormDefinitionDTO toDtoName(FormDefinition processDefinition);

}
