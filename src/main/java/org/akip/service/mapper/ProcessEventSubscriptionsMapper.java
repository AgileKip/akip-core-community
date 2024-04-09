package org.akip.service.mapper;

import org.akip.service.dto.ProcessEventSubscriptionDTO;
import org.camunda.bpm.engine.runtime.EventSubscription;
import org.springframework.stereotype.Component;

/**
 * Mapper for the DTO {@link ProcessEventSubscriptionDTO}.
 */
@Component
public class ProcessEventSubscriptionsMapper {

    public ProcessEventSubscriptionDTO camundaEventSubscriptionToProcessEventSubscriptionDTO(EventSubscription eventSubscription) {
        if (eventSubscription == null) {
            return null;
        } else {
            return new ProcessEventSubscriptionDTO(
                eventSubscription.getId(),
                eventSubscription.getEventType(),
                eventSubscription.getEventName(),
                eventSubscription.getExecutionId(),
                eventSubscription.getProcessInstanceId(),
                eventSubscription.getActivityId(),
                eventSubscription.getCreated(),
                eventSubscription.getTenantId()
            );
        }
    }
}
