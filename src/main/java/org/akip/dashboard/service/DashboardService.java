package org.akip.dashboard.service;

import java.math.BigDecimal;
import java.util.stream.Collectors;

import org.akip.dashboard.builder.DashboardGroupBuilder;
import org.akip.dashboard.model.*;
import org.akip.dashboard.service.dto.DashboardConfigDTO;
import org.akip.dashboard.service.dto.DashboardGroupConfigDTO;
import org.akip.dashboard.util.IntervalUtil;
import org.akip.exception.BadRequestErrorException;
import org.akip.service.ProcessDefinitionService;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {

    private final ProcessDefinitionService processDefinitionService;

    private final DashboardConfigService dashboardConfigService;

    private final ApplicationContext applicationContext;

    public DashboardService(
        ProcessDefinitionService processDefinitionService,
        DashboardConfigService dashboardConfigService,
        ApplicationContext applicationContext
    ) {
        this.processDefinitionService = processDefinitionService;
        this.dashboardConfigService = dashboardConfigService;
        this.applicationContext = applicationContext;
    }

    public DashboardResponseModel buildDashboard(DashboardRequestModel dashboardRequest) {
        DashboardResponseModel dashboardResponseModel = new DashboardResponseModel();
        try {
            dashboardRequest.setProcessDefinition(
                processDefinitionService
                    .findByIdOrBpmnProcessDefinitionId(dashboardRequest.getProcessDefinitionIdOrBpmnProcessDefinitionId())
                    .orElseThrow()
            );
            dashboardRequest.setStartDateTime(IntervalUtil.getStartDateTime(dashboardRequest));
            dashboardRequest.setEndDateTime(IntervalUtil.getEndDateTime(dashboardRequest));

            dashboardResponseModel.setProcessDefinition(dashboardRequest.getProcessDefinition());
            dashboardResponseModel.setInterval(dashboardRequest.getInterval());
            dashboardResponseModel.setStartDate(dashboardRequest.getStartDateTime());
            dashboardResponseModel.setEndDate(dashboardRequest.getEndDateTime());

            DashboardConfigDTO dashboardConfig = dashboardConfigService.findByProcessDefinition(dashboardRequest.getProcessDefinition());

            dashboardConfig
                .getGroups()
                .forEach(
                    groupConfig -> {
                        dashboardResponseModel.addGroup(buildGroup(dashboardRequest, groupConfig).title(groupConfig.getTitle()));
                    }
                );

            prepareChartModels(dashboardResponseModel);
        } catch (Exception e) {
            throw new BadRequestErrorException("travelPlanEnterpriseApp.dashboard.error", e.getMessage());
        }
        return dashboardResponseModel;
    }

    private GroupModel buildGroup(DashboardRequestModel dashboardRequest, DashboardGroupConfigDTO groupConfig) {
        DashboardGroupBuilder dashboardGroupBuilder = (DashboardGroupBuilder) applicationContext.getBean(groupConfig.getGroupBuilder());
        return dashboardGroupBuilder.buildGroup(dashboardRequest, groupConfig);
    }

    private void prepareChartModels(DashboardResponseModel dashboardResponseModel) {
        dashboardResponseModel.getGroups().forEach(this::prepareChartModels);
    }

    private void prepareChartModels(GroupModel groupModel) {
        if (!(groupModel instanceof CardGroupModel)) {
            return;
        }

        CardGroupModel cardGroupModel = (CardGroupModel) groupModel;
        cardGroupModel.getCards().forEach(this::prepareChartModels);
    }

    private void prepareChartModels(CardModel cardModel) {
        for (int index = 1; index < cardModel.getTableModel().getHeads().size(); index++) {
            cardModel.addCardChartModel(buildCardChartModel(cardModel.getTableModel(), index));
        }
    }

    private CardChartModel buildCardChartModel(TableModel tableModel, int index) {
        CardChartModel cardChartModel = new CardChartModel();
        cardChartModel.setTitle(tableModel.getHeads().get(index));
        cardChartModel.setBarChartModel(tableModelToBarChartModel(tableModel, index - 1));
        cardChartModel.setPieChartModel(tableModelToPieChartModel(tableModel, index - 1));
        return cardChartModel;
    }

    private BarChartModel tableModelToBarChartModel(TableModel tableModel, final int index) {
        if (tableModel.getRows().isEmpty()) {
            return null;
        }
        BarChartModel barChartModel = new BarChartModel();

        barChartModel.setCategories(tableModel.getRows().stream().map(row -> row.getTitle()).collect(Collectors.toList()));

        String head = tableModel.getHeads().get(index);
        SerieModel serie = new SerieModel()
            .name(head)
            .data(tableModel.getRows().stream().map(row -> row.getValues().get(index)).collect(Collectors.toList()))
            .dataHumanReadable(
                tableModel
                    .getRows()
                    .stream()
                    .map(
                        row ->
                            (row.getHumanReadableValues().isEmpty())
                                ? String.valueOf(row.getValues().get(index))
                                : row.getHumanReadableValues().get(index)
                    )
                    .collect(Collectors.toList())
            );
        barChartModel.addSerie(serie);

        return barChartModel;
    }

    private PieChartModel tableModelToPieChartModel(TableModel tableModel, int index) {
        if (tableModel.getRows().isEmpty()) {
            return null;
        }
        PieChartModel pieChartModel = new PieChartModel();

        pieChartModel.setLabels(tableModel.getRows().stream().map(row -> row.getTitle()).collect(Collectors.toList()));

        pieChartModel.setSeries(
            tableModel
                .getRows()
                .stream()
                .map(row -> row.getValues().get(index) != null ? row.getValues().get(index) : BigDecimal.ZERO)
                .collect(Collectors.toList())
        );

        pieChartModel.setSeriesHumanReadable(
            tableModel
                .getRows()
                .stream()
                .map(
                    row ->
                        row.getHumanReadableValues().isEmpty()
                            ? String.valueOf(row.getValues().get(index))
                            : row.getHumanReadableValues().get(index)
                )
                .collect(Collectors.toList())
        );

        return pieChartModel;
    }
}
