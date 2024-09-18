package org.akip.service;

import org.akip.domain.ProcessMember;
import org.akip.repository.ProcessDefinitionRepository;
import org.akip.repository.ProcessMemberRepository;
import org.akip.service.dto.ProcessMemberDTO;
import org.akip.service.mapper.ProcessMemberMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link ProcessMember}.
 */
@Service
@Transactional
public class ProcessMemberService {

    private final Logger log = LoggerFactory.getLogger(ProcessMemberService.class);

    private final ProcessMemberRepository processMemberRepository;

    private final ProcessDefinitionRepository processDefinitionRepository;

    private final ProcessMemberMapper processMemberMapper;

    public ProcessMemberService(ProcessMemberRepository processMemberRepository, ProcessDefinitionRepository processDefinitionRepository, ProcessMemberMapper processMemberMapper) {
        this.processMemberRepository = processMemberRepository;
        this.processDefinitionRepository = processDefinitionRepository;
        this.processMemberMapper = processMemberMapper;
    }

    @Transactional(readOnly = true)
    public List<ProcessMemberDTO> getProcessMembers(Long processId) {
        log.debug("Request to get ProcessMember for Process {}", processId);
        return processMemberRepository
                .findByProcessDefinitionId(processId)
                .stream()
                .map(processMemberMapper::toDto)
                .collect(Collectors.toCollection(LinkedList::new));
    }


    public ProcessMemberDTO save(Long processId, ProcessMemberDTO processMemberDTO) {
        log.debug("Request to save ProcessMember : {}{}", processId, processMemberDTO.getUsername());
        processMemberDTO.setProcessDefinition(processDefinitionRepository.findById(processId).get());
        ProcessMember processMember = processMemberMapper.toEntity(processMemberDTO);
        ProcessMember processMemberToSaved = processMemberRepository.save(processMember);
        return processMemberMapper.toDto(processMemberToSaved);
    }

    public List<String> getProcessRolesByUsername(String username) {
        List<String> processRoles = new ArrayList<>();
        processRoles.addAll(processMemberRepository.findProcessRolesWithDefinitionIdPrefixByUsername(username));
        processRoles.addAll(processMemberRepository.findProcessWildcardPrefixByUsername(username));
        return processRoles;
    }

    public void delete(Long ProcessId, Long ProcessMemberId) {
        log.debug("Request to delete ProcessMember : {}", ProcessId, ProcessMemberId);
        processMemberRepository.deleteById(ProcessMemberId);
    }
}
