package org.akip.service;

import org.akip.domain.Tenant;
import org.akip.domain.enumeration.StatusProcessDeployment;
import org.akip.repository.TenantRepository;
import org.akip.service.dto.TenantDTO;
import org.akip.service.mapper.TenantMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Tenant}.
 */
@Service
@Transactional
public class TenantService {

    private final Logger log = LoggerFactory.getLogger(TenantService.class);

    private final TenantRepository tenantRepository;

    private final TenantMapper tenantMapper;

    public TenantService(TenantRepository tenantRepository, TenantMapper tenantMapper) {
        this.tenantRepository = tenantRepository;
        this.tenantMapper = tenantMapper;
    }

    /**
     * Save a tenant.
     *
     * @param tenantDTO the entity to save.
     * @return the persisted entity.
     */
    public TenantDTO save(TenantDTO tenantDTO) {
        log.debug("Request to save Tenant : {}", tenantDTO);
        Tenant tenant = tenantMapper.toEntity(tenantDTO);
        tenant = tenantRepository.save(tenant);
        return tenantMapper.toDto(tenant);
    }

    /**
     * Get all the tenants.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<TenantDTO> findAll() {
        log.debug("Request to get all Tenants");
        return tenantRepository.findAll().stream().map(tenantMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one tenant by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TenantDTO> findOne(Long id) {
        log.debug("Request to get Tenant : {}", id);
        return tenantRepository.findById(id).map(tenantMapper::toDto);
    }

    /**
     * Delete the tenant by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Tenant : {}", id);
        tenantRepository.deleteById(id);
    }

    public List<TenantDTO> findByProcessDefinitionAndDeploymentActive(String bpmnProcessDefinitionId) {
        return tenantRepository
                .findByProcessDefinitionAndStatus(bpmnProcessDefinitionId, StatusProcessDeployment.ACTIVE)
                .stream().map(tenantMapper::toDto)
                .collect(Collectors.toCollection(LinkedList::new));
    }
}
