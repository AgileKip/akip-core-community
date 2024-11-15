package org.akip.service.mapper;

import org.akip.domain.DecisionDefinition;
import org.akip.service.dto.DecisionDefinitionDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * Mapper for the entity {@link DecisionDefinition} and its DTO {@link DecisionDefinitionDTO}.
 */

@Mapper(componentModel = "spring", uses = {})
public interface DecisionDefinitionMapper extends EntityMapper<DecisionDefinitionDTO, DecisionDefinition> {
    @Named("name")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "dmnDecisionDefinitionId", source = "dmnDecisionDefinitionId")
    DecisionDefinitionDTO toDtoName(DecisionDefinition decisionDefinition);

    @Named("loadTaskContext")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "dmnDecisionDefinitionId", source = "dmnDecisionDefinitionId")
    DecisionDefinitionDTO toDTOLoadTaskContext(DecisionDefinition decisionDefinition);
}
