package org.akip.dashboard.service.dto;

import org.akip.domain.enumeration.StatusProcessInstance;

import java.time.Duration;
import java.time.LocalDateTime;

public class DashboardProcessInstanceDTO {

    private Long id;
    private String businessKey;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private StatusProcessInstance status;
    private String tenantName;
    private String username;

    private Duration executionDurationBusinessHour;
    private Duration executionDurationTotal;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBusinessKey() {
        return businessKey;
    }

    public void setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
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

    public StatusProcessInstance getStatus() {
        return status;
    }

    public void setStatus(StatusProcessInstance status) {
        this.status = status;
    }

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Duration getExecutionDurationBusinessHour() {
        return executionDurationBusinessHour;
    }

    public void setExecutionDurationBusinessHour(Duration executionDurationBusinessHour) {
        this.executionDurationBusinessHour = executionDurationBusinessHour;
    }

    public Duration getExecutionDurationTotal() {
        return executionDurationTotal;
    }

    public void setExecutionDurationTotal(Duration executionDurationTotal) {
        this.executionDurationTotal = executionDurationTotal;
    }
}
