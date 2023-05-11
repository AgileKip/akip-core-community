package org.akip.dashboard.service.dto;

import org.akip.domain.enumeration.StatusTaskInstance;

import java.time.Duration;
import java.time.Instant;

public class DashboardTaskInstanceDTO {

    private Long id;
    private String taskId;
    private String name;
    private String assignee;
    private StatusTaskInstance status;
    private Instant createTime;
    private Instant startTime;
    private Instant endTime;
    private Duration unassignedDurationBusinessHour;
    private Duration unassignedDurationTotal;
    private Duration executionDurationBusinessHour;
    private Duration executionDurationTotal;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public StatusTaskInstance getStatus() {
        return status;
    }

    public void setStatus(StatusTaskInstance status) {
        this.status = status;
    }

    public Instant getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Instant createTime) {
        this.createTime = createTime;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public Instant getEndTime() {
        return endTime;
    }

    public void setEndTime(Instant endTime) {
        this.endTime = endTime;
    }

    public Duration getUnassignedDurationBusinessHour() {
        return unassignedDurationBusinessHour;
    }

    public void setUnassignedDurationBusinessHour(Duration unassignedDurationBusinessHour) {
        this.unassignedDurationBusinessHour = unassignedDurationBusinessHour;
    }

    public Duration getUnassignedDurationTotal() {
        return unassignedDurationTotal;
    }

    public void setUnassignedDurationTotal(Duration unassignedDurationTotal) {
        this.unassignedDurationTotal = unassignedDurationTotal;
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
