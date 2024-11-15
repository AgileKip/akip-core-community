package org.akip.service.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

public class ProcessTimelineItemDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String icon;

    private String status;

    private String title;

    private LocalDateTime createdDate;

    private ProcessTimelineItemDefinitionDTO itemDefinition;

    public ProcessTimelineItemDTO(Long id, String icon, String status, String title, LocalDateTime createdDate) {
        this.id = id;
        this.icon = icon;
        this.status = status;
        this.title = title;
        this.createdDate = createdDate;
    }

    public ProcessTimelineItemDTO() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public ProcessTimelineItemDefinitionDTO getItemDefinition() {
        return itemDefinition;
    }

    public void setItemDefinition(ProcessTimelineItemDefinitionDTO itemDefinition) {
        this.itemDefinition = itemDefinition;
    }

    public ProcessTimelineItemDTO id(Long id) {
        this.id = id;
        return this;
    }

    public ProcessTimelineItemDTO icon(String icon) {
        this.icon = icon;
        return this;
    }

    public ProcessTimelineItemDTO status(String status) {
        this.status = status;
        return this;
    }

    public ProcessTimelineItemDTO title(String title) {
        this.title = title;
        return this;
    }

    public ProcessTimelineItemDTO createdDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public ProcessTimelineItemDTO itemDefinition(ProcessTimelineItemDefinitionDTO itemDefinition) {
        this.itemDefinition = itemDefinition;
        return this;
    }
}
