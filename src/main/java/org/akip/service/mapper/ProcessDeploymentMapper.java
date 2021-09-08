package org.akip.service.mapper;

import org.akip.domain.ProcessDeployment;
import org.akip.service.dto.ProcessDeploymentBpmnModelDTO;
import org.akip.service.dto.ProcessDeploymentDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.nio.charset.StandardCharsets;

/**
 * Mapper for the entity {@link ProcessDeployment} and its DTO {@link ProcessDeploymentDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
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
