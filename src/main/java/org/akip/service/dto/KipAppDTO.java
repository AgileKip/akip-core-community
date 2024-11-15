package org.akip.service.dto;


import org.akip.domain.enumeration.KipAppStatus;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;
import java.util.Objects;

/**
 * A DTO for the {@link org.akip.domain.KipApp} entity.
 */
public class KipAppDTO implements Serializable {

    private Long id;

    private String name;

    private String identifier;

    private String description;

    private String baseUrl;

    private KipAppStatus status;

    private String version;

    private Instant buildDate;

    private Instant deployDate;

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

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public KipAppStatus getStatus() {
        return status;
    }

    public void setStatus(KipAppStatus status) {
        this.status = status;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Instant getBuildDate() {
        return buildDate;
    }

    public void setBuildDate(Instant buildDate) {
        this.buildDate = buildDate;
    }

    public Instant getDeployDate() {
        return deployDate;
    }

    public void setDeployDate(Instant deployDate) {
        this.deployDate = deployDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof KipAppDTO)) {
            return false;
        }

        KipAppDTO kipAppDTO = (KipAppDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, kipAppDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "KipAppDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", identifier='" + getIdentifier() + "'" +
            ", baseUrl='" + getBaseUrl() + "'" +
            ", status='" + getStatus() + "'" +
            ", version='" + getVersion() + "'" +
            ", buildDate='" + getBuildDate() + "'" +
            ", deployDate='" + getDeployDate() + "'" +
            "}";
    }
}
