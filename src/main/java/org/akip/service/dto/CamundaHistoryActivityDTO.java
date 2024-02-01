package org.akip.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.camunda.bpm.engine.history.HistoricActivityInstance;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * A DTO for representing the "HistoricActivityInstance" entity from camunda.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CamundaHistoryActivityDTO implements Serializable, HistoricActivityInstance {

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

    public CamundaHistoryActivityDTO(String id, String activityType, String activityName, String executionId, String processInstanceId, String activityId, Date startTime, Date endTime, String tenantId) {
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

    public CamundaHistoryActivityDTO() {}

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

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getParentActivityInstanceId() {
        return null;
    }

    @Override
    public String getActivityType() {
        return activityType;
    }

    @Override
    public String getProcessDefinitionKey() {
        return null;
    }

    @Override
    public String getProcessDefinitionId() {
        return null;
    }

    @Override
    public String getRootProcessInstanceId() {
        return null;
    }

    @Override
    public String getActivityName() {
        return activityName;
    }

    @Override
    public String getExecutionId() {
        return executionId;
    }

    @Override
    public String getTaskId() {
        return null;
    }

    @Override
    public String getCalledProcessInstanceId() {
        return null;
    }

    @Override
    public String getCalledCaseInstanceId() {
        return null;
    }

    @Override
    public String getProcessInstanceId() {
        return processInstanceId;
    }

    @Override
    public String getActivityId() {
        return activityId;
    }

    @Override
    public Date getStartTime() {
        return startTime;
    }

    @Override
    public Date getEndTime() {
        return endTime;
    }

    @Override
    public Long getDurationInMillis() {
        return null;
    }

    @Override
    public boolean isCompleteScope() {
        return false;
    }

    @Override
    public boolean isCanceled() {
        return false;
    }

    @Override
    public String getTenantId() {
        return tenantId;
    }

    @Override
    public Date getRemovalTime() {
        return null;
    }

    @Override
    public String getAssignee() {
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CamundaHistoryActivityDTO)) {
            return false;
        }

        CamundaHistoryActivityDTO camundahistoryActivityDTO = (CamundaHistoryActivityDTO) o;
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
        return "CamundaJobDTO{" +
            "id=" + getId() +
            "processInstanceId=" + getProcessInstanceId() +
            "activityType=" + getActivityType() +
            "activityName=" + getActivityName() +
            "}";
    }
}
