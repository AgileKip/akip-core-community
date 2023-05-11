package org.akip.dashboard.builder;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.akip.dashboard.model.*;
import org.akip.dashboard.service.dto.DashboardGroupConfigDTO;
import org.akip.dashboard.service.dto.DashboardProcessInstanceDTO;
import org.akip.dashboard.service.mapper.DashboardProcessInstanceMapper;
import org.akip.repository.ProcessInstanceRepository;
import org.springframework.stereotype.Service;

@Service
public class AkipDashboardProcessInstanceChartGroupBuilder extends DashboardGroupBuilder {

    private final ProcessInstanceRepository processInstanceRepository;

    private final DashboardProcessInstanceMapper dashboardProcessInstanceMapper;

    public AkipDashboardProcessInstanceChartGroupBuilder(
        ProcessInstanceRepository processInstanceRepository,
        DashboardProcessInstanceMapper dashboardProcessInstanceMapper
    ) {
        this.processInstanceRepository = processInstanceRepository;
        this.dashboardProcessInstanceMapper = dashboardProcessInstanceMapper;
    }

    @Override
    public GroupModel buildGroup(DashboardRequestModel dashboardRequest, DashboardGroupConfigDTO groupConfig) {
        List<DashboardProcessInstanceDTO> processInstances = getProcessInstances(dashboardRequest);

        CardGroupModel cardGroup = new CardGroupModel().title(groupConfig.getTitle());
        cardGroup.getCards().add(buildCardModelProcessesByTenant(processInstances));
        return cardGroup;
    }

    private CardModel buildCardModelProcessesByTenant(List<DashboardProcessInstanceDTO> processInstances) {
        CardModel card = new CardModel().title("Processes by Tenant");

        card.getTableModel().addHead("Tenant").addHead("Number of Processes");

        List<String> tenants = processInstances
            .stream()
            .map(processInstance -> processInstance.getTenantName())
            .collect(Collectors.toSet())
            .stream()
            .sorted((tenantName1, tenantName2) -> tenantName1.compareTo(tenantName2))
            .collect(Collectors.toList());

        tenants.forEach(
            tenantName -> {
                TableRowModel tableRow = new TableRowModel();
                if (tenantName == null) {
                    tableRow.setTitle("No Tenant");
                    tableRow.addValue(
                        new BigDecimal(processInstances.stream().filter(processInstance -> processInstance.getTenantName() == null).count())
                    );
                } else {
                    tableRow.setTitle(tenantName);
                    tableRow.addValue(
                        new BigDecimal(
                            processInstances.stream().filter(processInstance -> processInstance.getTenantName().equals(tenantName)).count()
                        )
                    );
                }
                card.getTableModel().addRow(tableRow);
            }
        );
        return card;
    }

    private List<DashboardProcessInstanceDTO> getProcessInstances(DashboardRequestModel dashboardRequest) {
        return processInstanceRepository
            .findByProcessDefinitionIdAndStartDateBetween(
                dashboardRequest.getProcessDefinition().getId(),
                dashboardRequest.getStartDateTime(),
                dashboardRequest.getEndDateTime()
            )
            .stream()
            .map(dashboardProcessInstanceMapper::toDTO)
            .collect(Collectors.toList());
    }
}
