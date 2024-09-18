package org.akip.service;

import org.akip.domain.ProcessRole;
import org.akip.repository.ProcessDefinitionRepository;
import org.akip.repository.ProcessRoleRepository;
import org.akip.service.dto.ProcessRoleDTO;
import org.akip.service.mapper.ProcessRoleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link ProcessRole}.
 */
@Service
@Transactional
public class ProcessRoleService {

    private final Logger log = LoggerFactory.getLogger(ProcessRoleService.class);

    private final ProcessRoleRepository processRoleRepository;

    private final ProcessDefinitionRepository processDefinitionRepository;

    private final ProcessRoleMapper processRoleMapper;

    public ProcessRoleService(ProcessRoleRepository processRoleRepository, ProcessDefinitionRepository processDefinitionRepository, ProcessRoleMapper processRoleMapper) {
        this.processRoleRepository = processRoleRepository;
        this.processDefinitionRepository = processDefinitionRepository;
        this.processRoleMapper = processRoleMapper;
    }

    @Transactional(readOnly = true)
    public List<ProcessRoleDTO> getProcessRolesByProcessDefinitionId(Long processDefinitionId) {
        log.debug("Request to get ProcessRole for Process {}", processDefinitionId);
        return processRoleRepository
                .findByProcessDefinitionId(processDefinitionId)
                .stream()
                .map(processRoleMapper::toDto)
                .collect(Collectors.toCollection(LinkedList::new));
    }


    public ProcessRoleDTO save(Long processDefinitionId, ProcessRoleDTO processRoleDTO) {
        log.debug("Request to save ProcessRole : {}{}", processDefinitionId, processRoleDTO.getName());
        processRoleDTO.setProcessDefinition(processDefinitionRepository.findById(processDefinitionId).get());
        ProcessRole processRole = processRoleMapper.toEntity(processRoleDTO);
        ProcessRole processRoleToSaved = processRoleRepository.save(processRole);
        return processRoleMapper.toDto(processRoleToSaved);
    }

    public void delete(Long processDefinitionId, Long processRoleId) {
        log.debug("Request to delete ProcessRole : {}{}", processDefinitionId, processRoleId);
        processRoleRepository.deleteById(processRoleId);
    }
}
