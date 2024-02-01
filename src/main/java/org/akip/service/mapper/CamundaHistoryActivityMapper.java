package org.akip.service.mapper;

import org.akip.service.dto.CamundaEventSubscriptionDTO;
import org.akip.service.dto.CamundaHistoryActivityDTO;
import org.camunda.bpm.engine.history.HistoricActivityInstance;
import org.camunda.bpm.engine.runtime.EventSubscription;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper for the DTO {@link CamundaHistoryActivityDTO}.
 */
@Component
public class CamundaHistoryActivityMapper {

    public List<CamundaHistoryActivityDTO> mapToCamundaEventSubscriptionsDTO(List<HistoricActivityInstance> historyActivity) {
        return historyActivity.stream().map(this::mapToCamundaHistoryActivityDTO).collect(Collectors.toList());
    }

    public CamundaHistoryActivityDTO mapToCamundaHistoryActivityDTO(HistoricActivityInstance historyActivity) {
        if (historyActivity == null) {
            return null;
        } else {
            return new CamundaHistoryActivityDTO(

                historyActivity.getId(),
                historyActivity.getActivityType(),
                historyActivity.getActivityName(),
                historyActivity.getExecutionId(),
                historyActivity.getProcessInstanceId(),
                historyActivity.getActivityId(),
                historyActivity.getStartTime(),
                historyActivity.getTenantId()
            );
        }
    }
}
