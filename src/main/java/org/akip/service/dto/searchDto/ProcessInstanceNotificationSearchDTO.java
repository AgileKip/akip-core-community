package org.akip.service.dto.searchDto;

import org.akip.domain.enumeration.ProcessInstanceEventType;
import org.akip.domain.enumeration.ProcessInstanceNotificationStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ProcessInstanceNotificationSearchDTO {

    private Long id;

    private String title;

    private String description;

    private LocalDateTime createDate;

    private LocalDateTime readDate;

    private ProcessInstanceNotificationStatus status;

    private ProcessInstanceEventType eventType;

    private String subscriberId;

    private String processInstanceName;

    public ProcessInstanceNotificationSearchDTO(
        Long id,
        String title,
        String description,
        LocalDateTime createDate,
        LocalDateTime readDate,
        ProcessInstanceNotificationStatus status,
        ProcessInstanceEventType eventType,
        String subscriberId,
        String processInstanceName
    ) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.createDate = createDate;
        this.readDate = readDate;
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

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getReadDate() { return readDate; }

    public void setReadDate(LocalDateTime readDate) { this.readDate = readDate; }

    public ProcessInstanceNotificationStatus getStatus() {
        return status;
    }

    public void setStatus(ProcessInstanceNotificationStatus status) {
        this.status = status;
    }

    public ProcessInstanceEventType getEventType() {
        return eventType;
    }

    public void setEventType(ProcessInstanceEventType eventType) {
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
