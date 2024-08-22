package org.akip.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.camunda.bpm.engine.runtime.EventSubscription;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * A DTO for representing the "ProcessEventSubscription" entity.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProcessEventSubscriptionDTO implements Serializable, EventSubscription {

    private static final long serialVersionUID = 1L;

    private String id;
    private String eventType;
    private String eventName;
    private String executionId;
    private String processInstanceId;
    private String activityId;
    private Date created;
    private String tenantId;

    public ProcessEventSubscriptionDTO(
        String id,
        String eventType,
        String eventName,
        String executionId,
        String processInstanceId,
        String activityId,
        Date created,
        String tenantId
    ) {
        this.id = id;
        this.eventType = eventType;
        this.eventName = eventName;
        this.executionId = executionId;
        this.processInstanceId = processInstanceId;
        this.activityId = activityId;
        this.created = created;
        this.tenantId = tenantId;
    }

    public ProcessEventSubscriptionDTO() {}

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    @Override
    public String getExecutionId() {
        return executionId;
    }

    public void setExecutionId(String executionId) {
        this.executionId = executionId;
    }

    @Override
    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    @Override
    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProcessEventSubscriptionDTO)) {
            return false;
        }

        ProcessEventSubscriptionDTO camundaEventSubscriptionDTO = (ProcessEventSubscriptionDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, camundaEventSubscriptionDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProcessEventSubscriptionDTO{" +
            "id=" + getId() +
            "processInstanceId=" + getProcessInstanceId() +
            "eventType=" + getEventType() +
            "eventName=" + getEventName() +
            "}";
    }
}
