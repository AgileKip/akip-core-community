package org.akip.service.mapper;

import org.akip.domain.TemporaryProcessInstance;
import org.akip.service.dto.TemporaryProcessInstanceDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link TemporaryProcessInstance} and its DTO {@link TemporaryProcessInstanceDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TemporaryProcessInstanceMapper extends EntityMapper<TemporaryProcessInstanceDTO, TemporaryProcessInstance> {}
