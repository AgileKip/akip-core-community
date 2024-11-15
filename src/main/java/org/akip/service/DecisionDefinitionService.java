package org.akip.service;

import org.akip.domain.DecisionDefinition;
import org.akip.exception.BadRequestErrorException;
import org.akip.repository.DecisionDefinitionRepository;
import org.akip.service.dto.DecisionDefinitionDTO;
import org.akip.service.mapper.DecisionDefinitionMapper;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.model.dmn.DmnModelInstance;
import org.camunda.bpm.model.dmn.instance.Decision;
import org.camunda.bpm.model.xml.type.ModelElementType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link DecisionDefinition}.
 */
@Service
@Transactional
public class DecisionDefinitionService {

    private final Logger log = LoggerFactory.getLogger(DecisionDefinitionService.class);

    private final DecisionDefinitionRepository decisionDefinitionRepository;

    private final DecisionDefinitionMapper decisionDefinitionMapper;

    public DecisionDefinitionService(
        DecisionDefinitionRepository decisionDefinitionRepository,
        DecisionDefinitionMapper decisionDefinitionMapper
    ) {
        this.decisionDefinitionRepository = decisionDefinitionRepository;
        this.decisionDefinitionMapper = decisionDefinitionMapper;
    }

    public DecisionDefinition createOrUpdateDecisionDefinition(DmnModelInstance dmnModelInstance) {
        Decision decision = extracAndValidDecisionFromModel(dmnModelInstance);
        Optional<DecisionDefinition> optionalDecisionDefinition = decisionDefinitionRepository.findByDmnDecisionDefinitionId(
            decision.getId()
        );

        if (optionalDecisionDefinition.isPresent()) {
            return updateDecisionDefinition(decision);
        }
        return createDecisionDefinition(decision);
    }

    private Decision extracAndValidDecisionFromModel(DmnModelInstance modelInstance) {
        ModelElementType decisionType = modelInstance.getModel().getType(Decision.class);
        Decision decision = (Decision) modelInstance.getModelElementsByType(decisionType).iterator().next();

        if (StringUtils.isBlank(decision.getName())) {
            throw new BadRequestErrorException("decisionDefinition.error.dmnNameNotProvided");
        }

        return decision;
    }

    private DecisionDefinition createDecisionDefinition(Decision decision) {
        DecisionDefinition decisionDefinition = new DecisionDefinition();
        decisionDefinition.setDmnDecisionDefinitionId(decision.getId());
        decisionDefinition.setName(decision.getName());

        return decisionDefinitionRepository.save(decisionDefinition);
    }

    private DecisionDefinition updateDecisionDefinition(Decision decision) {
        DecisionDefinition decisionDefinition = decisionDefinitionRepository.findByDmnDecisionDefinitionId(decision.getId()).orElseThrow();
        decisionDefinition.setName(decision.getName());

        return decisionDefinitionRepository.save(decisionDefinition);
    }

    /**
     * Get all the decisionDefinitions.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<DecisionDefinitionDTO> findAll() {
        log.debug("Request to get all DecisionDefinitions");
        return decisionDefinitionRepository
            .findAll()
            .stream()
            .map(decisionDefinitionMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one decisionDefinition by id.
     *
     * @param idOrDmnDecisionDefinitionId the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DecisionDefinitionDTO> findByIdOrDmnDecisionDefinitionId(String idOrDmnDecisionDefinitionId) {
        log.debug("Request to get DecisionDefinition : {}", idOrDmnDecisionDefinitionId);
        if (StringUtils.isNumeric(idOrDmnDecisionDefinitionId)) {
            return decisionDefinitionRepository.findById(Long.parseLong(idOrDmnDecisionDefinitionId)).map(decisionDefinitionMapper::toDto);
        }
        return decisionDefinitionRepository.findByDmnDecisionDefinitionId(idOrDmnDecisionDefinitionId).map(decisionDefinitionMapper::toDto);
    }

    /**
     * Delete the decisionDefinition by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete DecisionDefinition : {}", id);
        decisionDefinitionRepository.deleteById(id);
    }
}
