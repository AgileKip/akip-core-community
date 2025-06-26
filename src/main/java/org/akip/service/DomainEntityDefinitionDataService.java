package org.akip.service;

import org.akip.domainEntity.DomainEntityManager;
import org.akip.service.dto.DomainEntityDefinitionDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DomainEntityDefinitionDataService {


    private final DomainEntityDefinitionService domainEntityDefinitionService;

    private final DomainEntityManager domainEntityManager;


    public DomainEntityDefinitionDataService(DomainEntityDefinitionService domainEntityDefinitionService, DomainEntityManager domainEntityManager) {
        this.domainEntityDefinitionService = domainEntityDefinitionService;
        this.domainEntityManager = domainEntityManager;
    }

    public List<Map<String, Object>> findAll(String domainEntityDefinitionName) {
        DomainEntityDefinitionDTO domainEntityDefinition = domainEntityDefinitionService.findByIdOrName(domainEntityDefinitionName).orElseThrow();
        List<Map<String, Object>> result = domainEntityManager.findAll(domainEntityDefinition);
        return result;
    }

    public Map<String, Object> findById(String domainEntityDefinitionName, Long domainEntityId) {
        DomainEntityDefinitionDTO domainEntityDefinition = domainEntityDefinitionService.findByIdOrName(domainEntityDefinitionName).orElseThrow();
        Map<String, Object> data = domainEntityManager.findById(domainEntityDefinition, domainEntityId);
        return data;
    }

    public Map<String, Object> create(String domainEntityDefinitionName, Map<String, Object> domainEntityData) {
        DomainEntityDefinitionDTO domainEntityDefinition = domainEntityDefinitionService.findByIdOrName(domainEntityDefinitionName).orElseThrow();
        domainEntityData.put("version", 1);
        domainEntityManager.insert(domainEntityDefinition, domainEntityData);
        return domainEntityData;
    }

    public Map<String, Object> update(String domainEntityDefinitionName, Map<String, Object> domainEntityData) {
        DomainEntityDefinitionDTO domainEntityDefinition = domainEntityDefinitionService.findByIdOrName(domainEntityDefinitionName).orElseThrow();
        domainEntityManager.update(domainEntityDefinition, domainEntityData);
        return domainEntityData;
    }

    public void delete(String domainEntityDefinitionName, Long domainEntityId) {
        DomainEntityDefinitionDTO domainEntityDefinition = domainEntityDefinitionService.findByIdOrName(domainEntityDefinitionName).orElseThrow();
        domainEntityManager.delete(domainEntityDefinition, domainEntityId);
    }
}
