package org.akip.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import jakarta.persistence.Lob;
import org.camunda.bpm.engine.runtime.Job;

/**
 * A DTO for representing the "JOB" entity from camunda.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CamundaJobDTO implements Serializable, Job {

    private String id;
    private String processDefinitionId;
    private String processDefinitionKey;
    private String processInstanceId;
    private String failedActivityId;
    private String jobDefinitionId;
    private Date createTime;
    private Date dueDate;
    private int retries;
    private boolean suspended;
    private long priority;
    private String tenantId;
    private String jobHandlerConfiguration;
    private String executionId;

    @Lob
    private String exceptionMessage;

    public CamundaJobDTO(
        String id,
        String processDefinitionId,
        String processDefinitionKey,
        String processInstanceId,
        String failedActivityId,
        String jobDefinitionId,
        Date createTime,
        Date dueDate,
        int retries,
        boolean suspended,
        long priority,
        String tenantId,
        String jobHandlerConfiguration,
        String executionId,
        String exceptionMessage
    ) {
        this.id = id;
        this.processDefinitionId = processDefinitionId;
        this.processDefinitionKey = processDefinitionKey;
        this.processInstanceId = processInstanceId;
        this.failedActivityId = failedActivityId;
        this.jobDefinitionId = jobDefinitionId;
        this.createTime = createTime;
        this.dueDate = dueDate;
        this.retries = retries;
        this.suspended = suspended;
        this.priority = priority;
        this.tenantId = tenantId;
        this.jobHandlerConfiguration = jobHandlerConfiguration;
        this.executionId = executionId;
        this.exceptionMessage = exceptionMessage;
    }

    public CamundaJobDTO() {}

    public String getId() {
        return id;
    }

    @Override
    public Date getDuedate() {
        return null;
    }

    @Override
    public String getRootProcessInstanceId() {
        return "";
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProcessDefinitionId() {
        return processDefinitionId;
    }

    public void setProcessDefinitionId(String processDefinitionId) {
        this.processDefinitionId = processDefinitionId;
    }

    public String getProcessDefinitionKey() {
        return processDefinitionKey;
    }

    public void setProcessDefinitionKey(String processDefinitionKey) {
        this.processDefinitionKey = processDefinitionKey;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getFailedActivityId() {
        return failedActivityId;
    }

    @Override
    public String getDeploymentId() {
        return null;
    }

    public void setFailedActivityId(String failedActivityId) {
        this.failedActivityId = failedActivityId;
    }

    public String getJobDefinitionId() {
        return jobDefinitionId;
    }

    public void setJobDefinitionId(String jobDefinitionId) {
        this.jobDefinitionId = jobDefinitionId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public int getRetries() {
        return retries;
    }

    public void setRetries(int retries) {
        this.retries = retries;
    }

    public boolean isSuspended() {
        return suspended;
    }

    public void setSuspended(boolean suspended) {
        this.suspended = suspended;
    }

    public long getPriority() {
        return priority;
    }

    public void setPriority(long priority) {
        this.priority = priority;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getJobHandlerConfiguration() {
        return jobHandlerConfiguration;
    }

    public void setJobHandlerConfiguration(String jobHandlerConfiguration) {
        this.jobHandlerConfiguration = jobHandlerConfiguration;
    }

    public String getExecutionId() {
        return executionId;
    }

    public void setExecutionId(String executionId) {
        this.executionId = executionId;
    }

    public String getExceptionMessage() {
        return exceptionMessage;
    }

    public void setExceptionMessage(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CamundaJobDTO)) {
            return false;
        }

        CamundaJobDTO camundaJobDTO = (CamundaJobDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, camundaJobDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CamundaJobDTO{" +
            "id=" + getId() +
            "processDefinitionId=" + getProcessDefinitionId() +
            "processInstanceId=" + getProcessInstanceId() +
            "failedActivityId=" + getFailedActivityId() +
            "createTime=" + getCreateTime() +
            "dueDate=" + getDueDate() +
            "retries=" + getRetries() +
            "exceptionMessage=" + getExceptionMessage() +
          "}";
    }
}
