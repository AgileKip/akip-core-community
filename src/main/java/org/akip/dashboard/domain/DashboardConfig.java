package org.akip.dashboard.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

import org.akip.domain.ProcessDefinition;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

/**
 * A DashboardConfig.
 */
@Entity
@Table(name = "dashboard_config")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DashboardConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "calendar_properties")
    private String calendarProperties;

    @ManyToOne
    private ProcessDefinition processDefinition;

    @OneToMany(mappedBy = "dashboardConfig", cascade = CascadeType.ALL, orphanRemoval = true)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "dashboardConfig" }, allowSetters = true)
    private List<DashboardGroupConfig> groups = new ArrayList<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DashboardConfig id(Long id) {
        this.id = id;
        return this;
    }

    public String getCalendarProperties() {
        return calendarProperties;
    }

    public void setCalendarProperties(String calendarProperties) {
        this.calendarProperties = calendarProperties;
    }

    public ProcessDefinition getProcessDefinition() {
        return this.processDefinition;
    }

    public DashboardConfig processDefinition(ProcessDefinition processDefinition) {
        this.processDefinition = processDefinition;
        return this;
    }

    public void setProcessDefinition(ProcessDefinition processDefinition) {
        this.processDefinition = processDefinition;
    }

    public List<DashboardGroupConfig> getGroups() {
        return this.groups;
    }

    public DashboardConfig groups(List<DashboardGroupConfig> groups) {
        this.setGroups(groups);
        return this;
    }

    public DashboardConfig addDashboardGroupConfig(DashboardGroupConfig DashboardGroupConfig) {
        this.groups.add(DashboardGroupConfig);
        DashboardGroupConfig.setDashboardConfig(this);
        return this;
    }

    public DashboardConfig removeDashboardGroupConfig(DashboardGroupConfig DashboardGroupConfig) {
        this.groups.remove(DashboardGroupConfig);
        DashboardGroupConfig.setDashboardConfig(null);
        return this;
    }

    public void setGroups(List<DashboardGroupConfig> groups) {
        if (this.groups != null) {
            this.groups.forEach(i -> i.setDashboardConfig(null));
        }
        if (groups != null) {
            groups.forEach(i -> i.setDashboardConfig(this));
        }
        this.groups = groups;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DashboardConfig)) {
            return false;
        }
        return id != null && id.equals(((DashboardConfig) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DashboardConfig{" +
            "id=" + getId() +
            "}";
    }
}
