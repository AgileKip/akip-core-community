package org.akip.dashboard.web.rest;

import java.time.LocalDate;

import org.akip.dashboard.model.DashboardInterval;
import org.akip.dashboard.model.DashboardRequestModel;
import org.akip.dashboard.model.DashboardResponseModel;
import org.akip.dashboard.service.DashboardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class DashboardController {

    private final Logger log = LoggerFactory.getLogger(DashboardController.class);

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/process-definition/{idOrBpmnProcessDefinitionId}/dashboard")
    public DashboardResponseModel getDashboardByDefaultInterval(@PathVariable("idOrBpmnProcessDefinitionId") String idOrBpmnProcessDefinitionId) {
        log.debug("REST request to get a dashboard of the process definition {}", idOrBpmnProcessDefinitionId);
        DashboardRequestModel dashboardRequest = new DashboardRequestModel()
            .processDefinitionIdOrBpmnProcessDefinitionId(idOrBpmnProcessDefinitionId)
            .interval(DashboardInterval.LAST_90_DAYS);
        return dashboardService.buildDashboard(dashboardRequest);
    }

    @GetMapping("/process-definition/{idOrBpmnProcessDefinitionId}/dashboard/{interval}")
    public DashboardResponseModel getDashboardByInterval(
        @PathVariable("idOrBpmnProcessDefinitionId") String idOrBpmnProcessDefinitionId,
        @PathVariable("interval") DashboardInterval interval
    ) {
        log.debug("REST request to get a dashboard of the process definition {}", idOrBpmnProcessDefinitionId);
        DashboardRequestModel dashboardRequest = new DashboardRequestModel()
            .processDefinitionIdOrBpmnProcessDefinitionId(idOrBpmnProcessDefinitionId)
            .interval(interval);
        return dashboardService.buildDashboard(dashboardRequest);
    }

    @GetMapping("/process-definition/{idOrBpmnProcessDefinitionId}/dashboard/{startDate}/{endDate}")
    public DashboardResponseModel getDashboardByDates(
        @PathVariable("idOrBpmnProcessDefinitionId") String idOrBpmnProcessDefinitionId,
        @PathVariable("startDate") LocalDate startDate,
        @PathVariable("endDate") LocalDate endDate
    ) {
        log.debug("REST request to get a dashboard of the process definition {}", idOrBpmnProcessDefinitionId);
        DashboardRequestModel dashboardRequest = new DashboardRequestModel()
            .processDefinitionIdOrBpmnProcessDefinitionId(idOrBpmnProcessDefinitionId)
            .interval(DashboardInterval.CUSTOM)
            .startDate(startDate)
            .endDate(endDate);
        return dashboardService.buildDashboard(dashboardRequest);
    }
}
