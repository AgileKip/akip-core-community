package org.akip.dashboard.domain;

import java.io.Serializable;
import jakarta.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A DashboardGroupConfig.
 */
@Entity
@Table(name = "dashboard_group_config")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DashboardGroupConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "group_builder")
    private String groupBuilder;

    @Lob
    @Column(name = "expression")
    private String expression;

    @ManyToOne
    private DashboardConfig dashboardConfig;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DashboardGroupConfig id(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return this.title;
    }

    public DashboardGroupConfig title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGroupBuilder() {
        return this.groupBuilder;
    }

    public DashboardGroupConfig groupBuilder(String groupBuilder) {
        this.groupBuilder = groupBuilder;
        return this;
    }

    public void setGroupBuilder(String groupBuilder) {
        this.groupBuilder = groupBuilder;
    }

    public String getExpression() {
        return this.expression;
    }

    public DashboardGroupConfig expression(String expression) {
        this.expression = expression;
        return this;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public DashboardConfig getDashboardConfig() {
        return this.dashboardConfig;
    }

    public DashboardGroupConfig dashboardConfig(DashboardConfig dashboardConfig) {
        this.setDashboardConfig(dashboardConfig);
        return this;
    }

    public void setDashboardConfig(DashboardConfig dashboardConfig) {
        this.dashboardConfig = dashboardConfig;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DashboardGroupConfig)) {
            return false;
        }
        return id != null && id.equals(((DashboardGroupConfig) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DashboardGroupConfig{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", groupBuilder='" + getGroupBuilder() + "'" +
            ", expression='" + getExpression() + "'" +
            "}";
    }
}
