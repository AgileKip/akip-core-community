package org.akip.service.dto;

import org.akip.domain.enumeration.DomainEntityDefinitionStatus;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * A DTO for the {@link org.akip.domain.DomainEntityDefinition} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class DomainEntityDefinitionDTO implements Serializable {

    private static final String KIPAPP_DOMAIN_ENTITY_TABLE_PREFIX = "AKIP_DOMAIN_ENTITY_";
    private Long id;

    private String name;

    private Integer version;

    private DomainEntityDefinitionStatus status;

    private LocalDate executionDate;

    private List<DomainEntityFieldDefinitionDTO> fields = new ArrayList<>();

    private List<DomainEntityHasARelationDefinitionDTO> hasARelations = new ArrayList<>();

    private List<DomainEntityHasManyRelationDefinitionDTO> hasManyRelations = new ArrayList<>();

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

    public DomainEntityDefinitionDTO name(String name) {
        this.name = name;
        return this;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public DomainEntityDefinitionStatus getStatus() {
        return status;
    }

    public void setStatus(DomainEntityDefinitionStatus status) {
        this.status = status;
    }

    public DomainEntityDefinitionDTO status(DomainEntityDefinitionStatus status) {
        this.status = status;
        return this;
    }

    public LocalDate getExecutionDate() {
        return executionDate;
    }

    public void setExecutionDate(LocalDate executionDate) {
        this.executionDate = executionDate;
    }

    public List<DomainEntityFieldDefinitionDTO> getFields() {
        return fields;
    }

    public void setFields(List<DomainEntityFieldDefinitionDTO> fields) {
        this.fields = fields;
    }

    public DomainEntityDefinitionDTO addField(DomainEntityFieldDefinitionDTO field) {
        this.fields.add(field);
        return this;
    }

    public List<DomainEntityHasARelationDefinitionDTO> getHasARelations() {
        return hasARelations;
    }

    public void setHasARelations(List<DomainEntityHasARelationDefinitionDTO> hasARelations) {
        this.hasARelations = hasARelations;
    }

    public DomainEntityDefinitionDTO addHasARelation(DomainEntityHasARelationDefinitionDTO hasARelation) {
        this.hasARelations.add(hasARelation);
        return this;
    }

    public List<DomainEntityHasManyRelationDefinitionDTO> getHasManyRelations() {
        return hasManyRelations;
    }

    public void setHasManyRelations(List<DomainEntityHasManyRelationDefinitionDTO> hasManyRelations) {
        this.hasManyRelations = hasManyRelations;
    }

    public DomainEntityDefinitionDTO addHasManyRelation(DomainEntityHasManyRelationDefinitionDTO hasManyRelation) {
        this.hasManyRelations.add(hasManyRelation);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DomainEntityDefinitionDTO)) {
            return false;
        }

        DomainEntityDefinitionDTO domainEntityDefinitionDTO = (DomainEntityDefinitionDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, domainEntityDefinitionDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "DomainEntityDefinitionDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", version=" + getVersion() +
            ", status='" + getStatus() + "'" +
            ", executionDate='" + getExecutionDate() + "'" +
            "}";
    }

    public String getTableName() {
        return KIPAPP_DOMAIN_ENTITY_TABLE_PREFIX + name.toUpperCase();
    }

    public List<String> getColumns() {
        List<String> columns = new ArrayList<>();
        columns.add("id");
        columns.addAll(fields.stream().map(DomainEntityFieldDefinitionDTO::getName).toList());
        columns.addAll(hasARelations.stream().map(DomainEntityHasARelationDefinitionDTO::getForeignKeyColumn).toList());
        columns.add("version");
        return columns;
    }

    public String getForeignKeyColumn() {
        return name.toLowerCase() + "_id";
    }

    public List<String> getHasManyRelationNames() {
        return hasManyRelations.stream().map(DomainEntityHasManyRelationDefinitionDTO::getCollectionName).collect(Collectors.toList());
    }

    public DomainEntityHasManyRelationDefinitionDTO getHasManyRelationDefinitionByName(String hasManyRelationName) {
        return hasManyRelations.stream().filter(hasManyRelation -> hasManyRelation.getCollectionName().equals(hasManyRelationName)).findFirst().orElseThrow();
    }

}
