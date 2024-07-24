package org.akip.service.dto;

import org.akip.domain.enumeration.ProcessInstanceNotificationStatus;
import org.akip.domain.enumeration.ProcessInstanceNotificationType;
import org.akip.domain.ProcessInstance;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link org.akip.domain.ProcessInstanceNotification} entity.
 */
public class ProcessInstanceNotificationDTO implements Serializable {

    private Long id;

    private String title;

    private String description;

    private LocalDate date;

    private ProcessInstanceNotificationStatus status;

    private ProcessInstanceNotificationType eventType;

    private String subscriberId;

    private ProcessInstance processInstance;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public ProcessInstanceNotificationStatus getStatus() {
        return status;
    }

    public void setStatus(ProcessInstanceNotificationStatus status) {
        this.status = status;
    }

    public ProcessInstanceNotificationType getEventType() {
        return eventType;
    }

    public void setEventType(ProcessInstanceNotificationType eventType) {
        this.eventType = eventType;
    }

    public String getSubscriberId() {
        return subscriberId;
    }

    public void setSubscriberId(String subscriberId) {
        this.subscriberId = subscriberId;
    }

    public ProcessInstance getProcessInstance() {
        return processInstance;
    }

    public void setProcessInstance(ProcessInstance processInstance) {
        this.processInstance = processInstance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProcessInstanceNotificationDTO)) {
            return false;
        }

        ProcessInstanceNotificationDTO processInstanceNotificationDTO = (ProcessInstanceNotificationDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, processInstanceNotificationDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProcessInstanceNotificationDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            "}";
    }
}
