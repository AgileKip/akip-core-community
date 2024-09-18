package org.akip.service;

import org.akip.domain.TenantRole;
import org.akip.repository.TenantRepository;
import org.akip.repository.TenantRoleRepository;
import org.akip.service.dto.TenantRoleDTO;
import org.akip.service.mapper.TenantRoleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link TenantRole}.
 */
@Service
@Transactional
public class TenantRoleService {

    private final Logger log = LoggerFactory.getLogger(TenantRoleService.class);

    private final TenantRoleRepository tenantRoleRepository;

    private final TenantRoleMapper tenantRoleMapper;

    private final TenantRepository tenantRepository;

    public TenantRoleService(TenantRoleRepository tenantRoleRepository, TenantRoleMapper tenantRoleMapper, TenantRepository tenantRepository) {
        this.tenantRoleRepository = tenantRoleRepository;
        this.tenantRoleMapper = tenantRoleMapper;
        this.tenantRepository = tenantRepository;
    }

    @Transactional(readOnly = true)
    public List<TenantRoleDTO> getTenantRoles(Long tenantId) {
        log.debug("Request to get TenantRole for Tenant {}", tenantId);
        return tenantRoleRepository
                .findByTenantId(tenantId)
                .stream()
                .map(tenantRoleMapper::toDto)
                .collect(Collectors.toCollection(LinkedList::new));
    }


    public TenantRoleDTO save(Long tenantId, TenantRoleDTO tenantRoleDTO) {
        log.debug("Request to save TentantRole : {}{}", tenantId, tenantRoleDTO.getName());
        tenantRoleDTO.setTenant(tenantRepository.findById(tenantId).get());
        TenantRole tenantRole = tenantRoleMapper.toEntity(tenantRoleDTO);
        tenantRole = tenantRoleRepository.save(tenantRole);
        return tenantRoleMapper.toDto(tenantRole);
    }

    public void delete(Long tenantId, Long tenantRoleId) {
        log.debug("Request to delete TenantRole : {}", tenantId, tenantRoleId);
        tenantRoleRepository.deleteById(tenantRoleId);
    }
}
