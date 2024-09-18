package org.akip.service.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mapstruct.Mapper;

import java.util.Collections;
import java.util.Map;

@Mapper(componentModel = "spring")
public interface MapUtilMapper {

    ObjectMapper objectMapper = new ObjectMapper();
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
