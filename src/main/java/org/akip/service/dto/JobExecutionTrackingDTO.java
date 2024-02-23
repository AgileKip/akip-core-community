package org.akip.service.dto;

import org.akip.event.JobExecutionTrackingConstants;

import javax.persistence.Lob;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * A DTO for the {@link org.akip.domain.JobExecutionTracking} entity.
 */
public class JobExecutionTrackingDTO implements Serializable {

    private Long id;

    private String cronName;

    private String identifier;

    private LocalDate startDate;

    private LocalDate endDate;

    private String status = JobExecutionTrackingConstants.STATUS_RUNNING;

    @Lob
    private Map<String, String> descriptions = new HashMap<>();

    public JobExecutionTrackingDTO(String identifier, String cronName) {
        this.identifier = identifier;
        this.cronName = cronName;
    }

    public JobExecutionTrackingDTO() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCronName() {
        return cronName;
    }

    public void setCronName(String cronName) {
        this.cronName = cronName;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Map<String, String> getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(Map<String, String> descriptions) {
        this.descriptions = descriptions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof JobExecutionTrackingDTO)) {
            return false;
        }

        JobExecutionTrackingDTO jobExecutionTrackingDTO = (JobExecutionTrackingDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, jobExecutionTrackingDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "JobExecutionTrackingDTO{" +
            "id=" + getId() +
            ", cronName='" + getCronName() + "'" +
            ", identifier='" + getIdentifier() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", status='" + getStatus() + "'" +
            ", description='" + getDescriptions() + "'" +
            "}";
    }
}
