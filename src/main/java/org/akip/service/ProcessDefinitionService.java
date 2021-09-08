package org.akip.service;

import org.akip.domain.ProcessDefinition;
import org.akip.domain.enumeration.StatusProcessDefinition;
import org.akip.repository.ProcessDefinitionRepository;
import org.akip.service.dto.ProcessDefinitionDTO;
import org.akip.service.mapper.ProcessDefinitionMapper;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.Process;
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
 * Service Implementation for managing {@link org.akip.domain.ProcessDefinition}.
 */
@Service
@Transactional
public class ProcessDefinitionService {

    private static final String ENTITY_NAME = "processDefinition";

    private final Logger log = LoggerFactory.getLogger(ProcessDefinitionService.class);

    private final ProcessDefinitionRepository processDefinitionRepository;

    private final ProcessDefinitionMapper processDefinitionMapper;

    public ProcessDefinitionService(
        ProcessDefinitionRepository processDefinitionRepository,
        ProcessDefinitionMapper processDefinitionMapper
    ) {
        this.processDefinitionRepository = processDefinitionRepository;
        this.processDefinitionMapper = processDefinitionMapper;
    }

    public ProcessDefinition createOrUpdateProcessDefinition(BpmnModelInstance bpmnModelInstance) {
        Process process = extracAndValidProcessFromModel(bpmnModelInstance);
        Optional<ProcessDefinition> optionalProcessDefinition = processDefinitionRepository.findByBpmnProcessDefinitionId(process.getId());

        if (optionalProcessDefinition.isPresent()) {
            return updateProcessDefinition(process);
        }

        return createProcessDefinition(process);
    }

    private Process extracAndValidProcessFromModel(BpmnModelInstance modelInstance) {
        ModelElementType processType = modelInstance.getModel().getType(Process.class);
        Process process = (Process) modelInstance.getModelElementsByType(processType).iterator().next();

        if (!process.isExecutable()) {
            throw new RuntimeException("Model is not executable");
        }

        if (StringUtils.isBlank(process.getName())) {
            throw new RuntimeException("Process name is not provided");
        }

        return process;
    }

    private ProcessDefinition createProcessDefinition(Process process) {
        ProcessDefinition processDefinition = new ProcessDefinition();
        processDefinition.setBpmnProcessDefinitionId(process.getId());
        processDefinition.setName(process.getName());
        processDefinition.setCanBeManuallyStarted(process.isCamundaStartableInTasklist());
        processDefinition.setStatus(StatusProcessDefinition.ACTIVE);
        if (!process.getDocumentations().isEmpty()) {
            processDefinition.setDescription(process.getDocumentations().iterator().next().getRawTextContent());
        }

        return processDefinitionRepository.save(processDefinition);
    }

    private ProcessDefinition updateProcessDefinition(Process process) {
        ProcessDefinition processDefinition = processDefinitionRepository.findByBpmnProcessDefinitionId(process.getId()).orElseThrow();
        processDefinition.setName(process.getName());
        processDefinition.setCanBeManuallyStarted(process.isCamundaStartableInTasklist());
        processDefinition.setStatus(StatusProcessDefinition.ACTIVE);
        if (!process.getDocumentations().isEmpty()) {
            processDefinition.setDescription(process.getDocumentations().iterator().next().getRawTextContent());
        }

        return processDefinitionRepository.save(processDefinition);
    }

    /**
     * Get all the processDefinitions.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ProcessDefinitionDTO> findAll() {
        log.debug("Request to get all ProcessDefinitions");
        return processDefinitionRepository
            .findAll()
            .stream()
            .map(processDefinitionMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one processDefinition by id.
     *
     * @param idOrBpmnProcessDefinitionId the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProcessDefinitionDTO> findByIdOrBpmnProcessDefinitionId(String idOrBpmnProcessDefinitionId) {
        log.debug("Request to get ProcessDefinition : {}", idOrBpmnProcessDefinitionId);
        if (StringUtils.isNumeric(idOrBpmnProcessDefinitionId)) {
            return processDefinitionRepository.findById(Long.parseLong(idOrBpmnProcessDefinitionId)).map(processDefinitionMapper::toDto);
        }
        return processDefinitionRepository.findByBpmnProcessDefinitionId(idOrBpmnProcessDefinitionId).map(processDefinitionMapper::toDto);
    }

    /**
     * Delete the processDefinition by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ProcessDefinition : {}", id);
        processDefinitionRepository.deleteById(id);
    }
}
