package org.akip.dashboard.model;

import java.util.ArrayList;
import java.util.List;

public class DashboardConfigModel {

    private List<GroupConfigModel> groups = new ArrayList<>();

    public DashboardConfigModel addGroup(GroupConfigModel groupConfigModel) {
        groups.add(groupConfigModel);
        return this;
    }

    public List<GroupConfigModel> getGroups() {
        return groups;
    }

    public void setGroups(List<GroupConfigModel> groups) {
        this.groups = groups;
    }
}
