package org.akip.service.mapper;

import org.akip.domain.*;
import org.akip.service.dto.ProcessInstanceSubscriptionDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link ProcessInstanceSubscription} and its DTO {@link ProcessInstanceSubscriptionDTO}.
 */
@Mapper(componentModel = "spring", uses = { ProcessInstanceMapper.class })
public interface ProcessInstanceSubscriptionMapper extends EntityMapper<ProcessInstanceSubscriptionDTO, ProcessInstanceSubscription> {}
