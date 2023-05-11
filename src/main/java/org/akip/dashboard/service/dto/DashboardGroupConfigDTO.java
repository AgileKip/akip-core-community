package org.akip.dashboard.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link DashboardGroupConfig} entity.
 */
public class DashboardGroupConfigDTO implements Serializable {

    private Long id;

    private String title;

    private String groupBuilder;

    @Lob
    private String expression;

    private DashboardConfigDTO dashboardConfig;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DashboardGroupConfigDTO title(String title) {
        this.title = title;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public DashboardGroupConfigDTO groupBuilder(String groupBuilder) {
        this.groupBuilder = groupBuilder;
        return this;
    }

    public String getGroupBuilder() {
        return groupBuilder;
    }

    public void setGroupBuilder(String groupBuilder) {
        this.groupBuilder = groupBuilder;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public DashboardConfigDTO getDashboardConfig() {
        return dashboardConfig;
    }

    public void setDashboardConfig(DashboardConfigDTO dashboardConfig) {
        this.dashboardConfig = dashboardConfig;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DashboardGroupConfigDTO)) {
            return false;
        }

        DashboardGroupConfigDTO dashboardGroupConfigDTO = (DashboardGroupConfigDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, dashboardGroupConfigDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DashboardGroupConfigDTO{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", groupBuilder='" + getGroupBuilder() + "'" +
            ", expression='" + getExpression() + "'" +
            ", dashboardConfig=" + getDashboardConfig() +
            "}";
    }
}
