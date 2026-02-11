package org.akip.service.mapper;

import org.akip.domain.*;
import org.akip.service.dto.ProcessInstanceNotificationDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link ProcessInstanceNotification} and its DTO {@link ProcessInstanceNotificationDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProcessInstanceNotificationMapper extends EntityMapper<ProcessInstanceNotificationDTO, ProcessInstanceNotification> {}
