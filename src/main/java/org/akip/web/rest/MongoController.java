package org.akip.web.rest;

import org.akip.service.MongoService;
import org.akip.service.dto.Filter;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mongo")
public class MongoController {

    private final MongoService mongoService;

    public MongoController(MongoService mongoService) {
        this.mongoService = mongoService;
    }

    @PostMapping("/searchEntities/{entityName}")
    public List<Object> findByEntityName(@PathVariable String entityName) {
        return mongoService.findByEntityName(entityName);
    }

    @PostMapping("/searchFilteredEntities/{entityName}")
    public List<Object> findEntitiesByFilters(@RequestBody List<Filter> filters, @PathVariable String entityName) {
        return mongoService.findEntitiesFiltered(filters, entityName, "DomainEntities");
    }
}
