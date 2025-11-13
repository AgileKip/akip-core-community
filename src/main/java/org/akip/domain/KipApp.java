package org.akip.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.akip.domain.enumeration.KipAppStatus;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.Instant;

/**
 * A KipApp.
 */
@Entity
@Table(name = "kipapp")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class KipApp implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "identifier")
    private String identifier;

    @Lob
    @Column(name = "description")
    private String description;

    @Column(name = "base_url")
    private String baseUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private KipAppStatus status;

    @Column(name = "version")
    private String version;

    @Column(name = "build_date")
    private Instant buildDate;

    @Column(name = "deploy_date")
    private Instant deployDate;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public KipApp id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public KipApp name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdentifier() {
        return this.identifier;
    }

    public KipApp identifier(String identifier) {
        this.identifier = identifier;
        return this;
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
        return this.baseUrl;
    }

    public KipApp baseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        return this;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public KipAppStatus getStatus() {
        return this.status;
    }

    public KipApp status(KipAppStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(KipAppStatus status) {
        this.status = status;
    }

    public String getVersion() {
        return this.version;
    }

    public KipApp version(String version) {
        this.version = version;
        return this;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Instant getBuildDate() {
        return this.buildDate;
    }

    public KipApp buildDate(Instant buildDate) {
        this.buildDate = buildDate;
        return this;
    }

    public void setBuildDate(Instant buildDate) {
        this.buildDate = buildDate;
    }

    public Instant getDeployDate() {
        return this.deployDate;
    }

    public KipApp deployDate(Instant deployDate) {
        this.deployDate = deployDate;
        return this;
    }

    public void setDeployDate(Instant deployDate) {
        this.deployDate = deployDate;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof KipApp)) {
            return false;
        }
        return id != null && id.equals(((KipApp) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "KipApp{" +
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
