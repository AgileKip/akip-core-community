package org.akip.domainEntity.filters;

import org.akip.service.dto.DomainEntityDefinitionDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

public class DomainEntitySearchStringFilter extends DomainEntitySearchFilter {

    private String value;

    @Override
    public String getType() {
        return "string";
    }

    @Override
    public boolean isActive() {
        return StringUtils.isNotBlank(value);
    }

    @Override
    public void buildCriteria(DomainEntityDefinitionDTO domainEntityDefinition, StringBuilder stringBuilder) {
        stringBuilder.append(" AND ")
                .append(domainEntityDefinition.getName())
                .append(".")
                .append(getId())
                .append(" like :")
                .append(getId());
    }

    @Override
    public void setParameters(MapSqlParameterSource params) {
        params.addValue(getId(), value + "%");
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public DomainEntitySearchStringFilter id(String id) {
        setId(id);
        return this;
    }

    public DomainEntitySearchStringFilter value(String value) {
        this.value = value;
        return this;
    }

}
