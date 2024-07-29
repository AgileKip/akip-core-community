package org.akip.service.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.akip.domain.JobExecutionTracking;
import org.akip.service.dto.JobExecutionTrackingDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.HashMap;
import java.util.Map;

/**
 * Mapper for the entity {@link JobExecutionTracking} and its DTO {@link JobExecutionTrackingDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface JobExecutionTrackingMapper extends EntityMapper<JobExecutionTrackingDTO, JobExecutionTracking> {
    ObjectMapper mapper = new ObjectMapper();

    @Mapping(source = "summary", target = "summary", qualifiedByName = "toHashMapStringDescription")
    JobExecutionTrackingDTO toDto(JobExecutionTracking jobExecutionTracking);

    @Mapping(source = "summary", target = "summary", qualifiedByName = "toStringHashMapDescription")
    JobExecutionTracking toEntity(JobExecutionTrackingDTO jobExecutionTrackingDTO);

    @Named("toHashMapStringDescription")
    default HashMap<String, String> toHashMapStringDescription(String description) throws JsonProcessingException {
        if (description != null) {
            return mapper.readValue(description, new TypeReference<>() {});
        }
        return new HashMap<String, String>();
    }

    @Named("toStringHashMapDescription")
    default String toStringHashMapDescription(Map<String, String> descriptions) throws JsonProcessingException {
        if (descriptions == null) {
            return null;
        }

        return mapper.writeValueAsString(descriptions);
    }
}
