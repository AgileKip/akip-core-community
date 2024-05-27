package org.akip.form.camundaForm8;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.akip.web.rest.FormDefinitionController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CamundaForm8Controller {

    private final Logger log = LoggerFactory.getLogger(FormDefinitionController.class);

    private final CamundaForm8Service camundaForm8Service;

    public CamundaForm8Controller(CamundaForm8Service camundaForm8Service) {
        this.camundaForm8Service = camundaForm8Service;
    }

    @GetMapping("/camunda-form-8/form-definition/{formDefinitionId}")
    public CamundaForm8Def getCamundaForm8(@PathVariable Long formDefinitionId) throws JsonProcessingException {
        log.debug("REST request CamundaForm8 with id : {} ", formDefinitionId);
        return camundaForm8Service.getCamundaForm8(formDefinitionId);
    }
}
