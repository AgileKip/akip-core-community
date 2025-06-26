package org.akip.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.akip.domain.enumeration.DomainEntityFieldType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.io.Serializable;

/**
 * A DomainEntityFieldDefinition.
 */
@Entity
@Table(name = "domain_entity_field_definition")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DomainEntityFieldDefinition implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private DomainEntityFieldType type;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "domainEntityFieldDefinitions", "domainEntityHasARelationDefinitions" }, allowSetters = true)
    private DomainEntityDefinition domainEntityDefinition;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public DomainEntityFieldDefinition id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public DomainEntityFieldDefinition name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DomainEntityFieldType getType() {
        return this.type;
    }

    public DomainEntityFieldDefinition type(DomainEntityFieldType type) {
        this.setType(type);
        return this;
    }

    public void setType(DomainEntityFieldType type) {
        this.type = type;
    }

    public DomainEntityDefinition getDomainEntityDefinition() {
        return this.domainEntityDefinition;
    }

    public void setDomainEntityDefinition(DomainEntityDefinition domainEntityDefinition) {
        this.domainEntityDefinition = domainEntityDefinition;
    }

    public DomainEntityFieldDefinition domainEntityDefinition(DomainEntityDefinition domainEntityDefinition) {
        this.setDomainEntityDefinition(domainEntityDefinition);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DomainEntityFieldDefinition)) {
            return false;
        }
        return getId() != null && getId().equals(((DomainEntityFieldDefinition) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DomainEntityFieldDefinition{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
