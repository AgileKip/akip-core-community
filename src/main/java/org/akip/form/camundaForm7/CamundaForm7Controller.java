package org.akip.form.camundaForm7;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.akip.web.rest.FormDefinitionController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CamundaForm7Controller {

    private final Logger log = LoggerFactory.getLogger(FormDefinitionController.class);

    private final CamundaForm7Service camundaForm7Service;

    public CamundaForm7Controller(CamundaForm7Service camundaForm7Service) {
        this.camundaForm7Service = camundaForm7Service;
    }

    @GetMapping("/camunda-form-7/form-definition/{formDefinitionId}")
    public List<CamundaForm7FieldDef> getCamundaForm7(@PathVariable Long formDefinitionId) throws JsonProcessingException {
        log.debug("REST request CamundaForm7 with id : {} ", formDefinitionId);
        return camundaForm7Service.getCamundaForm7(formDefinitionId);
    }
}
