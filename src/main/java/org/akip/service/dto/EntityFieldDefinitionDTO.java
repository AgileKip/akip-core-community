package org.akip.service.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class EntityFieldDefinitionDTO implements Serializable {

    private Long id;

    private String name;

    private String type;

    private String label;

    private Boolean enabledFilter;

    private Boolean enabledResultColumn;

    private List<ValidationDTO> validations;

    private DomainEntityDefinitionDTO definition;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Boolean getEnabledFilter() {
        return enabledFilter;
    }

    public void setEnabledFilter(Boolean enabledFilter) {
        this.enabledFilter = enabledFilter;
    }

    public Boolean getEnabledResultColumn() {
        return enabledResultColumn;
    }

    public void setEnabledResultColumn(Boolean enabledResultColumn) {
        this.enabledResultColumn = enabledResultColumn;
    }

    public List<ValidationDTO> getValidations() {
        return validations;
    }

    public void setValidations(List<ValidationDTO> validations) {
        this.validations = validations;
    }

    public DomainEntityDefinitionDTO getDefinition() {
        return definition;
    }

    public void setDefinition(DomainEntityDefinitionDTO definition) {
        this.definition = definition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EntityFieldDefinitionDTO)) {
            return false;
        }

        EntityFieldDefinitionDTO entityFieldDefinitionDTO = (EntityFieldDefinitionDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, entityFieldDefinitionDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore

    @Override
    public String toString() {
        return "EntityFieldDefinitionDTO{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", type='" + type + '\'' +
            ", definition=" + definition +
            '}';
    }
}
