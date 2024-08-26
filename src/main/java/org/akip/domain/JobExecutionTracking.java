package org.akip.domain;

import org.akip.event.JobExecutionTrackingConstants;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * A JobExecutionTracking.
 */
@Entity
@Table(name = "job_execution_tracking")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class JobExecutionTracking implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "cron_name")
    private String cronName;

    @Column(name = "identifier")
    private String identifier;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Column(name = "status")
    private String status;

    @Lob
    @Column(name = "summary")
    private String summary;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public JobExecutionTracking id(Long id) {
        this.id = id;
        return this;
    }

    public String getCronName() {
        return this.cronName;
    }

    public JobExecutionTracking cronName(String cronName) {
        this.cronName = cronName;
        return this;
    }

    public void setCronName(String cronName) {
        this.cronName = cronName;
    }

    public String getIdentifier() {
        return this.identifier;
    }

    public JobExecutionTracking identifier(String identifier) {
        this.identifier = identifier;
        return this;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public LocalDateTime getStartDate() {
        return this.startDate;
    }

    public JobExecutionTracking startDate(LocalDateTime startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return this.endDate;
    }

    public JobExecutionTracking endDate(LocalDateTime endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return this.status;
    }

    public JobExecutionTracking status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSummary() {
        return this.summary;
    }

    public JobExecutionTracking summary(String summary) {
        this.summary = summary;
        return this;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @PrePersist
    @PreUpdate
    public void prePersist() {
        if (this.startDate == null) {
            this.setStartDate(LocalDateTime.now());
        }
        if (
            this.getStatus().equals(JobExecutionTrackingConstants.STATUS_COMPLETE_SUCCESS) ||
            this.getStatus().equals(JobExecutionTrackingConstants.STATUS_COMPLETE_ERROR)
        ) {
            this.setEndDate(LocalDateTime.now());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof JobExecutionTracking)) {
            return false;
        }
        return id != null && id.equals(((JobExecutionTracking) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "JobExecutionTracking{" +
            "id=" + getId() +
            ", cronName='" + getCronName() + "'" +
            ", identifier='" + getIdentifier() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
