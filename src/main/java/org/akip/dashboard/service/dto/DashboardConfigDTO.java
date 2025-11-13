package org.akip.dashboard.service.dto;

import org.akip.service.dto.ProcessDefinitionDTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import jakarta.persistence.Lob;

/**
 * A DTO for the {@link DashboardConfig} entity.
 */
public class DashboardConfigDTO implements Serializable {

    private Long id;

    @Lob
    private String calendarProperties;

    private ProcessDefinitionDTO processDefinition;

    private List<DashboardGroupConfigDTO> groups = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCalendarProperties() {
        return calendarProperties;
    }

    public void setCalendarProperties(String calendarProperties) {
        this.calendarProperties = calendarProperties;
    }

    public ProcessDefinitionDTO getProcessDefinition() {
        return processDefinition;
    }

    public void setProcessDefinition(ProcessDefinitionDTO processDefinition) {
        this.processDefinition = processDefinition;
    }

    public DashboardConfigDTO addGroup(DashboardGroupConfigDTO group) {
        groups.add(group);
        return this;
    }

    public List<DashboardGroupConfigDTO> getGroups() {
        return groups;
    }

    public void setGroups(List<DashboardGroupConfigDTO> groups) {
        this.groups = groups;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DashboardConfigDTO)) {
            return false;
        }

        DashboardConfigDTO dashboardConfigDTO = (DashboardConfigDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, dashboardConfigDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DashboardConfigDTO{" +
            "id=" + getId() +
            "}";
    }
}
