package org.akip.domainEntity.filters;

import org.akip.service.dto.DomainEntityDefinitionDTO;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import java.util.List;
import java.util.Map;

public class DomainEntitySearchHasARelationFilter extends DomainEntitySearchFilter {

    private Integer value;

    private String otherEntityDisplayFieldName;

    private List<Map<String, Object>> values;

    @Override
    public String getType() {
        return "hasARelation";
    }

    @Override
    public boolean isActive() {
        return value != null;
    }

    @Override
    public void buildCriteria(DomainEntityDefinitionDTO domainEntityDefinition, StringBuilder stringBuilder) {
        stringBuilder.append(" AND ")
                .append(domainEntityDefinition.getName())
                .append(".")
                .append(getId())
                .append("_id = :")
                .append(getId());
    }

    @Override
    public void setParameters(MapSqlParameterSource params) {
        params.addValue(getId(), value);
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getOtherEntityDisplayFieldName() {
        return otherEntityDisplayFieldName;
    }

    public void setOtherEntityDisplayFieldName(String otherEntityDisplayFieldName) {
        this.otherEntityDisplayFieldName = otherEntityDisplayFieldName;
    }

    public List<Map<String, Object>> getValues() {
        return values;
    }

    public void setValues(List<Map<String, Object>> values) {
        this.values = values;
    }

    public DomainEntitySearchHasARelationFilter id(String id) {
        setId(id);
        return this;
    }

    public DomainEntitySearchHasARelationFilter value(Integer value) {
        this.value = value;
        return this;
    }

    public DomainEntitySearchHasARelationFilter otherEntityDisplayFieldName(String otherEntityDisplayFieldName) {
        this.otherEntityDisplayFieldName = otherEntityDisplayFieldName;
        return this;
    }

    public DomainEntitySearchHasARelationFilter values(List<Map<String, Object>> values) {
        this.values = values;
        return this;
    }

}
