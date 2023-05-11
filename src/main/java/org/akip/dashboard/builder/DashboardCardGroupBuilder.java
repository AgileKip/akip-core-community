package org.akip.dashboard.builder;

import org.akip.dashboard.model.CardGroupModel;
import org.akip.dashboard.model.DashboardRequestModel;
import org.akip.dashboard.service.dto.DashboardGroupConfigDTO;

public abstract class DashboardCardGroupBuilder extends DashboardGroupBuilder {

    @Override
    public abstract CardGroupModel buildGroup(DashboardRequestModel dashboardRequest, DashboardGroupConfigDTO groupConfig);
}
