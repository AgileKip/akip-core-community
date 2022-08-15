package org.akip.dashboard.builder;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;

import org.akip.dashboard.model.*;
import org.akip.dashboard.service.DashboardConfigService;
import org.akip.dashboard.service.dto.DashboardConfigDTO;
import org.akip.dashboard.service.dto.DashboardGroupConfigDTO;
import org.akip.dashboard.service.dto.DashboardTaskInstanceDTO;
import org.akip.dashboard.service.mapper.DashboardTaskInstanceMapper;
import org.akip.dashboard.util.BusinessHoursDurationUtil;
import org.akip.dashboard.util.DurationUtil;
import org.akip.domain.enumeration.TypeTaskInstance;
import org.akip.repository.TaskInstanceRepository;
import org.springframework.stereotype.Service;

@Service
public class AkipDashboardTaskInstancePerAssigneeChartGroupBuilder extends DashboardGroupBuilder {

    private final TaskInstanceRepository taskInstanceRepository;

    private final DashboardTaskInstanceMapper dashboardTaskInstanceMapper;

    private final DashboardConfigService dashboardConfigService;

    public AkipDashboardTaskInstancePerAssigneeChartGroupBuilder(
        TaskInstanceRepository taskInstanceRepository,
        DashboardTaskInstanceMapper dashboardTaskInstanceMapper,
        DashboardConfigService dashboardConfigService
    ) {
        this.taskInstanceRepository = taskInstanceRepository;
        this.dashboardTaskInstanceMapper = dashboardTaskInstanceMapper;
        this.dashboardConfigService = dashboardConfigService;
    }

    @Override
    public GroupModel buildGroup(DashboardRequestModel dashboardRequest, DashboardGroupConfigDTO groupConfig) {
        List<DashboardTaskInstanceDTO> taskInstances = getTaskInstances(dashboardRequest);

        CardGroupModel cardGroup = new CardGroupModel().title(groupConfig.getTitle());

        List<String> taskNames = taskInstances
            .stream()
            .map(taskInstance -> taskInstance.getName())
            .collect(Collectors.toSet())
            .stream()
            .sorted((taskName1, taskName2) -> taskName1.compareTo(taskName2))
            .collect(Collectors.toList());

        taskNames.forEach(
            taskName -> {
                cardGroup.addCard(buildCardMeanTaskExecutionTimePerAssignee(taskName, taskInstances));
            }
        );

        return cardGroup;
    }

    private List<DashboardTaskInstanceDTO> getTaskInstances(DashboardRequestModel dashboardRequest) {
        DashboardConfigDTO dashboardConfig = dashboardConfigService.findByProcessDefinition(dashboardRequest.getProcessDefinition());
        BusinessHoursDurationUtil businessHoursDurationUtil = new BusinessHoursDurationUtil(dashboardConfig.getCalendarProperties());

        List<DashboardTaskInstanceDTO> dashboardTaskInstances = taskInstanceRepository
            .findByProcessDefinitionIdAndTypeAndCreateDateBetween(
                dashboardRequest.getProcessDefinition().getId(),
                TypeTaskInstance.USER_TASK,
                dashboardRequest.getStartDateTime().toInstant(ZoneOffset.UTC),
                dashboardRequest.getEndDateTime().toInstant(ZoneOffset.UTC)
            )
            .stream()
            .map(dashboardTaskInstanceMapper::toDTO)
            .collect(Collectors.toList());

        dashboardTaskInstances.forEach(
            dashboardTaskInstance -> {
                if (dashboardTaskInstance.getCreateTime() != null && dashboardTaskInstance.getStartTime() != null) {
                    dashboardTaskInstance.setUnassignedDurationBusinessHour(
                        businessHoursDurationUtil.businessHourDuration(
                            dashboardTaskInstance.getCreateTime(),
                            dashboardTaskInstance.getStartTime()
                        )
                    );
                    dashboardTaskInstance.setUnassignedDurationTotal(
                        Duration.between(dashboardTaskInstance.getCreateTime(), dashboardTaskInstance.getStartTime())
                    );
                }

                if (dashboardTaskInstance.getStartTime() != null && dashboardTaskInstance.getEndTime() != null) {
                    dashboardTaskInstance.setExecutionDurationBusinessHour(
                        businessHoursDurationUtil.businessHourDuration(
                            dashboardTaskInstance.getStartTime(),
                            dashboardTaskInstance.getEndTime()
                        )
                    );
                    dashboardTaskInstance.setExecutionDurationTotal(
                        Duration.between(dashboardTaskInstance.getStartTime(), dashboardTaskInstance.getEndTime())
                    );
                }
            }
        );

        return dashboardTaskInstances;
    }

    private CardModel buildCardMeanTaskExecutionTimePerAssignee(String taskName, List<DashboardTaskInstanceDTO> taskInstances) {
        CardModel card = new CardModel().title("Task " + taskName);
        card
            .getTableModel()
            .addHead("Assignee")
            .addHead("Number of Executions")
            .addHead("Mean Time to Completion (Business Hours)")
            .addHead("Mean Time to Completion (Total)");

        List<String> assignees = taskInstances
            .stream()
            .filter(taskIntance -> taskIntance.getName().equals(taskName))
            .filter(taskInstance -> taskInstance.getExecutionDurationTotal() != null)
            .map(taskInstance -> taskInstance.getAssignee())
            .filter(assignee -> assignee != null)
            .collect(Collectors.toSet())
            .stream()
            .sorted((assignee1, assignee2) -> assignee1.compareTo(assignee2))
            .collect(Collectors.toList());

        assignees.forEach(
            assignee -> {
                List<Duration> durationsBusinessHours = taskInstances
                    .stream()
                    .filter(dashboardTaskInstanceDTO -> dashboardTaskInstanceDTO.getName().equals(taskName))
                    .filter(dashboardTaskInstanceDTO -> assignee.equals(dashboardTaskInstanceDTO.getAssignee()))
                    .map(dashboardTaskInstanceDTO -> dashboardTaskInstanceDTO.getExecutionDurationBusinessHour())
                    .filter(duration -> duration != null)
                    .collect(Collectors.toList());

                List<Duration> durationsTotal = taskInstances
                    .stream()
                    .filter(dashboardTaskInstanceDTO -> dashboardTaskInstanceDTO.getName().equals(taskName))
                    .filter(dashboardTaskInstanceDTO -> assignee.equals(dashboardTaskInstanceDTO.getAssignee()))
                    .map(dashboardTaskInstanceDTO -> dashboardTaskInstanceDTO.getExecutionDurationTotal())
                    .filter(duration -> duration != null)
                    .collect(Collectors.toList());

                card
                    .getTableModel()
                    .addRow(
                        new TableRowModel()
                            .title(assignee)
                            .addValue(new BigDecimal(durationsTotal.size()))
                            .addHumanReadableValue(String.valueOf(durationsTotal.size()))
                            .addValue(DurationUtil.durationInSeconds(DurationUtil.meanDuration(durationsBusinessHours)))
                            .addHumanReadableValue(DurationUtil.humanReadableDuration(DurationUtil.meanDuration(durationsBusinessHours)))
                            .addValue(DurationUtil.durationInSeconds(DurationUtil.meanDuration(durationsTotal)))
                            .addHumanReadableValue(DurationUtil.humanReadableDuration(DurationUtil.meanDuration(durationsTotal)))
                    );
            }
        );

        return card;
    }
}
