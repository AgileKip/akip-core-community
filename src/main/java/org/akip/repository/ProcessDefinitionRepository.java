package org.akip.repository;

import org.akip.domain.DomainEntityDefinition;
import org.akip.domain.ProcessDefinition;
import org.akip.service.dto.DomainEntityDefinitionDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data SQL repository for the ProcessDefinition entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProcessDefinitionRepository extends JpaRepository<ProcessDefinition, Long> {
    Optional<ProcessDefinition> findByBpmnProcessDefinitionId(String bpmnProcessDefinitionId);

    Optional<ProcessDefinition> findByStartFormDefinitionId(Long startFormDefinitionId);

    boolean existsByName(String name);

    @Modifying
    @Query("update ProcessDefinition set domainEntityDefinition = ?1 where bpmnProcessDefinitionId = ?2")
    void updateDomainEntityDefinition(DomainEntityDefinition domainEntityDefinition, String bpmnProcessDefinitionId);

    @Modifying
    @Query("update ProcessDefinition set domainEntityDefinition = null where bpmnProcessDefinitionId = ?1")
    void removeDomainEntityDefinition(String bpmnProcessDefinitionId);
}
