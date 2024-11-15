package org.akip.service;

import org.akip.domain.DecisionDefinition;
import org.akip.domain.DecisionDeployment;
import org.akip.domain.enumeration.StatusDecisionDeployment;
import org.akip.repository.DecisionDeploymentRepository;
import org.akip.service.dto.DecisionDefinitionDTO;
import org.akip.service.dto.DecisionDeploymentDTO;
import org.akip.service.dto.DecisionDeploymentDmnModelDTO;
import org.akip.service.mapper.DecisionDeploymentMapper;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.model.dmn.Dmn;
import org.camunda.bpm.model.dmn.DmnModelInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.io.ByteArrayInputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
@Service
public class DecisionDeploymentService {

    private final Logger log = LoggerFactory.getLogger(DecisionDeploymentService.class);

    private final DecisionDefinitionService decisionDefinitionService;

    private final DecisionDeploymentRepository decisionDeploymentRepository;

    private final RepositoryService repositoryService;

    private final DecisionDeploymentMapper decisionDeploymentMapper;

    public DecisionDeploymentService(
        DecisionDefinitionService decisionDefinitionService,
        DecisionDeploymentRepository decisionDeploymentRepository,
        RepositoryService repositoryService,
        DecisionDeploymentMapper decisionDeploymentMapper
    ) {
        this.decisionDefinitionService = decisionDefinitionService;
        this.decisionDeploymentRepository = decisionDeploymentRepository;
        this.repositoryService = repositoryService;
        this.decisionDeploymentMapper = decisionDeploymentMapper;
    }

    public DecisionDeploymentDTO deploy(DecisionDeploymentDTO decisionDeploymentDTO) {
        DmnModelInstance dmnModelInstance = Dmn.readModelFromStream(new ByteArrayInputStream(decisionDeploymentDTO.getSpecificationFile()));
        DecisionDefinition decisionDefinition = decisionDefinitionService.createOrUpdateDecisionDefinition(dmnModelInstance);

        org.camunda.bpm.engine.repository.Deployment camundaDeployment = deployInCamunda(
            decisionDeploymentDTO,
            decisionDefinition,
            dmnModelInstance
        );

        org.camunda.bpm.engine.repository.DecisionDefinition camundaDecisionDefinition = repositoryService
            .createDecisionDefinitionQuery()
            .deploymentId(camundaDeployment.getId())
            .singleResult();

        DecisionDeployment decisionDeployment = decisionDeploymentMapper.toEntity(decisionDeploymentDTO);
        decisionDeployment.setDecisionDefinition(decisionDefinition);
        decisionDeployment.setCamundaDeploymentId(camundaDeployment.getId());
        decisionDeployment.setCamundaDecisionDefinitionId(camundaDecisionDefinition.getId());
        decisionDeployment.setStatus(StatusDecisionDeployment.ACTIVE);
        decisionDeployment.setDeployDate(LocalDateTime.now());
        decisionDeployment.setActivationDate(decisionDeployment.getDeployDate());

        inactivePreviousDecisionDeployments(decisionDeployment);

        return decisionDeploymentMapper.toDto(decisionDeploymentRepository.save(decisionDeployment));
    }

    public Optional<DecisionDeploymentDTO> findOne(Long id) {
        return decisionDeploymentRepository.findById(id).map(decisionDeploymentMapper::toDto);
    }

    public Optional<DecisionDeploymentDmnModelDTO> findDmnModel(Long id) {
        return decisionDeploymentRepository.findById(id).map(decisionDeploymentMapper::toDmnModelDto);
    }

    public void active(Long decisionDeploymentId) {
        DecisionDeployment decisionDeployment = decisionDeploymentRepository.findById(decisionDeploymentId).orElseThrow();
        inactivePreviousDecisionDeployments(decisionDeployment);
        decisionDeploymentRepository.updateStatusById(StatusDecisionDeployment.ACTIVE, decisionDeploymentId);
        decisionDeploymentRepository.updateActivationDateById(LocalDateTime.now(), decisionDeploymentId);
    }

    public void inactive(Long decisionDeploymentId) {
        decisionDeploymentRepository.updateStatusById(StatusDecisionDeployment.INACTIVE, decisionDeploymentId);
        decisionDeploymentRepository.updateInactivationDateById(LocalDateTime.now(), decisionDeploymentId);
    }

    public List<DecisionDeploymentDTO> findByDecisionDefinition(String idOrDmnDecisionDefinitionId) {
        DecisionDefinitionDTO decisionDefinitionDTO = decisionDefinitionService
            .findByIdOrDmnDecisionDefinitionId(idOrDmnDecisionDefinitionId)
            .orElseThrow();
        return decisionDeploymentRepository
            .findByDecisionDefinitionId(decisionDefinitionDTO.getId())
            .stream()
            .map(decisionDeploymentMapper::toDto)
            .collect(Collectors.toList());
    }

    public List<DecisionDeploymentDTO> findActiveByDecisionDefinition(String idOrDmnDecisionDefinitionId) {
        DecisionDefinitionDTO decisionDefinitionDTO = decisionDefinitionService
            .findByIdOrDmnDecisionDefinitionId(idOrDmnDecisionDefinitionId)
            .orElseThrow();
        return decisionDeploymentRepository
            .findByDecisionDefinitionIdAndStatusIsActive(decisionDefinitionDTO.getId())
            .stream()
            .map(decisionDeploymentMapper::toDto)
            .collect(Collectors.toList());
    }

    private org.camunda.bpm.engine.repository.Deployment deployInCamunda(
        DecisionDeploymentDTO decisionDeploymentDTO,
        DecisionDefinition decisionDefinition,
        DmnModelInstance dmnModelInstance
    ) {
        if (decisionDeploymentDTO.getTenant() == null) {
            return repositoryService
                .createDeployment()
                .addModelInstance(decisionDefinition.getDmnDecisionDefinitionId() + ".dmn", dmnModelInstance)
                .name(decisionDefinition.getDmnDecisionDefinitionId())
                .deploy();
        }

        return repositoryService
            .createDeployment()
            .addModelInstance(decisionDefinition.getDmnDecisionDefinitionId() + ".dmn", dmnModelInstance)
            .name(decisionDefinition.getDmnDecisionDefinitionId())
            .tenantId(decisionDeploymentDTO.getTenant().getIdentifier())
            .deploy();
    }

    private void inactivePreviousDecisionDeployments(DecisionDeployment decisionDeployment) {
        List<DecisionDeployment> previousDecisionDeployments = getActiveDecisionDeployment(decisionDeployment);
        previousDecisionDeployments.forEach(
            previousDecisionDeployment -> {
                inactive(previousDecisionDeployment.getId());
            }
        );
    }

    private List<DecisionDeployment> getActiveDecisionDeployment(DecisionDeployment decisionDeployment) {
        if (decisionDeployment.getTenant() == null) {
            return decisionDeploymentRepository.findByDecisionDefinitionIdAndStatusAndTenantIsNull(
                decisionDeployment.getDecisionDefinition().getId(),
                StatusDecisionDeployment.ACTIVE
            );
        }

        return decisionDeploymentRepository.findByDecisionDefinitionIdAndStatusAndTenantId(
            decisionDeployment.getDecisionDefinition().getId(),
            StatusDecisionDeployment.ACTIVE,
            decisionDeployment.getTenant().getId()
        );
    }
}
