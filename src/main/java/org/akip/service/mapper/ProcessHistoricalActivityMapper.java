package org.akip.service.mapper;

import org.akip.service.dto.ProcessHistoricalActivityDTO;
import org.camunda.bpm.engine.history.HistoricActivityInstance;
import org.springframework.stereotype.Component;

/**
 * Mapper for the DTO {@link ProcessHistoricalActivityDTO}.
 */
@Component
public class ProcessHistoricalActivityMapper {

    public ProcessHistoricalActivityDTO camundaHistoricActivityToProcessHistoricalActivityDTO(HistoricActivityInstance historyActivity) {
        if (historyActivity == null) {
            return null;
        }

        return new ProcessHistoricalActivityDTO(
            historyActivity.getId(),
            historyActivity.getActivityType(),
            historyActivity.getActivityName(),
            historyActivity.getExecutionId(),
            historyActivity.getProcessInstanceId(),
            historyActivity.getActivityId(),
            historyActivity.getStartTime(),
            historyActivity.getEndTime(),
            historyActivity.getTenantId()
        );
    }
}
