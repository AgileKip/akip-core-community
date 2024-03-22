package org.akip.service.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.akip.domain.ProcessDeployment;
import org.akip.service.dto.ProcessDeploymentBpmnModelDTO;
import org.akip.service.dto.ProcessDeploymentDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;

/**
 * Mapper for the entity {@link ProcessDeployment} and its DTO {@link ProcessDeploymentDTO}.
 */
@Mapper(componentModel = "spring", uses = {ProcessDefinitionMapper.class, MapUtilMapper.class})
public interface ProcessDeploymentMapper extends EntityMapper<ProcessDeploymentDTO, ProcessDeployment> {

    @Mapping(target = "specificationFile", ignore = true)
    @Mapping(target = "specificationFileContentType", ignore = true)
    ProcessDeploymentDTO toDto(ProcessDeployment processDeployment);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "specificationFile", source = "specificationFile")
    @Mapping(target = "specificationFileContentType", source = "specificationFileContentType")
    ProcessDeploymentBpmnModelDTO toBpmnModelDto(ProcessDeployment processDeployment);

    default String byteArrayToString(byte[] bytes) {
        return new String(bytes, StandardCharsets.UTF_8);
    }

}
