package org.akip.web.rest;

import org.akip.service.FormDefinitionService;
import org.akip.service.dto.FormDefinitionDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class FormDefinitionController {

    private final Logger log = LoggerFactory.getLogger(FormDefinitionController.class);
    private final FormDefinitionService formDefinitionService;

    public FormDefinitionController(FormDefinitionService formDefinitionService) {
        this.formDefinitionService = formDefinitionService;
    }

    @GetMapping("/form-definition/{id}")
    public FormDefinitionDTO find(@PathVariable Long id) {
        log.debug("REST request to get FormDefinition of the FormDefinitionId : {} ", id);
        return formDefinitionService.findById(id).get();
    }

}
