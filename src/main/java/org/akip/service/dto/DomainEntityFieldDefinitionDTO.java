package org.akip.service.dto;

import jakarta.validation.constraints.NotNull;
import org.akip.domain.enumeration.DomainEntityFieldType;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link org.akip.domain.DomainEntityFieldDefinition} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DomainEntityFieldDefinitionDTO implements Serializable {

    private Long id;

    private String name;

    private DomainEntityFieldType type;

    @NotNull
    private DomainEntityDefinitionDTO domainEntityDefinition;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DomainEntityFieldDefinitionDTO id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DomainEntityFieldDefinitionDTO name(String name) {
        this.name = name;
        return this;
    }

    public DomainEntityFieldType getType() {
        return type;
    }

    public void setType(DomainEntityFieldType type) {
        this.type = type;
    }

    public DomainEntityFieldDefinitionDTO type(DomainEntityFieldType type) {
        this.type = type;
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
        if (!(o instanceof DomainEntityFieldDefinitionDTO)) {
            return false;
        }

        DomainEntityFieldDefinitionDTO domainEntityFieldDefinitionDTO = (DomainEntityFieldDefinitionDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, domainEntityFieldDefinitionDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DomainEntityFieldDefinitionDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", type='" + getType() + "'" +
            ", domainEntityDefinition=" + getDomainEntityDefinition() +
            "}";
    }


    public String getUserFriendlyName() {
        if (name == null) {
            return "";
        }
        return StringUtils.join(
                StringUtils.splitByCharacterTypeCamelCase(StringUtils.capitalize(name)),
                ' '
        );
    }
}
