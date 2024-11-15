package org.akip.dashboard.builder;

import org.akip.dashboard.model.DashboardRequestModel;
import org.akip.dashboard.model.GroupModel;
import org.akip.dashboard.service.dto.DashboardGroupConfigDTO;

public abstract class DashboardGroupBuilder {

    public abstract GroupModel buildGroup(DashboardRequestModel dashboardRequest, DashboardGroupConfigDTO groupConfig);
}
