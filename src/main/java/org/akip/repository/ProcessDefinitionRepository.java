package org.akip.repository;

import org.akip.domain.ProcessDefinition;
import org.springframework.data.jpa.repository.JpaRepository;
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

}
