package org.akip.dashboard.builder;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

import org.akip.dashboard.model.BoxGroupModel;
import org.akip.dashboard.model.BoxModel;
import org.akip.dashboard.model.DashboardRequestModel;
import org.akip.dashboard.model.GroupModel;
import org.akip.dashboard.service.DashboardConfigService;
import org.akip.dashboard.service.dto.DashboardConfigDTO;
import org.akip.dashboard.service.dto.DashboardGroupConfigDTO;
import org.akip.dashboard.service.dto.DashboardProcessInstanceDTO;
import org.akip.dashboard.service.mapper.DashboardProcessInstanceMapper;
import org.akip.dashboard.util.BusinessHoursDurationUtil;
import org.akip.dashboard.util.DurationUtil;
import org.akip.domain.enumeration.StatusProcessInstance;
import org.akip.repository.ProcessInstanceRepository;
import org.springframework.stereotype.Service;

@Service
public class AkipDashboardProcessInstanceBoxGroupBuilder extends DashboardGroupBuilder {

    private final ProcessInstanceRepository processInstanceRepository;

    private final DashboardProcessInstanceMapper dashboardProcessInstanceMapper;

    private final DashboardConfigService dashboardConfigService;

    public AkipDashboardProcessInstanceBoxGroupBuilder(
        ProcessInstanceRepository processInstanceRepository,
        DashboardProcessInstanceMapper dashboardProcessInstanceMapper,
        DashboardConfigService dashboardConfigService
    ) {
        this.processInstanceRepository = processInstanceRepository;
        this.dashboardProcessInstanceMapper = dashboardProcessInstanceMapper;
        this.dashboardConfigService = dashboardConfigService;
    }

    @Override
    public GroupModel buildGroup(DashboardRequestModel dashboardRequest, DashboardGroupConfigDTO groupConfig) {
        List<DashboardProcessInstanceDTO> processInstances = getProcessInstances(dashboardRequest);

        BoxGroupModel boxGroupModel = new BoxGroupModel().title(groupConfig.getTitle());
        boxGroupModel.addBox(new BoxModel().title("Process Created").variant("warning").value(String.valueOf(processInstances.size())));

        boxGroupModel.addBox(
            new BoxModel()
                .title("Active Processes")
                .variant("info")
                .value(
                    String.valueOf(
                        processInstances
                            .stream()
                            .filter(processInstance -> processInstance.getStatus().equals(StatusProcessInstance.RUNNING))
                            .count()
                    )
                )
        );

        boxGroupModel.addBox(
            new BoxModel()
                .title("Canceled Processes")
                .variant("danger")
                .value(
                    String.valueOf(
                        processInstances
                            .stream()
                            .filter(processInstance -> processInstance.getStatus().equals(StatusProcessInstance.CANCELED))
                            .count()
                    )
                )
        );

        boxGroupModel.addBox(
            new BoxModel()
                .title("Completed Processes")
                .variant("success")
                .value(
                    String.valueOf(
                        processInstances
                            .stream()
                            .filter(processInstance -> processInstance.getStatus().equals(StatusProcessInstance.COMPLETED))
                            .count()
                    )
                )
        );

        List<Duration> durationsBusinessHours = processInstances
            .stream()
            .map(processInstance -> processInstance.getExecutionDurationBusinessHour())
            .filter(duration -> duration != null)
            .collect(Collectors.toList());

        Duration meanExecutionDurationBusinessHour = DurationUtil.meanDuration(durationsBusinessHours);

        boxGroupModel.addBox(
            new BoxModel()
                .title("MTTC (B.H.)")
                .subtitle("(Mean Time to Completion)")
                .variant("primary")
                .value(DurationUtil.humanReadableDuration(meanExecutionDurationBusinessHour))
        );

        List<Duration> durationsTotal = processInstances
            .stream()
            .map(processInstance -> processInstance.getExecutionDurationTotal())
            .filter(duration -> duration != null)
            .collect(Collectors.toList());

        Duration meanExecutionDurationTotal = DurationUtil.meanDuration(durationsTotal);

        boxGroupModel.addBox(
            new BoxModel()
                .title("MTTC (Total)")
                .subtitle("(Mean Time to Completion)")
                .variant("secondary")
                .value(DurationUtil.humanReadableDuration(meanExecutionDurationTotal))
        );

        return boxGroupModel;
    }

    private List<DashboardProcessInstanceDTO> getProcessInstances(DashboardRequestModel dashboardRequest) {
        DashboardConfigDTO dashboardConfig = dashboardConfigService.findByProcessDefinition(dashboardRequest.getProcessDefinition());
        BusinessHoursDurationUtil businessHoursDurationUtil = new BusinessHoursDurationUtil(dashboardConfig.getCalendarProperties());

        List<DashboardProcessInstanceDTO> dashboardProcessInstances = processInstanceRepository
            .findByProcessDefinitionIdAndStartDateBetween(
                dashboardRequest.getProcessDefinition().getId(),
                dashboardRequest.getStartDateTime(),
                dashboardRequest.getEndDateTime()
            )
            .stream()
            .map(dashboardProcessInstanceMapper::toDTO)
            .collect(Collectors.toList());

        dashboardProcessInstances.forEach(
            dashboardProcessInstance -> {
                if (dashboardProcessInstance.getStartDate() != null && dashboardProcessInstance.getEndDate() != null) {
                    dashboardProcessInstance.setExecutionDurationBusinessHour(
                        businessHoursDurationUtil.businessHourDuration(
                            dashboardProcessInstance.getStartDate(),
                            dashboardProcessInstance.getEndDate()
                        )
                    );
                    dashboardProcessInstance.setExecutionDurationTotal(
                        Duration.between(dashboardProcessInstance.getStartDate(), dashboardProcessInstance.getEndDate())
                    );
                }
            }
        );

        return dashboardProcessInstances;
    }
}
