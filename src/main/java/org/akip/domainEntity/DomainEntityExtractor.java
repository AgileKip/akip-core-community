package org.akip.domainEntity;

import org.akip.service.dto.DomainEntityDefinitionDTO;
import org.akip.service.dto.DomainEntityFieldDefinitionDTO;
import org.akip.service.dto.DomainEntityHasARelationDefinitionDTO;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class DomainEntityExtractor implements ResultSetExtractor<Map<String, Object>> {

    private static final int MAX_LENGTH_CLOB_WHEN_FIND_BY_ID = 0; //Will try to get the entire CLOB

    private DomainEntityDefinitionDTO domainEntityDefinition;

    public DomainEntityExtractor(DomainEntityDefinitionDTO domainEntityDefinition) {
        this.domainEntityDefinition = domainEntityDefinition;
    }

    @Override
    public Map<String, Object> extractData(ResultSet rs) throws SQLException {
        Map<String, Object> entry = new HashMap<>();
        while (rs.next()) {
            entry.put("id", rs.getLong("id"));
            for (DomainEntityFieldDefinitionDTO field: domainEntityDefinition.getFields()) {
                Object value = rs.getObject(field.getName());
                value = ClobConverter.convertClobToString(value, MAX_LENGTH_CLOB_WHEN_FIND_BY_ID);
                entry.put(field.getName(), value);
            }
            for (DomainEntityHasARelationDefinitionDTO hasARelationDefinition: domainEntityDefinition.getHasARelations()) {
                entry.put(hasARelationDefinition.getForeignKeyColumn(), rs.getObject(hasARelationDefinition.getForeignKeyColumn()));
                entry.put(hasARelationDefinition.getDisplayColumn(), rs.getObject(hasARelationDefinition.getDisplayColumn()));
            }
            entry.put("version", rs.getInt("version"));
        }
        return entry;
    }
}
