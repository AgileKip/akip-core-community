package org.akip.dashboard.service;

import java.util.List;

import org.akip.dashboard.domain.DashboardConfig;
import org.akip.dashboard.repository.DashboardConfigRepository;
import org.akip.dashboard.service.dto.DashboardConfigDTO;
import org.akip.dashboard.service.dto.DashboardGroupConfigDTO;
import org.akip.dashboard.service.mapper.DashboardConfigMapper;
import org.akip.service.ProcessDefinitionService;
import org.akip.service.dto.ProcessDefinitionDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link DashboardConfig}.
 */
@Service
@Transactional
public class DashboardConfigService {

    private final Logger log = LoggerFactory.getLogger(DashboardConfigService.class);

    private final ProcessDefinitionService processDefinitionService;

    private final DashboardConfigRepository dashboardConfigRepository;

    private final DashboardConfigMapper dashboardConfigMapper;

    public DashboardConfigService(
        ProcessDefinitionService processDefinitionService,
        DashboardConfigRepository dashboardConfigRepository,
        DashboardConfigMapper dashboardConfigMapper
    ) {
        this.processDefinitionService = processDefinitionService;
        this.dashboardConfigRepository = dashboardConfigRepository;
        this.dashboardConfigMapper = dashboardConfigMapper;
    }

    public DashboardConfigDTO save(DashboardConfigDTO dashboardConfigDTO) {
        log.debug("Request to save DashboardConfig : {}", dashboardConfigDTO);
        DashboardConfig dashboardConfig = dashboardConfigMapper.toEntity(dashboardConfigDTO);
        dashboardConfig = dashboardConfigRepository.save(dashboardConfig);
        return dashboardConfigMapper.toDto(dashboardConfig);
    }

    @Transactional(readOnly = true)
    public DashboardConfigDTO findByProcessDefinition(String idOrBpmnProcessDefinitionId) {
        log.debug("Request to get DashboardConfig by ProcessDefinitionId: {}", idOrBpmnProcessDefinitionId);
        ProcessDefinitionDTO processDefinition = processDefinitionService
            .findByIdOrBpmnProcessDefinitionId(idOrBpmnProcessDefinitionId)
            .orElseThrow();
        return findByProcessDefinition(processDefinition);
    }

    @Transactional(readOnly = true)
    public DashboardConfigDTO findByProcessDefinition(ProcessDefinitionDTO processDefinition) {
        List<DashboardConfig> listDashboardConfig = dashboardConfigRepository.findByProcessDefinitionId(processDefinition.getId());
        if (listDashboardConfig.isEmpty()) {
            return buildDefaultDashboardConfig(processDefinition);
        }
        return dashboardConfigMapper.toDto(listDashboardConfig.get(0));
    }

    private DashboardConfigDTO buildDefaultDashboardConfig(ProcessDefinitionDTO processDefinition) {
        DashboardConfigDTO dashboardConfig = new DashboardConfigDTO();
        dashboardConfig.setProcessDefinition(processDefinition);

        dashboardConfig.addGroup(
            new DashboardGroupConfigDTO().title("Process Instances Metrics").groupBuilder("akipDashboardProcessInstanceBoxGroupBuilder")
        );

        dashboardConfig.addGroup(
            new DashboardGroupConfigDTO().title("Process Instances Charts").groupBuilder("akipDashboardProcessInstanceChartGroupBuilder")
        );

        dashboardConfig.addGroup(
            new DashboardGroupConfigDTO().title("Tasks Metrics").groupBuilder("akipDashboardTaskInstanceChartGroupBuilder")
        );

        dashboardConfig.addGroup(
            new DashboardGroupConfigDTO()
                .title("Tasks/Participants Metrics")
                .groupBuilder("akipDashboardTaskInstancePerAssigneeChartGroupBuilder")
        );

        dashboardConfig.setCalendarProperties(buildCalendarProperties());

        return dashboardConfig;
    }

    private String buildCalendarProperties() {
        StringBuilder sb = new StringBuilder();
        sb.append("weekday.monday = 08:00-12:00 & 14:00-18:00\n");
        sb.append("weekday.tuesday = 08:00-12:00 & 14:00-18:00\n");
        sb.append("weekday.wednesday = 08:00-12:00 & 14:00-18:00\n");
        sb.append("weekday.thursday = 08:00-12:00 & 14:00-18:00\n");
        sb.append("weekday.friday = 08:00-12:00 & 14:00-18:00\n");
        sb.append("weekday.saturday = \n");
        sb.append("weekday.sunday = \n");
        return sb.toString();
    }

    public void delete(Long id) {
        log.debug("Request to delete DashboardConfig : {}", id);
        dashboardConfigRepository.deleteById(id);
    }
}
