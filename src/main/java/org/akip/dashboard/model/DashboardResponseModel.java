package org.akip.dashboard.model;

import org.akip.service.dto.ProcessDefinitionDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DashboardResponseModel {

    private String title;
    private String subtitle;
    private ProcessDefinitionDTO processDefinition;
    private DashboardInterval interval;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private LocalDateTime buildDate;

    private List<GroupModel> groups = new ArrayList<>();

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public ProcessDefinitionDTO getProcessDefinition() {
        return processDefinition;
    }

    public void setProcessDefinition(ProcessDefinitionDTO processDefinition) {
        this.processDefinition = processDefinition;
    }

    public DashboardInterval getInterval() {
        return interval;
    }

    public void setInterval(DashboardInterval interval) {
        this.interval = interval;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public LocalDateTime getBuildDate() {
        return buildDate;
    }

    public void setBuildDate(LocalDateTime buildDate) {
        this.buildDate = buildDate;
    }

    public DashboardResponseModel addGroup(GroupModel groupModel) {
        groups.add(groupModel);
        return this;
    }

    public List<GroupModel> getGroups() {
        return groups;
    }

    public void setGroups(List<GroupModel> groups) {
        this.groups = groups;
    }
}
