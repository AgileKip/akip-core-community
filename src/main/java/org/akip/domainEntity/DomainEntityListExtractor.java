package org.akip.domainEntity;

import org.akip.service.dto.DomainEntityDefinitionDTO;
import org.akip.service.dto.DomainEntityFieldDefinitionDTO;
import org.akip.service.dto.DomainEntityHasARelationDefinitionDTO;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DomainEntityListExtractor implements ResultSetExtractor<List<Map<String, Object>>> {

    private static final int MAX_LENGTH_CLOB_WHEN_FIND_LIST = 0; //TODO: change the value to something like 25 use only when used in the findAll feature

    private DomainEntityDefinitionDTO domainEntityDefinition;

    public DomainEntityListExtractor(DomainEntityDefinitionDTO domainEntityDefinition) {
        this.domainEntityDefinition = domainEntityDefinition;
    }

    @Override
    public List<Map<String, Object>> extractData(ResultSet rs) throws SQLException {
        List<Map<String, Object>> list = new ArrayList<>();
        while (rs.next()) {
            Map<String, Object> entry = new HashMap<>();
            list.add(entry);
            entry.put("id", rs.getLong("id"));
            for (DomainEntityFieldDefinitionDTO field: domainEntityDefinition.getFields()) {
                Object value = rs.getObject(field.getName());
                value = ClobConverter.convertClobToString(value, MAX_LENGTH_CLOB_WHEN_FIND_LIST);
                entry.put(field.getName(), value);
            }
            for (DomainEntityHasARelationDefinitionDTO hasARelationDefinition: domainEntityDefinition.getHasARelations()) {
                entry.put(hasARelationDefinition.getForeignKeyColumn(), rs.getObject(hasARelationDefinition.getForeignKeyColumn()));
                entry.put(hasARelationDefinition.getDisplayColumn(), rs.getObject(hasARelationDefinition.getDisplayColumn()));
            }
            entry.put("version", rs.getInt("version"));
        }
        return list;
    }
}
