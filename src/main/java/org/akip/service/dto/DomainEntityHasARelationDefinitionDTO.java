package org.akip.service.dto;

import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link org.akip.domain.DomainEntityHasARelationDefinition} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DomainEntityHasARelationDefinitionDTO implements Serializable {

    private Long id;

    private String name;

    private String otherEntityName;

    private String otherEntityDisplayFieldName;

    @NotNull
    private DomainEntityDefinitionDTO domainEntityDefinition;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DomainEntityHasARelationDefinitionDTO id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DomainEntityHasARelationDefinitionDTO name(String name) {
        this.name = name;
        return this;
    }

    public String getOtherEntityName() {
        return otherEntityName;
    }

    public void setOtherEntityName(String otherEntityName) {
        this.otherEntityName = otherEntityName;
    }

    public DomainEntityHasARelationDefinitionDTO otherEntityName(String otherEntityName) {
        this.otherEntityName = otherEntityName;
        return this;
    }

    public String getOtherEntityDisplayFieldName() {
        return otherEntityDisplayFieldName;
    }

    public void setOtherEntityDisplayFieldName(String otherEntityDisplayFieldName) {
        this.otherEntityDisplayFieldName = otherEntityDisplayFieldName;
    }

    public DomainEntityHasARelationDefinitionDTO otherEntityDisplayFieldName(String otherEntityDisplayFieldName) {
        this.otherEntityDisplayFieldName = otherEntityDisplayFieldName;
        return this;
    }

    public DomainEntityDefinitionDTO getDomainEntityDefinition() {
        return domainEntityDefinition;
    }

    public void setDomainEntityDefinition(DomainEntityDefinitionDTO domainEntityDefinition) {
        this.domainEntityDefinition = domainEntityDefinition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DomainEntityHasARelationDefinitionDTO)) {
            return false;
        }

        DomainEntityHasARelationDefinitionDTO domainEntityHasARelationDefinitionDTO = (DomainEntityHasARelationDefinitionDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, domainEntityHasARelationDefinitionDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DomainEntityHasARelationDefinitionDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", otherEntityName='" + getOtherEntityName() + "'" +
            ", otherEntityDisplayFieldName='" + getOtherEntityDisplayFieldName() + "'" +
            ", domainEntityDefinition=" + getDomainEntityDefinition() +
            "}";
    }

    public String getForeignKeyColumn() {
        return name + "_id";
    }

    public String getDisplayColumn() {
        return name + "_" + otherEntityDisplayFieldName;
    }
}
