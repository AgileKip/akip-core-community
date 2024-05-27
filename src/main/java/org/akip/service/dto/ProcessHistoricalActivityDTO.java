package org.akip.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * A DTO for representing the ProcessHistoricalActivity.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProcessHistoricalActivityDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private String id;
    private String activityType;
    private String activityName;
    private String executionId;
    private String processInstanceId;
    private String activityId;
    private Date startTime;
    private Date endTime;
    private String tenantId;

    public ProcessHistoricalActivityDTO(String id, String activityType, String activityName, String executionId, String processInstanceId, String activityId, Date startTime, Date endTime, String tenantId) {
        this.id = id;
        this.activityType = activityType;
        this.activityName = activityName;
        this.executionId = executionId;
        this.processInstanceId = processInstanceId;
        this.activityId = activityId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.tenantId = tenantId;
    }

    public ProcessHistoricalActivityDTO() {}

    public void setId(String id) {
        this.id = id;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public void setExecutionId(String executionId) {
        this.executionId = executionId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getId() {
        return id;
    }

    public String getParentActivityInstanceId() {
        return null;
    }

    public String getActivityType() {
        return activityType;
    }

    public String getProcessDefinitionKey() {
        return null;
    }

    public String getProcessDefinitionId() {
        return null;
    }

    public String getRootProcessInstanceId() {
        return null;
    }

    public String getActivityName() {
        return activityName;
    }

    public String getExecutionId() {
        return executionId;
    }

    public String getTaskId() {
        return null;
    }

    public String getCalledProcessInstanceId() {
        return null;
    }

    public String getCalledCaseInstanceId() {
        return null;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public String getActivityId() {
        return activityId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public Long getDurationInMillis() {
        return null;
    }

    public boolean isCompleteScope() {
        return false;
    }

    public boolean isCanceled() {
        return false;
    }

    public String getTenantId() {
        return tenantId;
    }

    public Date getRemovalTime() {
        return null;
    }

    public String getAssignee() {
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProcessHistoricalActivityDTO)) {
            return false;
        }

        ProcessHistoricalActivityDTO camundahistoryActivityDTO = (ProcessHistoricalActivityDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, camundahistoryActivityDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProcessHistoricalActivityDTO{" +
            "id=" + getId() +
            "processInstanceId=" + getProcessInstanceId() +
            "activityType=" + getActivityType() +
            "activityName=" + getActivityName() +
            "}";
    }
}
