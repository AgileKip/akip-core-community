package org.akip.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.io.Serializable;

/**
 * A DomainEntityHasManyRelationDefinition.
 */
@Entity
@Table(name = "domain_entity_has_many_relation_definition")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DomainEntityHasManyRelationDefinition implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "collection_name")
    private String collectionName;

    @Column(name = "other_entity_name")
    private String otherEntityName;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(
        value = { "domainEntityFieldDefinitions", "domainEntityHasARelationDefinitions", "domainEntityHasManyRelationDefinitions" },
        allowSetters = true
    )
    private DomainEntityDefinition domainEntityDefinition;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public DomainEntityHasManyRelationDefinition id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCollectionName() {
        return this.collectionName;
    }

    public DomainEntityHasManyRelationDefinition collectionName(String collectionName) {
        this.setCollectionName(collectionName);
        return this;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    public String getOtherEntityName() {
        return this.otherEntityName;
    }

    public DomainEntityHasManyRelationDefinition otherEntityName(String otherEntityName) {
        this.setOtherEntityName(otherEntityName);
        return this;
    }

    public void setOtherEntityName(String otherEntityName) {
        this.otherEntityName = otherEntityName;
    }

    public DomainEntityDefinition getDomainEntityDefinition() {
        return this.domainEntityDefinition;
    }

    public void setDomainEntityDefinition(DomainEntityDefinition domainEntityDefinition) {
        this.domainEntityDefinition = domainEntityDefinition;
    }

    public DomainEntityHasManyRelationDefinition domainEntityDefinition(DomainEntityDefinition domainEntityDefinition) {
        this.setDomainEntityDefinition(domainEntityDefinition);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DomainEntityHasManyRelationDefinition)) {
            return false;
        }
        return getId() != null && getId().equals(((DomainEntityHasManyRelationDefinition) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DomainEntityHasManyRelationDefinition{" +
            "id=" + getId() +
            ", collectionName='" + getCollectionName() + "'" +
            ", otherEntityName='" + getOtherEntityName() + "'" +
            "}";
    }
}
