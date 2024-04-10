package org.akip.service.dto.searchDto;

import org.akip.domain.enumeration.ProcessInstanceNotificationStatus;
import org.akip.domain.enumeration.ProcessInstanceNotificationType;

import java.time.LocalDate;

public class ProcessInstanceNotificationSearchDTO {

    private Long id;

    private String title;

    private String description;

    private LocalDate date;

    private ProcessInstanceNotificationStatus status;

    private ProcessInstanceNotificationType eventType;

    private String subscriberId;

    private String processInstanceName;

    public ProcessInstanceNotificationSearchDTO(
        Long id,
        String title,
        String description,
        LocalDate date,
        ProcessInstanceNotificationStatus status,
        ProcessInstanceNotificationType eventType,
        String subscriberId,
        String processInstanceName
    ) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.date = date;
        this.status = status;
        this.eventType = eventType;
        this.subscriberId = subscriberId;
        this.processInstanceName = processInstanceName;
    }

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

    public String getProcessInstanceName() {
        return processInstanceName;
    }

    public void setProcessInstanceName(String processInstanceName) {
        this.processInstanceName = processInstanceName;
    }
}
