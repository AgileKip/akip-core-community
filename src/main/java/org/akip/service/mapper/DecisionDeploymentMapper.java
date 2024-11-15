package org.akip.service.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.akip.domain.DecisionDeployment;
import org.akip.service.dto.DecisionDeploymentDTO;
import org.akip.service.dto.DecisionDeploymentDmnModelDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;

@Component
@Mapper(componentModel = "spring", uses = {})
public interface DecisionDeploymentMapper extends EntityMapper<DecisionDeploymentDTO, DecisionDeployment> {
    ObjectMapper objectMapper = new ObjectMapper();

    @Mapping(target = "specificationFile", ignore = true)
    @Mapping(target = "specificationFileContentType", ignore = true)
    DecisionDeploymentDTO toDto(DecisionDeployment decisionDeployment);

    DecisionDeploymentDmnModelDTO toDmnModelDto(DecisionDeployment decisionDeployment);

    default String byteArrayToString(byte[] bytes) {
        return new String(bytes, StandardCharsets.UTF_8);
    }

    default String mapToString(Map<String, String> map) throws JsonProcessingException {
        if (map == null) {
            return null;
        }
        return objectMapper.writeValueAsString(map);
    }

    default Map<String, String> stringToMap(String s) throws JsonProcessingException {
        if (s == null) {
            return Collections.emptyMap();
        }
        return objectMapper.readValue(s, new TypeReference<Map<String, String>>() {});
    }
}
