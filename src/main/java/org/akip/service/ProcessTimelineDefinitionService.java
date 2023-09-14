package org.akip.service;

import org.akip.domain.ProcessDefinition;
import org.akip.domain.ProcessTimelineDefinition;
import org.akip.exception.BadRequestErrorException;
import org.akip.repository.ProcessDefinitionRepository;
import org.akip.repository.ProcessDeploymentRepository;
import org.akip.repository.ProcessTimelineDefinitionRepository;
import org.akip.service.dto.ProcessTimelineDefinitionDTO;
import org.akip.service.mapper.ProcessTimelineDefinitionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProcessTimelineDefinitionService {

    @Autowired
    private ProcessDefinitionRepository processDefinitionRepository;

    @Autowired
    private ProcessDeploymentRepository processDeploymentRepository;

    @Autowired
    private ProcessTimelineDefinitionRepository processTimelineDefinitionRepository;

    private final ProcessTimelineDefinitionMapper processTimelineDefinitionMapper;

    public ProcessTimelineDefinitionService(ProcessTimelineDefinitionMapper processTimelineDefinitionMapper) {
        this.processTimelineDefinitionMapper = processTimelineDefinitionMapper;
    }

    public List<ProcessTimelineDefinitionDTO> getByBpmnProcessDefinitionId(String bpmnProcessDefinitionId) {
        List<ProcessTimelineDefinition> processTimelineDefinitions = processTimelineDefinitionRepository.findByProcessDefinitionBpmnProcessDefinitionId(bpmnProcessDefinitionId);

        if (processTimelineDefinitions.isEmpty()) {
            throw new BadRequestErrorException("Timeline not defined for the process");
        }

        return processTimelineDefinitionMapper.toDto(processTimelineDefinitions);
    }

    public ProcessTimelineDefinitionDTO save(
            String bpmnProcessDefinitionId,
            ProcessTimelineDefinitionDTO processTimelineDefinitionDTO
    ) {
        AtomicInteger stepCount = new AtomicInteger();
        Optional<ProcessDefinition> processDefinition = processDefinitionRepository.findByBpmnProcessDefinitionId(
                bpmnProcessDefinitionId
        );
        ProcessTimelineDefinition processTimelineDefinition = processTimelineDefinitionMapper.toEntity(processTimelineDefinitionDTO);
        processTimelineDefinition
                .getItems()
                .forEach(
                        item -> {
                            item.setProcessTimelineDefinition(processTimelineDefinition);
                            item.setStep(stepCount.getAndIncrement());
                        }
                );
        processTimelineDefinition.setProcessDefinition(processDefinition.get());
        return processTimelineDefinitionMapper.toDto(processTimelineDefinitionRepository.save(processTimelineDefinition));
    }

    @Transactional(readOnly = true)
    public List<ProcessTimelineDefinitionDTO> findByProcessDefinitionBpmnProcessDefinitionId(String bpmnProcessDefinitionId) {
        return processTimelineDefinitionRepository
                .findByProcessDefinitionBpmnProcessDefinitionId(bpmnProcessDefinitionId)
                .stream()
                .map(processTimelineDefinitionMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<ProcessTimelineDefinitionDTO> findOne(Long processTimelineDefinitionId) {
        return processTimelineDefinitionRepository.findById(processTimelineDefinitionId).map(processTimelineDefinitionMapper::toDto);
    }

    public void delete(Long processTimelineDefinitionId) {
        processTimelineDefinitionRepository.deleteById(processTimelineDefinitionId);
    }
}
