package org.akip.dashboard.model;

import org.akip.service.dto.ProcessDefinitionDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class DashboardRequestModel {

    private String processDefinitionIdOrBpmnProcessDefinitionId;
    private DashboardInterval interval;
    private LocalDate startDate;
    private LocalDate endDate;

    private ProcessDefinitionDTO processDefinition;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    public DashboardRequestModel processDefinitionIdOrBpmnProcessDefinitionId(String processDefinitionIdOrBpmnProcessDefinitionId) {
        this.processDefinitionIdOrBpmnProcessDefinitionId = processDefinitionIdOrBpmnProcessDefinitionId;
        return this;
    }

    public String getProcessDefinitionIdOrBpmnProcessDefinitionId() {
        return processDefinitionIdOrBpmnProcessDefinitionId;
    }

    public void setProcessDefinitionIdOrBpmnProcessDefinitionId(String processDefinitionIdOrBpmnProcessDefinitionId) {
        this.processDefinitionIdOrBpmnProcessDefinitionId = processDefinitionIdOrBpmnProcessDefinitionId;
    }

    public DashboardRequestModel interval(DashboardInterval interval) {
        this.interval = interval;
        return this;
    }

    public DashboardInterval getInterval() {
        return interval;
    }

    public void setInterval(DashboardInterval interval) {
        this.interval = interval;
    }

    public DashboardRequestModel startDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public DashboardRequestModel endDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public ProcessDefinitionDTO getProcessDefinition() {
        return processDefinition;
    }

    public void setProcessDefinition(ProcessDefinitionDTO processDefinition) {
        this.processDefinition = processDefinition;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }
}
