package org.akip.web.rest;

import org.akip.domainEntity.DomainEntityDAO;
import org.akip.domainEntity.DomainEntitySearchRequest;
import org.akip.domainEntity.DomainEntitySearchResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/domain-entity-definition")
public class DomainEntityDefinitionSearchController {


    private static final Logger LOG = LoggerFactory.getLogger(DomainEntityDefinitionSearchController.class);

    private final DomainEntityDAO domainEntityDAO;

    public DomainEntityDefinitionSearchController(DomainEntityDAO domainEntityDAO) {
        this.domainEntityDAO = domainEntityDAO;
    }


    @GetMapping("/{domainEntityDefinitionName}/new-search-request")
    public DomainEntitySearchRequest getNewSearchRequest(@PathVariable("domainEntityDefinitionName") String domainEntityDefinitionName) {
        LOG.debug("REST request to create a search request to domain entity {}", domainEntityDefinitionName);
        return domainEntityDAO.createNewSearchRequest(domainEntityDefinitionName);
    }


    @PostMapping("/{domainEntityDefinitionName}/search")
    public DomainEntitySearchResult search(@PathVariable("domainEntityDefinitionName") String domainEntityDefinitionName, @RequestBody DomainEntitySearchRequest searchRequest) {
        LOG.debug("REST request to search domain entity {}", domainEntityDefinitionName);
        return domainEntityDAO.search(domainEntityDefinitionName, searchRequest);
    }

}
