package org.akip.event;

import javax.persistence.Lob;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class JobExecutionTrackingEventDTO implements Serializable {
    private static final long serialVersionUID = -4949725918032027351L;

    private String cronName;

    private String identifier;

    private String status;

    @Lob
    private Map<String, String> descriptions = new HashMap<>();

    public JobExecutionTrackingEventDTO(String identifier, String status, Map<String, String> descriptions) {
        this.identifier = identifier;
        this.status = status;
        this.descriptions = descriptions;
    }

    public JobExecutionTrackingEventDTO(String identifier, String cronName) {
        this.identifier = identifier;
        this.cronName = cronName;
    }

    public JobExecutionTrackingEventDTO() {}

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Map<String, String> getDescriptions() {
        return descriptions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof JobExecutionTrackingEventDTO)) {
            return false;
        }

        JobExecutionTrackingEventDTO jobExecutionTrackingEventDTO = (JobExecutionTrackingEventDTO) o;
        if (this.identifier == null) {
            return false;
        }
        return Objects.equals(this.identifier, jobExecutionTrackingEventDTO.identifier);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.identifier);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "JobExecutionTrackingEventDTO{" +
            "cronName='" + getCronName() + "'" +
            ", identifier='" + getIdentifier() + "'" +
            ", status='" + getStatus() + "'" +
            ", descriptions='" + getDescriptions() + "'" +
            "}";
    }
}
