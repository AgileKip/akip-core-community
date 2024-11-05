package org.akip.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;

/**
 * A EntityFieldDefinition.
 */
@Entity
@Table(name = "entity_field_definition")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class EntityFieldDefinition implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator", sequenceName = "akip_hibernate_sequence")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    @Column(name = "label")
    private String label;

    @Column(name = "enabled_filter")
    private Boolean enabledFilter;

    @Column(name = "enabled_result_column")
    private Boolean enabledResultColumn;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "validations")
    private String validations;

    @ManyToOne
    private DomainEntityDefinition definition;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EntityFieldDefinition id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public EntityFieldDefinition name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return this.type;
    }

    public EntityFieldDefinition type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLabel() {
        return label;
    }

    public EntityFieldDefinition label(String label) {
        this.label = label;
        return this;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Boolean getEnabledFilter() {
        return enabledFilter;
    }

    public EntityFieldDefinition enabledFilter(Boolean enabledFilter) {
        this.enabledFilter = enabledFilter;
        return this;
    }

    public void setEnabledFilter(Boolean enabledFilter) {
        this.enabledFilter = enabledFilter;
    }

    public Boolean getEnabledResultColumn() {
        return enabledResultColumn;
    }

    public EntityFieldDefinition enabledResultColumn(Boolean enabledResultColumn) {
        this.enabledResultColumn = enabledResultColumn;
        return this;
    }

    public void setEnabledResultColumn(Boolean enabledResultColumn) {
        this.enabledResultColumn = enabledResultColumn;
    }

    public String getValidations() {
        return validations;
    }

    public EntityFieldDefinition validations(String validations) {
        this.setValidations(validations);
        return this;
    }

    public void setValidations(String validations) {
        this.validations = validations;
    }

    public DomainEntityDefinition getDefinition() {
        return this.definition;
    }

    public EntityFieldDefinition definition(DomainEntityDefinition definition) {
        this.setDefinition(definition);
        return this;
    }

    public void setDefinition(DomainEntityDefinition definition) {
        this.definition = definition;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EntityFieldDefinition)) {
            return false;
        }
        return id != null && id.equals(((EntityFieldDefinition) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore

    @Override
    public String toString() {
        return "EntityFieldDefinition{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", type='" + type + '\'' +
            ", label='" + label + '\'' +
            ", definition=" + definition +
            '}';
    }
}
