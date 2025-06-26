package org.akip.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.io.Serializable;

/**
 * A DomainEntityHasARelationDefinition.
 */
@Entity
@Table(name = "domain_entity_hasarelation_definition")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DomainEntityHasARelationDefinition implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "other_entity_name")
    private String otherEntityName;

    @Column(name = "other_entity_display_field_name")
    private String otherEntityDisplayFieldName;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "domainEntityFieldDefinitions", "domainEntityHasARelationDefinitions" }, allowSetters = true)
    private DomainEntityDefinition domainEntityDefinition;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public DomainEntityHasARelationDefinition id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public DomainEntityHasARelationDefinition name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOtherEntityName() {
        return this.otherEntityName;
    }

    public DomainEntityHasARelationDefinition otherEntityName(String otherEntityName) {
        this.setOtherEntityName(otherEntityName);
        return this;
    }

    public void setOtherEntityName(String otherEntityName) {
        this.otherEntityName = otherEntityName;
    }

    public String getOtherEntityDisplayFieldName() {
        return this.otherEntityDisplayFieldName;
    }

    public DomainEntityHasARelationDefinition otherEntityDisplayFieldName(String otherEntityDisplayFieldName) {
        this.setOtherEntityDisplayFieldName(otherEntityDisplayFieldName);
        return this;
    }

    public void setOtherEntityDisplayFieldName(String otherEntityDisplayFieldName) {
        this.otherEntityDisplayFieldName = otherEntityDisplayFieldName;
    }

    public DomainEntityDefinition getDomainEntityDefinition() {
        return this.domainEntityDefinition;
    }

    public void setDomainEntityDefinition(DomainEntityDefinition domainEntityDefinition) {
        this.domainEntityDefinition = domainEntityDefinition;
    }

    public DomainEntityHasARelationDefinition domainEntityDefinition(DomainEntityDefinition domainEntityDefinition) {
        this.setDomainEntityDefinition(domainEntityDefinition);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DomainEntityHasARelationDefinition)) {
            return false;
        }
        return getId() != null && getId().equals(((DomainEntityHasARelationDefinition) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DomainEntityHasARelationDefinition{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", otherEntityName='" + getOtherEntityName() + "'" +
            ", otherEntityDisplayFieldName='" + getOtherEntityDisplayFieldName() + "'" +
            "}";
    }
}
