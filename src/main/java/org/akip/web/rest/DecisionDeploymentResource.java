package org.akip.web.rest;

import org.akip.service.DecisionDeploymentService;
import org.akip.service.dto.DecisionDeploymentDTO;
import org.akip.service.dto.DecisionDeploymentDmnModelDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.Optional;

/**
 * REST controller for managing {@link org.akip.domain.DecisionDefinition}.
 */
@RestController
@RequestMapping("/api")
public class DecisionDeploymentResource {

    private final Logger log = LoggerFactory.getLogger(DecisionDeploymentResource.class);

    private static final String MESSAGE_PROPERTIES_SAVED = "Properties Successfully Saved";

    private final DecisionDeploymentService decisionDeploymentService;

    public DecisionDeploymentResource(DecisionDeploymentService decisionDeploymentService) {
        this.decisionDeploymentService = decisionDeploymentService;
    }

    /**
     * {@code POST  /decision-definitions} : Create a new Definition.
     *
     * @param decisionDeploymentDTO the decisionDeploymentDTO to deploy.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new decisionDeploymentDTO, or with status {@code 400 (Bad Request)} if the decisionDeploymentDTO has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/admin/decision-deployment/deploy")
    public ResponseEntity<Void> deploy(@RequestBody DecisionDeploymentDTO decisionDeploymentDTO) throws URISyntaxException {
        log.debug("REST request to deploy DecisionDeployment : {}", decisionDeploymentDTO);
        DecisionDeploymentDTO result = decisionDeploymentService.deploy(decisionDeploymentDTO);
        return ResponseEntity
            .created(new URI("/api/decision-deployment/" + result.getId()))
            .headers(HeaderUtil.createAlert(HeaderConstants.APPLICATION_NAME, buildDeployedMessage(result), result.getCamundaDecisionDefinitionId()))
            .build();
    }

    private String buildDeployedMessage(DecisionDeploymentDTO decisionDeploymentDTO) {
        return "Decision deployed successfully: " + decisionDeploymentDTO.getCamundaDeploymentId();
    }

    /**
     * {@code GET  /decision-definition-deployment/:id} : get the decisionDeployment.
     *
     * @param id the id of the decisionDeployment to retrieve.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @GetMapping("/decision-deployment/{id}")
    public ResponseEntity<DecisionDeploymentDTO> getDecisionDeployment(@PathVariable("id") Long id) {
        log.debug("REST request to get DecisionDeployment : {}", id);
        Optional<DecisionDeploymentDTO> result = decisionDeploymentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(result);
    }

    /**
     * {@code GET  /decision-definition-deployment/:id} : get the decisionDeployment.
     *
     * @param id the id of the decisionDeployment to retrieve.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @GetMapping("/decision-deployment/{id}/dmnModel")
    public ResponseEntity<DecisionDeploymentDmnModelDTO> getDecisionDeploymentDmnModel(@PathVariable("id") Long id) {
        log.debug("REST request to get DecisionDeployment : {}", id);
        Optional<DecisionDeploymentDmnModelDTO> result = decisionDeploymentService.findDmnModel(id);
        return ResponseUtil.wrapOrNotFound(result);
    }

    /**
     * {@code GET  /decision-definition-deployment/:id/active} : active the "id" decisionDeployment.
     *
     * @param id the id of the decisionDeployment to inactive.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @GetMapping("/decision-deployment/{id}/active")
    public ResponseEntity<Void> activeDecisionDeployment(@PathVariable("id") Long id) {
        log.debug("REST request to inactive DecisionDeployment : {}", id);
        decisionDeploymentService.active(id);
        return ResponseEntity
            .noContent()
            .build();
    }

    /**
     * {@code GET  /decision-definition-deployment/:id/inactive} : inactive the "id" decisionDeployment.
     *
     * @param id the id of the decisionDeployment to inactive.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @GetMapping("/decision-deployment/{id}/inactive")
    public ResponseEntity<Void> inactiveDecisionDeployment(@PathVariable("id") Long id) {
        log.debug("REST request to inactive DecisionDeployment : {}", id);
        decisionDeploymentService.inactive(id);
        return ResponseEntity
            .noContent()
            .build();
    }

    @PutMapping("/admin/decision-deployment/{id}/properties")
    public ResponseEntity<Void> saveProperties(@PathVariable("id") Long id, @RequestBody Map<String, String> properties) {
        log.debug("REST request to save DecisionDeployment properties: {}", id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createAlert(HeaderConstants.APPLICATION_NAME, MESSAGE_PROPERTIES_SAVED, id.toString()))
            .build();
    }
}
