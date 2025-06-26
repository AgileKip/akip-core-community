package org.akip.domainEntity;

import org.akip.domain.enumeration.DomainEntityDefinitionStatus;
import org.akip.domain.enumeration.DomainEntityFieldType;
import org.akip.service.dto.DomainEntityDefinitionDTO;
import org.akip.service.dto.DomainEntityFieldDefinitionDTO;
import org.akip.service.dto.DomainEntityHasARelationDefinitionDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DomainEntityDDLManager {

    private static final Logger LOG = LoggerFactory.getLogger(DomainEntityDDLManager.class);

    private final JdbcTemplate jdbcTemplate;

    public DomainEntityDDLManager(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void createTable(DomainEntityDefinitionDTO domainEntityDefinition) {
        if (domainEntityDefinition.getStatus() == DomainEntityDefinitionStatus.EXECUTED) {
            return;
        }
        jdbcTemplate.execute(buildCreateTableSQLCommand(domainEntityDefinition));
    }

    public void updateTable(DomainEntityDefinitionDTO domainEntityDefinitionOriginal, DomainEntityDefinitionDTO domainEntityDefinitionNew) {

        if (!domainEntityDefinitionOriginal.getTableName().equals(domainEntityDefinitionNew.getTableName())) {
            String sql = buildRenameTableSQLCommand(domainEntityDefinitionOriginal, domainEntityDefinitionNew);
            LOG.debug("SQL: {}", sql);
            jdbcTemplate.execute(sql);
        }

        extractNewColumns(domainEntityDefinitionNew).forEach(fieldDefinition -> {
            String sql = buildAddColumnSQLCommand(domainEntityDefinitionNew, fieldDefinition);
            LOG.debug("SQL: {}", sql);
            jdbcTemplate.execute(sql);
        });

        extractRenamedColumns(domainEntityDefinitionOriginal, domainEntityDefinitionNew).forEach(renamedColumnDefinition -> {
            String sql = buildRenameColumnSQLCommand(domainEntityDefinitionNew, renamedColumnDefinition);
            LOG.debug("SQL: {}", sql);
            jdbcTemplate.execute(sql);
        });

        extractModifiedColumns(domainEntityDefinitionOriginal, domainEntityDefinitionNew).forEach(fieldDefinition -> {
            String sql = buildModifyColumnSQLCommand(domainEntityDefinitionNew, fieldDefinition);
            LOG.debug("SQL: {}", sql);
            jdbcTemplate.execute(sql);
        });

        extractDroppedColumns(domainEntityDefinitionOriginal, domainEntityDefinitionNew).forEach(fieldDefinition -> {
            String sql = buildDropColumnSQLCommand(domainEntityDefinitionNew, fieldDefinition);
            LOG.debug("SQL: {}", sql);
            jdbcTemplate.execute(sql);
        });

        extractNewForeignKeyColumns(domainEntityDefinitionNew).forEach(hasARelationDefinition -> {
            String sql = buildAddForeignKeyColumnSQLCommand(domainEntityDefinitionNew, hasARelationDefinition);
            LOG.debug("SQL: {}", sql);
            jdbcTemplate.execute(sql);
        });

        extractRenamedForeignKeyColumns(domainEntityDefinitionOriginal, domainEntityDefinitionNew).forEach(renamedColumnDefinition -> {
            String sql = buildRenameColumnSQLCommand(domainEntityDefinitionNew, renamedColumnDefinition);
            LOG.debug("SQL: {}", sql);
            jdbcTemplate.execute(sql);
        });

        extractDroppedForeignKeyColumns(domainEntityDefinitionOriginal, domainEntityDefinitionNew).forEach(fieldDefinition -> {
            String sql = buildDropForeignKeyColumnSQLCommand(domainEntityDefinitionNew, fieldDefinition);
            LOG.debug("SQL: {}", sql);
            jdbcTemplate.execute(sql);
        });
    }

    private String buildRenameTableSQLCommand(DomainEntityDefinitionDTO domainEntityDefinitionOriginal, DomainEntityDefinitionDTO domainEntityDefinitionNew) {
        return new StringBuilder()
                .append("ALTER TABLE ")
                .append(domainEntityDefinitionOriginal.getTableName())
                .append(" RENAME TO ")
                .append(domainEntityDefinitionNew.getTableName())
                .toString();
    }


    private List<DomainEntityFieldDefinitionDTO> extractNewColumns(DomainEntityDefinitionDTO domainEntityDefinitionNew) {
        return domainEntityDefinitionNew
                .getFields()
                .stream()
                .filter(fieldDefinition -> fieldDefinition.getId() == null)
                .collect(Collectors.toList());
    }

    private List<RenamedColumnDefinitionDTO> extractRenamedColumns(DomainEntityDefinitionDTO domainEntityDefinitionOriginal, DomainEntityDefinitionDTO domainEntityDefinitionNew) {
        List<RenamedColumnDefinitionDTO> renamedColumns = new ArrayList<>();

        for (DomainEntityFieldDefinitionDTO fieldDefinitionOriginal : domainEntityDefinitionOriginal.getFields()) {
            Optional<DomainEntityFieldDefinitionDTO> optionalFieldDefinitionNew = domainEntityDefinitionNew
                    .getFields()
                    .stream()
                    .filter(fieldDefinitionNew -> fieldDefinitionOriginal.getId().equals(fieldDefinitionNew.getId()))
                    .findFirst();
            if (optionalFieldDefinitionNew.isEmpty()) {
                continue;
            }
            DomainEntityFieldDefinitionDTO fieldDefinitionNew = optionalFieldDefinitionNew.get();
            if (!fieldDefinitionOriginal.getName().equals(fieldDefinitionNew.getName())) {
                renamedColumns.add(new RenamedColumnDefinitionDTO(fieldDefinitionOriginal.getName(), fieldDefinitionNew.getName()));
            }

        }

        return renamedColumns;
    }

    private List<DomainEntityFieldDefinitionDTO> extractModifiedColumns(DomainEntityDefinitionDTO domainEntityDefinitionOriginal, DomainEntityDefinitionDTO domainEntityDefinitionNew) {
        List<DomainEntityFieldDefinitionDTO> modifiedColumns = new ArrayList<>();
        for (DomainEntityFieldDefinitionDTO fieldDefinitionOriginal : domainEntityDefinitionOriginal.getFields()) {
            Optional<DomainEntityFieldDefinitionDTO> optionalFieldDefinitionNew = domainEntityDefinitionNew
                    .getFields()
                    .stream()
                    .filter(fieldDefinitionNew -> fieldDefinitionOriginal.getId().equals(fieldDefinitionNew.getId()))
                    .findFirst();
            if (optionalFieldDefinitionNew.isEmpty()) {
                continue;
            }
            DomainEntityFieldDefinitionDTO fieldDefinitionNew = optionalFieldDefinitionNew.get();
            if (!fieldDefinitionOriginal.getType().equals(fieldDefinitionNew.getType())) {
                modifiedColumns.add(fieldDefinitionNew);
            }
        }
        return modifiedColumns;
    }

    private List<DomainEntityFieldDefinitionDTO> extractDroppedColumns(DomainEntityDefinitionDTO domainEntityDefinitionOriginal, DomainEntityDefinitionDTO domainEntityDefinitionNew) {
        List<DomainEntityFieldDefinitionDTO> droppedColumns = new ArrayList<>();

        for (DomainEntityFieldDefinitionDTO fieldDefinitionOriginal : domainEntityDefinitionOriginal.getFields()) {
            Optional<DomainEntityFieldDefinitionDTO> optionalFieldDefinitionNew = domainEntityDefinitionNew
                    .getFields()
                    .stream()
                    .filter(fieldDefinitionNew -> fieldDefinitionOriginal.getId().equals(fieldDefinitionNew.getId()))
                    .findFirst();
            if (optionalFieldDefinitionNew.isEmpty()) {
                droppedColumns.add(fieldDefinitionOriginal);
            }
        }

        return droppedColumns;
    }

    private List<DomainEntityHasARelationDefinitionDTO> extractNewForeignKeyColumns(DomainEntityDefinitionDTO domainEntityDefinitionNew) {
        return domainEntityDefinitionNew
                .getHasARelations()
                .stream()
                .filter(hasARelationDefinition -> hasARelationDefinition.getId() == null)
                .collect(Collectors.toList());
    }

    private List<RenamedColumnDefinitionDTO> extractRenamedForeignKeyColumns(DomainEntityDefinitionDTO domainEntityDefinitionOriginal, DomainEntityDefinitionDTO domainEntityDefinitionNew) {
        List<RenamedColumnDefinitionDTO> renamedColumns = new ArrayList<>();

        for (DomainEntityHasARelationDefinitionDTO hasARelationDefinitionOriginal : domainEntityDefinitionOriginal.getHasARelations()) {
            Optional<DomainEntityHasARelationDefinitionDTO> optionalFieldDefinitionNew = domainEntityDefinitionNew
                    .getHasARelations()
                    .stream()
                    .filter(hasARelationDefinitionNew -> hasARelationDefinitionOriginal.getId().equals(hasARelationDefinitionNew.getId()))
                    .findFirst();
            if (optionalFieldDefinitionNew.isEmpty()) {
                continue;
            }
            DomainEntityHasARelationDefinitionDTO fieldDefinitionNew = optionalFieldDefinitionNew.get();
            if (!hasARelationDefinitionOriginal.getName().equals(fieldDefinitionNew.getName())) {
                renamedColumns.add(new RenamedColumnDefinitionDTO(hasARelationDefinitionOriginal.getForeignKeyColumn(), fieldDefinitionNew.getForeignKeyColumn()));
            }

        }

        return renamedColumns;
    }

    private List<DomainEntityHasARelationDefinitionDTO> extractDroppedForeignKeyColumns(DomainEntityDefinitionDTO domainEntityDefinitionOriginal, DomainEntityDefinitionDTO domainEntityDefinitionNew) {
        List<DomainEntityHasARelationDefinitionDTO> droppedColumns = new ArrayList<>();

        for (var hasARelationDefinitionOriginal : domainEntityDefinitionOriginal.getHasARelations()) {
            Optional<DomainEntityHasARelationDefinitionDTO> optionalHasARelationDefinitionNew = domainEntityDefinitionNew
                    .getHasARelations()
                    .stream()
                    .filter(fieldDefinitionNew -> hasARelationDefinitionOriginal.getId().equals(fieldDefinitionNew.getId()))
                    .findFirst();
            if (optionalHasARelationDefinitionNew.isEmpty()) {
                droppedColumns.add(hasARelationDefinitionOriginal);
            }
        }

        return droppedColumns;
    }

    private String buildCreateTableSQLCommand(DomainEntityDefinitionDTO domainEntityDefinition) {
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE " + domainEntityDefinition.getTableName() + " ( ");
        sb.append(" id " + translateToDatabaseType(DomainEntityFieldType.INT) + " NOT NULL, ");
        for (DomainEntityFieldDefinitionDTO fieldDefinition: domainEntityDefinition.getFields()) {
            sb.append(" " + fieldDefinition.getName() + " " + translateToDatabaseType(fieldDefinition.getType()) + ", ");
        }

        for (DomainEntityHasARelationDefinitionDTO hasARelationDefinition: domainEntityDefinition.getHasARelations()) {
            sb.append(" " + hasARelationDefinition.getForeignKeyColumn() + " " + translateToDatabaseType(DomainEntityFieldType.INT) + ", ");
        }

        sb.append(" version " + translateToDatabaseType(DomainEntityFieldType.INT));
        sb.append(" ); ");
        return sb.toString();
    }


    private String buildAddColumnSQLCommand(DomainEntityDefinitionDTO domainEntityDefinitionNew, DomainEntityFieldDefinitionDTO fieldDefinition) {
        return "ALTER TABLE " + domainEntityDefinitionNew.getTableName() + " ADD " + fieldDefinition.getName() + " " + translateToDatabaseType(fieldDefinition.getType());
    }

    private String buildRenameColumnSQLCommand(DomainEntityDefinitionDTO domainEntityDefinitionNew, RenamedColumnDefinitionDTO renamedColumnDefinition) {
        return "ALTER TABLE " + domainEntityDefinitionNew.getTableName() + " RENAME COLUMN " + renamedColumnDefinition.getOriginalColumnName() + " TO " + renamedColumnDefinition.getNewColumnName();
    }

    private String buildModifyColumnSQLCommand(DomainEntityDefinitionDTO domainEntityDefinitionNew, DomainEntityFieldDefinitionDTO fieldDefinition) {
        return "ALTER TABLE " + domainEntityDefinitionNew.getTableName() + " ALTER " + fieldDefinition.getName() + " " + translateToDatabaseType(fieldDefinition.getType());
    }

    private String buildDropColumnSQLCommand(DomainEntityDefinitionDTO domainEntityDefinition, DomainEntityFieldDefinitionDTO fieldDefinition) {
        return "ALTER TABLE " + domainEntityDefinition.getTableName() + " DROP COLUMN " + fieldDefinition.getName();
    }

    private String buildAddForeignKeyColumnSQLCommand(DomainEntityDefinitionDTO domainEntityDefinition, DomainEntityHasARelationDefinitionDTO hasARelationDefinition) {
        return "ALTER TABLE " + domainEntityDefinition.getTableName() + " ADD " + hasARelationDefinition.getForeignKeyColumn() + " INT";
    }

    private String buildDropForeignKeyColumnSQLCommand(DomainEntityDefinitionDTO domainEntityDefinition, DomainEntityHasARelationDefinitionDTO hasARelationDefinition) {
        return "ALTER TABLE " + domainEntityDefinition.getTableName() + " DROP COLUMN " + hasARelationDefinition.getForeignKeyColumn();
    }

    private String translateToDatabaseType(DomainEntityFieldType type) {
        switch (type) {
            case STRING: {
                return "VARCHAR(100)";
            }
            case SMALL_STRING: {
                return "VARCHAR(50)";
            }
            case LONG_STRING: {
                return "VARCHAR(255)";
            }
            case INT: {
                return "INT";
            }
            case REAL: {
                return "REAL";
            }
            case DATE: {
                return "DATE";
            }
            case CLOB: {
                return "CLOB";
            }
            default: {
                return "VARCHAR(255)";
            }
        }
    }



}
