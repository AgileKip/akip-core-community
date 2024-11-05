package org.akip.service.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.akip.domain.EntityFieldDefinition;
import org.akip.service.dto.EntityFieldDefinitionDTO;
import org.akip.service.dto.ValidationDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Mapper for the entity {@link EntityFieldDefinition} and its DTO {@link EntityFieldDefinitionDTO}.
 */
@Mapper(componentModel = "spring", uses = { DomainEntityDefinitionMapper.class })
public interface EntityFieldDefinitionMapper extends EntityMapper<EntityFieldDefinitionDTO, EntityFieldDefinition> {

    ObjectMapper objectMapper =new ObjectMapper();

    @Mapping(target = "definition", source = "definition", qualifiedByName = "identifier")
    EntityFieldDefinitionDTO toDto(EntityFieldDefinition s);

    default String validationsToString(List<ValidationDTO> validations) throws JsonProcessingException {
        if (validations == null) {
            return null;
        }
        return objectMapper.writeValueAsString(validations);
    }

    default List<ValidationDTO> stringToValidations(String s) throws JsonProcessingException {
        if (s == null) {
            return null;
        }
        return objectMapper.readValue(s, new TypeReference<List<ValidationDTO>>() {});
    }
}
