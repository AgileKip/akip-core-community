package org.akip.web.rest;

import org.akip.service.DecisionDefinitionService;
import org.akip.service.DecisionDeploymentService;
import org.akip.service.dto.DecisionDefinitionDTO;
import org.akip.service.dto.DecisionDeploymentDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.ResponseUtil;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link org.akip.domain.DecisionDefinition}.
 */

@RestController
@RequestMapping("/api")
public class DecisionDefinitionResource {

    private final Logger log = LoggerFactory.getLogger(DecisionDefinitionResource.class);

    private final DecisionDefinitionService decisionDefinitionService;

    private final DecisionDeploymentService decisionDeploymentService;

    public DecisionDefinitionResource(
        DecisionDefinitionService decisionDefinitionService,
        DecisionDeploymentService decisionDeploymentService
    ) {
        this.decisionDefinitionService = decisionDefinitionService;
        this.decisionDeploymentService = decisionDeploymentService;
    }

    /**
     * {@code GET  /decision-definitions} : get all the decisionDefinitions.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of decisionDefinitions in body.
     */
    @GetMapping("/decision-definitions")
    public List<DecisionDefinitionDTO> getAllDecisionDefinitions() {
        log.debug("REST request to get all DecisionDefinitions");
        return decisionDefinitionService.findAll();
    }

    /**
     * {@code GET  /decision-definitions/:id} : get the "id" decisionDefinition.
     *
     * @param idOrDmnDecisionDefinitionId the id of the decisionDefinitionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the decisionDefinitionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/decision-definitions/{idOrDmnDecisionDefinitionId}")
    public ResponseEntity<DecisionDefinitionDTO> getDecisionDefinition(@PathVariable String idOrDmnDecisionDefinitionId) {
        log.debug("REST request to get DecisionDefinition : {}", idOrDmnDecisionDefinitionId);
        Optional<DecisionDefinitionDTO> decisionDefinitionDTO = decisionDefinitionService.findByIdOrDmnDecisionDefinitionId(
            idOrDmnDecisionDefinitionId
        );
        return ResponseUtil.wrapOrNotFound(decisionDefinitionDTO);
    }

    /**
     * {@code GET  /decision-definitions/:idOrDmnDecisionDefinitionId/tenants} : get the "idOrDmnDecisionDefinitionId" decisionDefinition.
     *
     * @param idOrDmnDecisionDefinitionId the id of the decisionDefinitionDTO associated with Tenants.
     * @return the list of tenantsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/decision-definitions/{idOrDmnDecisionDefinitionId}/active-deployments")
    public List<DecisionDeploymentDTO> getActiveDecisionDeployments(@PathVariable String idOrDmnDecisionDefinitionId) {
        log.debug("REST request to get Tenants of the DecisionDefinition : {}", idOrDmnDecisionDefinitionId);
        return decisionDeploymentService.findActiveByDecisionDefinition(idOrDmnDecisionDefinitionId);
    }

    /**
     * {@code GET  /decision-definitions/:idOrDmnDecisionDefinitionId/deployments} : get the "idOrDmnDecisionDefinitionId" decisionDefinition.
     *
     * @param idOrDmnDecisionDefinitionId the id of the decisionDefinitionDTO owner of the DecisionDeployments.
     * @return the list of decisionInstanceDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/decision-definitions/{idOrDmnDecisionDefinitionId}/deployments")
    public List<DecisionDeploymentDTO> getDecisionDeployments(@PathVariable String idOrDmnDecisionDefinitionId) {
        log.debug("REST request to get DecisionDeployments of the DecisionDefinition : {}", idOrDmnDecisionDefinitionId);
        return decisionDeploymentService.findByDecisionDefinition(idOrDmnDecisionDefinitionId);
    }

    /**
     * {@code DELETE  /decision-definitions/:id} : delete the "id" decisionDefinition.
     *
     * @param id the id of the decisionDefinitionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/admin/decision-definitions/{id}")
    public ResponseEntity<Void> deleteDecisionDefinition(@PathVariable Long id) {
        log.debug("REST request to delete DecisionDefinition : {}", id);
        decisionDefinitionService.delete(id);
        return ResponseEntity
            .noContent()
            .build();
    }
}
