package org.akip.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import org.akip.domain.enumeration.DomainEntityDefinitionStatus;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A DomainEntityDefinition.
 */
@Entity
@Table(name = "domain_entity_definition")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DomainEntityDefinition implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "version")
    private Integer version;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private DomainEntityDefinitionStatus status;

    @Column(name = "execution_date")
    private LocalDate executionDate;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "domainEntityDefinition", cascade = CascadeType.ALL, orphanRemoval = true)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "domainEntityDefinition" }, allowSetters = true)
    private Set<DomainEntityFieldDefinition> fields = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "domainEntityDefinition", cascade = CascadeType.ALL, orphanRemoval = true)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "domainEntityDefinition" }, allowSetters = true)
    private Set<DomainEntityHasARelationDefinition> hasARelations = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "domainEntityDefinition", cascade = CascadeType.ALL, orphanRemoval = true)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "domainEntityDefinition" }, allowSetters = true)
    private Set<DomainEntityHasManyRelationDefinition> hasManyRelations = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public DomainEntityDefinition id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public DomainEntityDefinition name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getVersion() {
        return this.version;
    }

    public DomainEntityDefinition version(Integer version) {
        this.setVersion(version);
        return this;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public DomainEntityDefinitionStatus getStatus() {
        return this.status;
    }

    public DomainEntityDefinition status(DomainEntityDefinitionStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(DomainEntityDefinitionStatus status) {
        this.status = status;
    }

    public LocalDate getExecutionDate() {
        return this.executionDate;
    }

    public DomainEntityDefinition executionDate(LocalDate executionDate) {
        this.setExecutionDate(executionDate);
        return this;
    }

    public void setExecutionDate(LocalDate executionDate) {
        this.executionDate = executionDate;
    }

    public Set<DomainEntityFieldDefinition> getFields() {
        return this.fields;
    }

    public void setFields(Set<DomainEntityFieldDefinition> fields) {
        if (this.fields != null) {
            this.fields.forEach(i -> i.setDomainEntityDefinition(null));
        }
        if (fields != null) {
            fields.forEach(i -> i.setDomainEntityDefinition(this));
        }
        this.fields = fields;
    }

    public DomainEntityDefinition fields(Set<DomainEntityFieldDefinition> fields) {
        this.setFields(fields);
        return this;
    }

    public DomainEntityDefinition addField(DomainEntityFieldDefinition field) {
        this.fields.add(field);
        field.setDomainEntityDefinition(this);
        return this;
    }

    public DomainEntityDefinition removeDomainEntityFieldDefinition(DomainEntityFieldDefinition field) {
        this.fields.remove(field);
        field.setDomainEntityDefinition(null);
        return this;
    }

    public Set<DomainEntityHasARelationDefinition> getHasARelations() {
        return this.hasARelations;
    }

    public void setHasARelations(Set<DomainEntityHasARelationDefinition> hasARelations) {
        if (this.hasARelations != null) {
            this.hasARelations.forEach(i -> i.setDomainEntityDefinition(null));
        }
        if (hasARelations != null) {
            hasARelations.forEach(i -> i.setDomainEntityDefinition(this));
        }
        this.hasARelations = hasARelations;
    }

    public DomainEntityDefinition hasARelations(
        Set<DomainEntityHasARelationDefinition> hasARelations
    ) {
        this.setHasARelations(hasARelations);
        return this;
    }

    public DomainEntityDefinition addHasARelation(
        DomainEntityHasARelationDefinition hasARelation
    ) {
        this.hasARelations.add(hasARelation);
        hasARelation.setDomainEntityDefinition(this);
        return this;
    }

    public DomainEntityDefinition removeHasARelation(
        DomainEntityHasARelationDefinition hasARelation
    ) {
        this.hasARelations.remove(hasARelation);
        hasARelation.setDomainEntityDefinition(null);
        return this;
    }

    public Set<DomainEntityHasManyRelationDefinition> getHasManyRelations() {
        return this.hasManyRelations;
    }

    public void setHasManyRelations(
        Set<DomainEntityHasManyRelationDefinition> hasManyRelations
    ) {
        if (this.hasManyRelations != null) {
            this.hasManyRelations.forEach(i -> i.setDomainEntityDefinition(null));
        }
        if (hasManyRelations != null) {
            hasManyRelations.forEach(i -> i.setDomainEntityDefinition(this));
        }
        this.hasManyRelations = hasManyRelations;
    }

    public DomainEntityDefinition domainEntityHasManyRelationDefinitions(
        Set<DomainEntityHasManyRelationDefinition> hasManyRelations
    ) {
        this.setHasManyRelations(hasManyRelations);
        return this;
    }

    public DomainEntityDefinition addHasManyRelation(
        DomainEntityHasManyRelationDefinition hasManyRelation
    ) {
        this.hasManyRelations.add(hasManyRelation);
        hasManyRelation.setDomainEntityDefinition(this);
        return this;
    }

    public DomainEntityDefinition removeHasManyRelation(
        DomainEntityHasManyRelationDefinition hasManyRelation
    ) {
        this.hasManyRelations.remove(hasManyRelation);
        hasManyRelation.setDomainEntityDefinition(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DomainEntityDefinition)) {
            return false;
        }
        return getId() != null && getId().equals(((DomainEntityDefinition) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DomainEntityDefinition{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", version=" + getVersion() +
            ", status='" + getStatus() + "'" +
            ", executionDate='" + getExecutionDate() + "'" +
            "}";
    }
}
