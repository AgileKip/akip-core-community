package org.akip.dashboard.builder;


import org.akip.dashboard.model.BoxGroupModel;
import org.akip.dashboard.model.DashboardRequestModel;
import org.akip.dashboard.service.dto.DashboardGroupConfigDTO;

public abstract class DashboardBoxGroupBuilder extends DashboardGroupBuilder {

    @Override
    public abstract BoxGroupModel buildGroup(DashboardRequestModel dashboardRequest, DashboardGroupConfigDTO groupConfig);
}
