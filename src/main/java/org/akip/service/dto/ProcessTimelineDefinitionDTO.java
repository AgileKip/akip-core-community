package org.akip.service.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProcessTimelineDefinitionDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    private String description;

    private String conditionExpression;

    private ProcessDefinitionDTO processDefinitionDTO;

    private List<ProcessTimelineItemDefinitionDTO> items = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getConditionExpression() {
        return conditionExpression;
    }

    public void setConditionExpression(String conditionExpression) {
        this.conditionExpression = conditionExpression;
    }

    public ProcessDefinitionDTO getProcessDefinitionDTO() {
        return processDefinitionDTO;
    }

    public void setProcessDefinitionDTO(ProcessDefinitionDTO processDefinitionDTO) {
        this.processDefinitionDTO = processDefinitionDTO;
    }

    public List<ProcessTimelineItemDefinitionDTO> getItems() {
        return items;
    }

    public void setItems(List<ProcessTimelineItemDefinitionDTO> items) {
        this.items = items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProcessTimelineDefinitionDTO that = (ProcessTimelineDefinitionDTO) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(description, that.description) &&
            Objects.equals(conditionExpression, that.conditionExpression) &&
            Objects.equals(processDefinitionDTO, that.processDefinitionDTO) &&
            Objects.equals(items, that.items)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, conditionExpression, processDefinitionDTO, items);
    }
}
