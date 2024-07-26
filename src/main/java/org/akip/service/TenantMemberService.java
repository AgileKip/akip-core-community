package org.akip.service;

import org.akip.domain.TenantMember;
import org.akip.repository.TenantMemberRepository;
import org.akip.service.dto.TenantDTO;
import org.akip.service.dto.TenantMemberDTO;
import org.akip.service.mapper.TenantMemberMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link TenantMember}.
 */
@Service
@Transactional
public class TenantMemberService {

    private final Logger log = LoggerFactory.getLogger(TenantMemberService.class);

    private final TenantMemberRepository tenantMemberRepository;

    private final TenantMemberMapper tenantMemberMapper;

    public TenantMemberService(TenantMemberRepository tenantMemberRepository, TenantMemberMapper tenantMemberMapper) {
        this.tenantMemberRepository = tenantMemberRepository;
        this.tenantMemberMapper = tenantMemberMapper;
    }

    @Transactional(readOnly = true)
    public List<TenantMemberDTO> getTenantMembers(Long tenantId) {
        log.debug("Request to get TenantMember for Tenant {}", tenantId);
        return tenantMemberRepository
                .findByTenantId(tenantId)
                .stream()
                .map(tenantMemberMapper::toDto)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    public TenantMemberDTO save(Long tenantId, TenantMemberDTO tenantMemberDTO) {
        log.debug("Request to save TentantMember : {}{}", tenantId, tenantMemberDTO.getUsername());
        tenantMemberDTO.setTenant(new TenantDTO(tenantId));
        TenantMember tenantMember = tenantMemberMapper.toEntity(tenantMemberDTO);
        tenantMember = tenantMemberRepository.save(tenantMember);
        return tenantMemberMapper.toDto(tenantMember);
    }

    public List<String> getTenantRolesByUsername(String username){
        List<String> tenantRoles = new ArrayList<>();
        tenantRoles.addAll(tenantMemberRepository.findTenantRolesWithDefinitionIdPrefixByUsername(username));
        tenantRoles.addAll(tenantMemberRepository.findTenantWildcardPrefixByUsername(username));
        return tenantRoles;
    }

    public void delete(Long tenantId, Long tenantMemberId) {
        log.debug("Request to delete TenantMember : {}", tenantId, tenantMemberId);
        tenantMemberRepository.deleteById(tenantMemberId);
    }
}
