package org.akip.service.dto;


import org.akip.domain.ProcessDefinition;
import org.akip.domain.enumeration.ActiveInactiveStatus;
import org.akip.domain.enumeration.SubscriberType;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link org.akip.domain.ProcessDefinitionSubscription } entity.
 */
public class ProcessDefinitionSubscriptionDTO implements Serializable {

    private Long id;

    private SubscriberType subscriberType;

    private String subscriberId;

    private ActiveInactiveStatus status;

    private LocalDate subscriptionDate;

    private Boolean notifyAll;

    private Boolean notifyTasks;

    private Boolean notifyAttachments;

    private Boolean notifyNotes;

    private Boolean notifyChats;

    private String bpmnProcessDefinitionId;

    private ProcessDefinition processDefinition;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SubscriberType getSubscriberType() {
        return subscriberType;
    }

    public void setSubscriberType(SubscriberType subscriberType) {
        this.subscriberType = subscriberType;
    }

    public String getSubscriberId() {
        return subscriberId;
    }

    public void setSubscriberId(String subscriberId) {
        this.subscriberId = subscriberId;
    }

    public ActiveInactiveStatus getStatus() {
        return status;
    }

    public void setStatus(ActiveInactiveStatus status) {
        this.status = status;
    }

    public LocalDate getSubscriptionDate() {
        return subscriptionDate;
    }

    public void setSubscriptionDate(LocalDate subscriptionDate) {
        this.subscriptionDate = subscriptionDate;
    }

    public Boolean getNotifyAll() {
        return notifyAll;
    }

    public void setNotifyAll(Boolean notifyAll) {
        this.notifyAll = notifyAll;
    }

    public Boolean getNotifyTasks() {
        return notifyTasks;
    }

    public void setNotifyTasks(Boolean notifyTasks) {
        this.notifyTasks = notifyTasks;
    }

    public Boolean getNotifyAttachments() {
        return notifyAttachments;
    }

    public void setNotifyAttachments(Boolean notifyAttachments) {
        this.notifyAttachments = notifyAttachments;
    }

    public Boolean getNotifyNotes() {
        return notifyNotes;
    }

    public void setNotifyNotes(Boolean notifyNotes) {
        this.notifyNotes = notifyNotes;
    }

    public Boolean getNotifyChats() {
        return notifyChats;
    }

    public void setNotifyChats(Boolean notifyChats) {
        this.notifyChats = notifyChats;
    }

    public ProcessDefinition getProcessDefinition() {
        return processDefinition;
    }

    public void setProcessDefinition(ProcessDefinition processDefinition) {
        this.processDefinition = processDefinition;
    }

    public String getBpmnProcessDefinitionId() {
        return bpmnProcessDefinitionId;
    }

    public void setBpmnProcessDefinitionId(String bpmnProcessDefinitionId) {
        this.bpmnProcessDefinitionId = bpmnProcessDefinitionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProcessDefinitionSubscriptionDTO)) {
            return false;
        }

        ProcessDefinitionSubscriptionDTO processDefinitionSubscriptionDTO = (ProcessDefinitionSubscriptionDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, processDefinitionSubscriptionDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProcessDefinitionSubscriptionDTO{" +
            "id=" + getId() +
            ", subscriberType='" + getSubscriberType() + "'" +
            ", subscriberId='" + getSubscriberId() + "'" +
            "}";
    }
}
