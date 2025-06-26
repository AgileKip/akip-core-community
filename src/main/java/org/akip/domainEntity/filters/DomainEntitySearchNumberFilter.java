package org.akip.domainEntity.filters;

import org.akip.service.dto.DomainEntityDefinitionDTO;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

public class DomainEntitySearchNumberFilter extends DomainEntitySearchFilter {

    private Integer value;

    @Override
    public String getType() {
        return "number";
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
                .append(" = :")
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

    public DomainEntitySearchNumberFilter id(String id) {
        setId(id);
        return this;
    }

    public DomainEntitySearchNumberFilter value(Integer value) {
        this.value = value;
        return this;
    }

}
