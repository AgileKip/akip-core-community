package org.akip.domainEntity;

import org.akip.domain.enumeration.DomainEntityFieldType;
import org.akip.domainEntity.filters.*;
import org.akip.domainEntity.sql.DomainEntitySQLCommandBuilder;
import org.akip.service.DomainEntityDefinitionService;
import org.akip.service.dto.DomainEntityDefinitionDTO;
import org.akip.service.dto.DomainEntityFieldDefinitionDTO;
import org.akip.service.dto.DomainEntityHasARelationDefinitionDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DomainEntityDAO {

    private static final Logger LOG = LoggerFactory.getLogger(DomainEntityDAO.class);

    private final DomainEntityDefinitionService domainEntityDefinitionService;

    private final DomainEntityManager domainEntityManager;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final DomainEntitySQLCommandBuilder domainEntitySQLCommandBuilder;

    public DomainEntityDAO(DomainEntityDefinitionService domainEntityDefinitionService, DomainEntityManager domainEntityManager, NamedParameterJdbcTemplate namedParameterJdbcTemplate, DomainEntitySQLCommandBuilder domainEntitySQLCommandBuilder) {
        this.domainEntityDefinitionService = domainEntityDefinitionService;
        this.domainEntityManager = domainEntityManager;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.domainEntitySQLCommandBuilder = domainEntitySQLCommandBuilder;
    }

    public DomainEntitySearchRequest createNewSearchRequest(String domainEntityDefinitionName) {
        DomainEntityDefinitionDTO domainEntityDefinition = domainEntityDefinitionService.findByIdOrName(domainEntityDefinitionName).orElseThrow();
        DomainEntitySearchRequest domainEntitySearchRequest = new DomainEntitySearchRequest();

        domainEntityDefinition.getFields().forEach(fieldDefinition -> {
            fieldDefinitionToFilter(fieldDefinition).ifPresent(filter -> {
                domainEntitySearchRequest.addFilter(filter);
            });
        });

        domainEntityDefinition.getHasARelations().forEach(hasARelationDefinition -> {
            domainEntitySearchRequest.addFilter(hasARelationDefinitionToFilter(hasARelationDefinition));
        });


        return domainEntitySearchRequest;

    }

    private Optional<DomainEntitySearchFilter> fieldDefinitionToFilter(DomainEntityFieldDefinitionDTO fieldDefinition) {
        if (fieldDefinition ==null) {
            throw new IllegalArgumentException("fieldDefinition is null");
        }

        if (fieldDefinition.getType() == DomainEntityFieldType.STRING || fieldDefinition.getType() == DomainEntityFieldType.SMALL_STRING || fieldDefinition.getType() == DomainEntityFieldType.LONG_STRING) {
            return Optional.of(new DomainEntitySearchStringFilter().id(fieldDefinition.getName()));
        }

        if (fieldDefinition.getType() == DomainEntityFieldType.INT || fieldDefinition.getType() == DomainEntityFieldType.REAL) {
            return Optional.of(new DomainEntitySearchNumberFilter().id(fieldDefinition.getName()));
        }

        if (fieldDefinition.getType() == DomainEntityFieldType.DATE) {
            return Optional.of(new DomainEntitySearchDateFilter().id(fieldDefinition.getName()));
        }

        return Optional.empty();
    }


    private DomainEntitySearchFilter hasARelationDefinitionToFilter(DomainEntityHasARelationDefinitionDTO hasARelationDefinition) {
        DomainEntityDefinitionDTO otherEntityDefinition = domainEntityDefinitionService.findByIdOrName(hasARelationDefinition.getOtherEntityName()).orElseThrow();

        List values = domainEntityManager.findAll(otherEntityDefinition).stream().map(originalMap -> {
            Map newMap = new HashMap();
            newMap.put("id", originalMap.get("id"));
            newMap.put("text", originalMap.get(hasARelationDefinition.getOtherEntityDisplayFieldName()));
            return newMap;

        }).collect(Collectors.toList());

        return new DomainEntitySearchHasARelationFilter()
                .id(hasARelationDefinition.getName())
                .otherEntityDisplayFieldName(hasARelationDefinition.getOtherEntityDisplayFieldName())
                .values(values);
    }

    public DomainEntitySearchResult search(String domainEntityDefinitionName, DomainEntitySearchRequest domainEntitySearchRequest) {
        DomainEntityDefinitionDTO domainEntityDefinition = domainEntityDefinitionService.findByIdOrName(domainEntityDefinitionName).orElseThrow();
        DomainEntitySearchResult result = new DomainEntitySearchResult();
        StringBuilder sql = new StringBuilder(domainEntitySQLCommandBuilder.buildFindAllSQLCommand(domainEntityDefinition));

        domainEntitySearchRequest.getFilters()
                .stream()
                .filter(DomainEntitySearchFilter::isActive)
                .forEach(filter -> {
                    filter.buildCriteria(domainEntityDefinition, sql);
                });

        MapSqlParameterSource params = new MapSqlParameterSource();
        domainEntitySearchRequest.getFilters()
                .stream()
                .filter(DomainEntitySearchFilter::isActive)
                .forEach(filter -> {
            filter.setParameters(params);
        });

        LOG.debug("SQL: {}", sql);
        result.setResults(namedParameterJdbcTemplate.query(sql.toString(), params, new DomainEntityListExtractor(domainEntityDefinition)));
        return result;
    }
}
