package org.akip.service.mapper;

import org.akip.service.dto.CamundaEventSubscriptionDTO;
import org.camunda.bpm.engine.runtime.EventSubscription;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper for the DTO {@link CamundaEventSubscriptionDTO}.
 */
@Component
public class CamundaEventSubscriptionsMapper {

    public List<CamundaEventSubscriptionDTO> mapToCamundaEventSubscriptionsDTO(List<EventSubscription> eventSubscriptions) {
        return eventSubscriptions.stream().map(this::mapToCamundaEventSubscriptionsDTO).collect(Collectors.toList());
    }

    public CamundaEventSubscriptionDTO mapToCamundaEventSubscriptionsDTO(EventSubscription eventSubscription) {
        if (eventSubscription == null) {
            return null;
        } else {
            return new CamundaEventSubscriptionDTO(
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
