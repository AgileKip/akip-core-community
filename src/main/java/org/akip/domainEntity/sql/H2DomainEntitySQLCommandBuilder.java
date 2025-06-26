package org.akip.domainEntity.sql;

import org.akip.service.DomainEntityDefinitionService;
import org.akip.service.dto.DomainEntityDefinitionDTO;
import org.akip.service.dto.DomainEntityHasARelationDefinitionDTO;
import org.springframework.stereotype.Service;

@Service
public class H2DomainEntitySQLCommandBuilder implements DomainEntitySQLCommandBuilder {

    private final DomainEntityDefinitionService domainEntityDefinitionService;

    public H2DomainEntitySQLCommandBuilder(DomainEntityDefinitionService domainEntityDefinitionService) {
        this.domainEntityDefinitionService = domainEntityDefinitionService;
    }

    public String buildFindByColumnSQLCommand(DomainEntityDefinitionDTO domainEntityDefinition, String columnName, Object value) {
        return new StringBuilder()
                .append(buildSelectClause(domainEntityDefinition))
                .append(buildFromClause(domainEntityDefinition))
                .append(buildJoinClauses(domainEntityDefinition))
                .append(buildWhereClause())
                .append(" AND ")
                .append(domainEntityDefinition.getName())
                .append(".")
                .append(columnName)
                .append(" = ")
                .append(value)
                .toString();
    }

    public String buildFindByIdSQLCommand(DomainEntityDefinitionDTO domainEntityDefinition, Object id) {
        return buildFindByColumnSQLCommand(domainEntityDefinition, "id", id);
    }

    public String buildFindAllSQLCommand(DomainEntityDefinitionDTO domainEntityDefinition) {
        return new StringBuilder()
                .append(buildSelectClause(domainEntityDefinition))
                .append(buildFromClause(domainEntityDefinition))
                .append(buildJoinClauses(domainEntityDefinition))
                .append(buildWhereClause())
                .toString();
    }

    public String buildDeleteByIdSQLCommand(DomainEntityDefinitionDTO domainEntityDefinition, Object id) {
        return new StringBuilder()
                .append("DELETE FROM ")
                .append(domainEntityDefinition.getTableName())
                .append(" WHERE ID = ")
                .append(id)
                .toString();
    }

    public String buildSelectClause(DomainEntityDefinitionDTO domainEntityDefinition) {
        StringBuilder sb = new StringBuilder()
                .append(" SELECT ");
        for (String domainEntityColumn: domainEntityDefinition.getColumns()) {
            sb.append(domainEntityDefinition.getName())
                    .append(".")
                    .append(domainEntityColumn)
                    .append(" AS ")
                    .append(domainEntityDefinition.getName())
                    .append("_")
                    .append(domainEntityColumn)
                    .append(", ");
        }
        for (DomainEntityHasARelationDefinitionDTO hasARelationDefinition: domainEntityDefinition.getHasARelations()) {
            sb.append(hasARelationDefinition.getName())
                    .append(".")
                    .append(hasARelationDefinition.getOtherEntityDisplayFieldName())
                    .append(" AS ")
                    .append(hasARelationDefinition.getName())
                    .append("_")
                    .append(hasARelationDefinition.getOtherEntityDisplayFieldName())
                    .append(", ");
        }
        sb.append(" 1 AS CONTROL ");
        return sb.toString();
    }

    public String buildFromClause(DomainEntityDefinitionDTO domainEntityDefinition) {
        return new StringBuilder()
                .append(" FROM ")
                .append(domainEntityDefinition.getTableName())
                .append(" AS ")
                .append(domainEntityDefinition.getName())
                .toString();
    }

    public String buildJoinClauses(DomainEntityDefinitionDTO domainEntityDefinition) {
        StringBuilder sb = new StringBuilder();
        for (DomainEntityHasARelationDefinitionDTO hasARelationDefinition: domainEntityDefinition.getHasARelations()) {
            DomainEntityDefinitionDTO otherEntityDomainEntityDefinition = domainEntityDefinitionService.findByIdOrName(hasARelationDefinition.getOtherEntityName()).orElseThrow();
            sb.append(" LEFT OUTER JOIN ")
                    .append(otherEntityDomainEntityDefinition.getTableName())
                    .append(" AS ")
                    .append(hasARelationDefinition.getName())
                    .append(" ON ")
                    .append(domainEntityDefinition.getName())
                    .append(".")
                    .append(hasARelationDefinition.getForeignKeyColumn())
                    .append(" = ")
                    .append(hasARelationDefinition.getName())
                    .append(".ID")
            ;
        }

        return sb.toString();
    }

    public String buildWhereClause() {
        return " WHERE 1 = 1 ";
    }

    public String buildCountSQLCommand(DomainEntityDefinitionDTO domainEntityDefinition) {
        return "SELECT COUNT(*) FROM " + domainEntityDefinition.getTableName();
    }

    public String buildNextValFromSequenceSQLCommand(String sequenceName) {
        return "SELECT NEXT VALUE FOR " + sequenceName;
    }
}
