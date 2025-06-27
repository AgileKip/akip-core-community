package org.akip.domainEntity;

import org.akip.domainEntity.sql.DomainEntitySQLCommandBuilder;
import org.akip.service.DomainEntityDefinitionService;
import org.akip.service.dto.DomainEntityDefinitionDTO;
import org.akip.service.dto.DomainEntityHasManyRelationDefinitionDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class DomainEntityManager {

    private static final Logger LOG = LoggerFactory.getLogger(DomainEntityManager.class);

    private final JdbcTemplate jdbcTemplate;

    private final DomainEntityDefinitionService domainEntityDefinitionService;

    private final DomainEntitySQLCommandBuilder domainEntitySQLCommandBuilder;

    public DomainEntityManager(JdbcTemplate jdbcTemplate, DomainEntityDefinitionService domainEntityDefinitionService, DomainEntitySQLCommandBuilder domainEntitySQLCommandBuilder) {
        this.jdbcTemplate = jdbcTemplate;
        this.domainEntityDefinitionService = domainEntityDefinitionService;
        this.domainEntitySQLCommandBuilder = domainEntitySQLCommandBuilder;
    }

    public Map<String, Object> findById(DomainEntityDefinitionDTO domainEntityDefinition, Long id) {
        String sql = domainEntitySQLCommandBuilder.buildFindByIdSQLCommand(domainEntityDefinition, id);
        LOG.debug("SQL: {}", sql);
        Map<String, Object> data = jdbcTemplate.query(sql, new DomainEntityExtractor(domainEntityDefinition));

        //Iterate over the hasManyRelations and retrieve them
        domainEntityDefinition.getHasManyRelations().forEach(hasManyRelation -> {
            DomainEntityDefinitionDTO otherEntityDomainEntityDefinition = domainEntityDefinitionService.findByIdOrName(hasManyRelation.getOtherEntityName()).orElseThrow();
            data.put(hasManyRelation.getCollectionName(), findByColumn(otherEntityDomainEntityDefinition, domainEntityDefinition.getForeignKeyColumn(), id));
        });

        return data;
    }

    public List<Map<String, Object>> findAll(DomainEntityDefinitionDTO domainEntityDefinition) {
        String sql = domainEntitySQLCommandBuilder.buildFindAllSQLCommand(domainEntityDefinition);
        LOG.debug("SQL: {}", sql);
        return jdbcTemplate.query(sql, new DomainEntityListExtractor(domainEntityDefinition));
    }

    public List<Map<String, Object>> findByColumn(DomainEntityDefinitionDTO domainEntityDefinition, String columnName, Object value) {
        String sql = domainEntitySQLCommandBuilder.buildFindByColumnSQLCommand(domainEntityDefinition, columnName, value);
        LOG.debug("SQL: {}", sql);
        return jdbcTemplate.query(sql, new DomainEntityListExtractor(domainEntityDefinition));
    }

    public int count(DomainEntityDefinitionDTO domainEntityDefinition) {
        return jdbcTemplate.queryForObject(domainEntitySQLCommandBuilder.buildCountSQLCommand(domainEntityDefinition), Integer.class);
    }

    @Transactional
    public int insert(DomainEntityDefinitionDTO domainEntityDefinition, Map<String, Object> data) {
        List<String> columns = domainEntityDefinition.getColumns();
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        simpleJdbcInsert.withTableName(domainEntityDefinition.getTableName());
        simpleJdbcInsert.usingColumns(columns.toArray(new String[columns.size()]));

        if (data.get("id") == null) {
            data.put("id", nextIdFromSequence("sequence_generator"));
        }

        insertHasManyRelations(domainEntityDefinition, data);

        return simpleJdbcInsert.execute(data);
    }

    private Long nextIdFromSequence(String sequenceName) {
        String sql = domainEntitySQLCommandBuilder.buildNextValFromSequenceSQLCommand(sequenceName);
        LOG.debug("SQL: {}", sql);
        return jdbcTemplate.queryForObject(sql, Long.class);
    }

    @Transactional
    public void update(DomainEntityDefinitionDTO domainEntityDefinition, Map<String, Object> data, UpdateDomainEntityDescriptorDTO updateDomainEntityDescriptor) {
        List<String> columnsToUpdate = updateDomainEntityDescriptor.getUpdateAllColumns() ? domainEntityDefinition.getColumns(): updateDomainEntityDescriptor.getUpdatedColumns();
        columnsToUpdate.removeAll(updateDomainEntityDescriptor.getReadOnlyColumns());
        columnsToUpdate.forEach(columnName -> {
            String sql = new StringBuilder()
                    .append("update ")
                    .append(domainEntityDefinition.getTableName())
                    .append(" set ")
                    .append(columnName)
                    .append(" = ? where id = ?")
                    .toString();
            LOG.debug("SQL: {}", sql);
            jdbcTemplate.update(sql, data.get(columnName), data.get("id"));
        });

        updateHasManyRelations(domainEntityDefinition, data, updateDomainEntityDescriptor);
    }



    @Transactional
    public void update(DomainEntityDefinitionDTO domainEntityDefinition, Map<String, Object> data) {
        update(domainEntityDefinition, data, UpdateDomainEntityDescriptorDTO.defaultUpdateDomainEntityDescriptorDTO());
    }

    @Transactional
    public void delete(DomainEntityDefinitionDTO domainEntityDefinition, Long id) {
        String sql = domainEntitySQLCommandBuilder.buildDeleteByIdSQLCommand(domainEntityDefinition, id);
        LOG.debug("SQL: {}", sql);
        jdbcTemplate.execute(sql);

        deleteHasManyRelations(domainEntityDefinition, id);
    }

    private void insertHasManyRelations(DomainEntityDefinitionDTO domainEntityDefinition, Map<String, Object> map) {
        for (DomainEntityHasManyRelationDefinitionDTO hasManyRelationDefinition : domainEntityDefinition.getHasManyRelations()) {
            insertHasManyRelation(domainEntityDefinition, map, hasManyRelationDefinition);
        }
    }

    private void insertHasManyRelation(DomainEntityDefinitionDTO domainEntityDefinition, Map<String, Object> data, DomainEntityHasManyRelationDefinitionDTO hasManyRelationDefinition) {
        //If the list is not um the data map, return
        if (data.get(hasManyRelationDefinition.getCollectionName()) == null) {
            return;
        }
        DomainEntityDefinitionDTO otherEntityDomainEntityDefinition = domainEntityDefinitionService.findByIdOrName(hasManyRelationDefinition.getOtherEntityName()).orElseThrow();
        for (Map<String, Object> listItemMap: (List<Map<String, Object>>) data.get(hasManyRelationDefinition.getCollectionName())) {
            listItemMap.put(domainEntityDefinition.getForeignKeyColumn(), data.get("id"));
            insert(otherEntityDomainEntityDefinition, listItemMap);
        }
    }

    private void updateHasManyRelations(DomainEntityDefinitionDTO domainEntityDefinition, Map<String, Object> data,  UpdateDomainEntityDescriptorDTO updateDomainEntityDescriptor) {
        List<String> hasManyRelationsToUpdate = updateDomainEntityDescriptor.getUpdateAllHasManyRelations() ? domainEntityDefinition.getHasManyRelationNames(): updateDomainEntityDescriptor.getUpdatedHasManyRelations();
        for (String hasManyRelationDefinition : hasManyRelationsToUpdate) {
            updateHasManyRelation(domainEntityDefinition, data, hasManyRelationDefinition, updateDomainEntityDescriptor);
        }
    }

    private void updateHasManyRelation(DomainEntityDefinitionDTO domainEntityDefinition, Map<String, Object> map, String hasManyRelationName, UpdateDomainEntityDescriptorDTO updateDomainEntityDescriptor) {
        DomainEntityHasManyRelationDefinitionDTO hasManyRelationDefinition = domainEntityDefinition.getHasManyRelationDefinitionByName(hasManyRelationName);
        //If the list is not um the data map, return
        if (map.get(hasManyRelationDefinition.getCollectionName()) == null) {
            return;
        }
        DomainEntityDefinitionDTO otherEntityDomainEntityDefinition = domainEntityDefinitionService.findByIdOrName(hasManyRelationDefinition.getOtherEntityName()).orElseThrow();
        for (Map<String, Object> listItemMap: (List<Map<String, Object>>) map.get(hasManyRelationDefinition.getCollectionName())) {
            //Inserting new items
            if (listItemMap.get("id") == null) {
                listItemMap.put(domainEntityDefinition.getForeignKeyColumn(), map.get("id"));
                insert(otherEntityDomainEntityDefinition, listItemMap);
                continue;
            }

            if (listItemMap.get("AKIP-SYNC-STATUS") == null) {
                continue;
            }

            if (listItemMap.get("AKIP-SYNC-STATUS").equals("updated")) {
                update(otherEntityDomainEntityDefinition, listItemMap, updateDomainEntityDescriptor.getUpdateDomainEntityDescriptorForHasManyRelation(hasManyRelationName));
                continue;
            }

            if (listItemMap.get("AKIP-SYNC-STATUS").equals("deleted")) {
                delete(otherEntityDomainEntityDefinition, Long.valueOf(listItemMap.get("id").toString()));
            }
        }
    }

    private void deleteHasManyRelations(DomainEntityDefinitionDTO domainEntityDefinition, Long domainEntityId) {
        for (String hasManyRelationDefinitionToDelete : domainEntityDefinition.getHasManyRelationNames()) {
            deleteHasManyRelation(domainEntityDefinition, domainEntityId, hasManyRelationDefinitionToDelete);
        }
    }

    private void deleteHasManyRelation(DomainEntityDefinitionDTO domainEntityDefinition, Long domainEntityId, String hasManyRelationName) {
        DomainEntityHasManyRelationDefinitionDTO hasManyRelationDefinition = domainEntityDefinition.getHasManyRelationDefinitionByName(hasManyRelationName);
        DomainEntityDefinitionDTO otherEntityDomainEntityDefinition = domainEntityDefinitionService.findByIdOrName(hasManyRelationDefinition.getOtherEntityName()).orElseThrow();
        for (Map<String, Object> otherEntityData: findByColumn(otherEntityDomainEntityDefinition, domainEntityDefinition.getForeignKeyColumn(), domainEntityId)) {
            delete(otherEntityDomainEntityDefinition, (Long) otherEntityData.get("id"));
        }
    }


}
