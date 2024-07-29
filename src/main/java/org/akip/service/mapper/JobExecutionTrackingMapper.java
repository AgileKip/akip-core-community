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

    @Mapping(source = "summary", target = "summary")
    JobExecutionTrackingDTO toDto(JobExecutionTracking jobExecutionTracking);

    @Mapping(source = "summary", target = "summary")
    JobExecutionTracking toEntity(JobExecutionTrackingDTO jobExecutionTrackingDTO);

    default HashMap<String, String> stringToHashMap(String s) throws JsonProcessingException {
        if (s != null) {
            return mapper.readValue(s, new TypeReference<>() {});
        }
        return new HashMap<String, String>();
    }

    default String mapToString(Map<String, String> map) throws JsonProcessingException {
        if (map == null) {
            return null;
        }

        return mapper.writeValueAsString(map);
    }
}
