package org.akip.web.rest;

import org.akip.exception.BadRequestErrorException;
import org.akip.service.DomainEntityDefinitionDataService;
import org.akip.service.DomainEntityDefinitionService;
import org.akip.service.dto.DomainEntityDefinitionDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/domain-entity-definition")
public class DomainEntityDefinitionDataController {

    private static final Logger LOG = LoggerFactory.getLogger(DomainEntityDefinitionDataController.class);

    private static final String ENTITY_NAME = "domainEntityDefinitionData";

    private final DomainEntityDefinitionDataService domainEntityDefinitionDataService;

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DomainEntityDefinitionService domainEntityDefinitionService;

    public DomainEntityDefinitionDataController(DomainEntityDefinitionDataService domainEntityDefinitionDataService, DomainEntityDefinitionService domainEntityDefinitionService) {
        this.domainEntityDefinitionDataService = domainEntityDefinitionDataService;
        this.domainEntityDefinitionService = domainEntityDefinitionService;
    }

    @GetMapping("/{domainEntityDefinitionName}/data")
    public List<Map<String, Object>> getAllDomainEntityData(@PathVariable("domainEntityDefinitionName") String domainEntityDefinitionName) {
        LOG.debug("REST request to get all data from Domain Entity: {}", domainEntityDefinitionName);
        var result = domainEntityDefinitionDataService.findAll(domainEntityDefinitionName);
        LOG.debug("REST request to get all data from Domain Entity {} retrieved {} lines", domainEntityDefinitionName, result.size());
        return result;
    }

    @GetMapping("/{domainEntityDefinitionName}/data/{domainEntityId}")
    public Map<String, Object> findById(@PathVariable("domainEntityDefinitionName") String domainEntityDefinitionName, @PathVariable("domainEntityId") Long domainEntityId) {
        LOG.debug("REST request to find a Domain Entity ({}) by id: {}", domainEntityDefinitionName, domainEntityId);
        return domainEntityDefinitionDataService.findById(domainEntityDefinitionName, domainEntityId);
    }

    @PostMapping("/{domainEntityDefinitionName}/data")
    public ResponseEntity<Map<String, Object>> create(
            @PathVariable("domainEntityDefinitionName") String domainEntityDefinitionName,
            @RequestBody Map<String, Object> domainEntityData
    ) throws URISyntaxException {
        LOG.debug("REST request to save DomainEntity : {}", domainEntityDefinitionName);
        if (domainEntityData.get("id") != null) {
            throw new BadRequestErrorException("A new domainEntity cannot already have an ID", domainEntityDefinitionName, "idexists");
        }
        DomainEntityDefinitionDTO domainEntityDefinition = domainEntityDefinitionService.findByIdOrName(domainEntityDefinitionName).orElseThrow();
        Map<String, Object> domainEntityDataSaved = domainEntityDefinitionDataService.create(domainEntityDefinitionName, domainEntityData);
        return ResponseEntity.created(new URI("/api/domain-entity-definition/" + domainEntityDefinition.getId() + "/data"))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, domainEntityDefinitionName))
                .body(domainEntityData);
    }


    @PutMapping("/{domainEntityDefinitionName}/data/{domainEntityId}")
    public ResponseEntity<Map<String, Object>> update(
            @PathVariable("domainEntityDefinitionName") String domainEntityDefinitionName,
            @PathVariable("domainEntityId") Long domainEntityId,
            @RequestBody Map<String, Object> domainEntityData
    ) throws URISyntaxException {
        LOG.debug("REST request to update DomainEntity : {}", domainEntityDefinitionName);
        if (domainEntityData.get("id") == null) {
            throw new BadRequestErrorException("A domainEntity cannot be updated without an ID", domainEntityDefinitionName, "idexists");
        }
        DomainEntityDefinitionDTO domainEntityDefinition = domainEntityDefinitionService.findByIdOrName(domainEntityDefinitionName).orElseThrow();
        Map<String, Object> domainEntityDataSaved = domainEntityDefinitionDataService.update(domainEntityDefinitionName, domainEntityData);
        return ResponseEntity.created(new URI("/api/domain-entity-definition/" + domainEntityDefinition.getId() + "/data"))
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, domainEntityDefinitionName))
                .body(domainEntityData);
    }

    @DeleteMapping("/{domainEntityDefinitionName}/data/{domainEntityId}")
    public ResponseEntity<Void> delete(
            @PathVariable("domainEntityDefinitionName") String domainEntityDefinitionName,
            @PathVariable("domainEntityId") Long domainEntityId) {
        LOG.debug("REST request to delete DomainEntity : {} with id {}", domainEntityDefinitionName, domainEntityId);
        domainEntityDefinitionDataService.delete(domainEntityDefinitionName, domainEntityId);
        return ResponseEntity.noContent()
                .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, domainEntityDefinitionName))
                .build();
    }


}
