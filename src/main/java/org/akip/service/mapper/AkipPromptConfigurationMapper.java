package org.akip.service.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.akip.domain.AkipPromptConfiguration;
import org.akip.service.dto.AkipPromptConfigurationDTO;
import org.akip.service.dto.AkipPromptConfigurationMessageDTO;
import org.akip.service.dto.AkipPromptConfigurationParamDTO;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Mapper;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Mapper for the entity {@link AkipPromptConfiguration} and its DTO {@link AkipPromptConfigurationDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AkipPromptConfigurationMapper extends EntityMapper<AkipPromptConfigurationDTO, AkipPromptConfiguration> {

    ObjectMapper objectMapper = new ObjectMapper();

    default List<AkipPromptConfigurationMessageDTO> stringToListMessages(String messagesAsString) throws JsonProcessingException {
        if (messagesAsString == null) {
            return Collections.emptyList();
        }
        return objectMapper.readValue(messagesAsString, new TypeReference<List<AkipPromptConfigurationMessageDTO>>() {});
    }

    default String listMessagesToString(List<AkipPromptConfigurationMessageDTO> messagesAsList) throws JsonProcessingException {
        if (messagesAsList == null) {
            return null;
        }
        return objectMapper.writeValueAsString(messagesAsList);
    }

    default List<AkipPromptConfigurationParamDTO> stringToListParams(String paramsAsString) throws JsonProcessingException {
        if (paramsAsString == null) {
            return Collections.emptyList();
        }
        return objectMapper.readValue(paramsAsString, new TypeReference<List<AkipPromptConfigurationParamDTO>>() {});
    }

    default String listParamsToString(List<AkipPromptConfigurationParamDTO> paramsAsList) throws JsonProcessingException {
        if (paramsAsList == null) {
            return null;
        }
        paramsAsList.forEach(param -> {
           if (StringUtils.isNotBlank(param.getValuesAsString())) {
               param.setValues(Stream.of(param.getValuesAsString().split("\n", -1)).collect(Collectors.toList()));
            }
        });
        return objectMapper.writeValueAsString(paramsAsList);
    }




}
