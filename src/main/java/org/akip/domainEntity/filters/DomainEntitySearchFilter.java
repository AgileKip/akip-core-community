package org.akip.domainEntity.filters;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.akip.service.dto.DomainEntityDefinitionDTO;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = DomainEntitySearchStringFilter.class, name = "string"),
        @JsonSubTypes.Type(value = DomainEntitySearchNumberFilter.class, name = "number"),
        @JsonSubTypes.Type(value = DomainEntitySearchDateFilter.class, name = "date"),
        @JsonSubTypes.Type(value = DomainEntitySearchHasARelationFilter.class, name = "hasARelation"),
})
public abstract class DomainEntitySearchFilter {

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public abstract String getType();
    public abstract boolean isActive();
    public abstract void buildCriteria(DomainEntityDefinitionDTO domainEntityDefinition, StringBuilder stringBuilder);
    public abstract void setParameters(MapSqlParameterSource params);


}
