package org.akip.service.mapper;


import org.akip.domain.ProcessDefinitionSubscription;
import org.akip.service.dto.ProcessDefinitionSubscriptionDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link org.akip.domain.ProcessDefinitionSubscription} and its DTO {@link ProcessDefinitionSubscriptionDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProcessDefinitionSubscriptionMapper
    extends EntityMapper<ProcessDefinitionSubscriptionDTO, ProcessDefinitionSubscription> {}
