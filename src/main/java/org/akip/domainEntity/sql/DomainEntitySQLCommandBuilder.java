package org.akip.domainEntity.sql;

import org.akip.service.dto.DomainEntityDefinitionDTO;

public interface DomainEntitySQLCommandBuilder {

    public String buildFindByIdSQLCommand(DomainEntityDefinitionDTO domainEntityDefinition, Object id);
    public String buildFindAllSQLCommand(DomainEntityDefinitionDTO domainEntityDefinition);
    public String buildDeleteByIdSQLCommand(DomainEntityDefinitionDTO domainEntityDefinition, Object id);
    public String buildSelectClause(DomainEntityDefinitionDTO domainEntityDefinition);
    public String buildFromClause(DomainEntityDefinitionDTO domainEntityDefinition);
    public String buildJoinClauses(DomainEntityDefinitionDTO domainEntityDefinition);
    public String buildWhereClause();
    public String buildCountSQLCommand(DomainEntityDefinitionDTO domainEntityDefinition);
    public String buildNextValFromSequenceSQLCommand(String sequenceName);
    public String buildFindByColumnSQLCommand(DomainEntityDefinitionDTO domainEntityDefinition, String columnName, Object value);
}
