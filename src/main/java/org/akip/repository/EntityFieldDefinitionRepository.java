package org.akip.repository;

import org.akip.domain.EntityFieldDefinition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data SQL repository for the EntityFieldDefinition entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EntityFieldDefinitionRepository extends JpaRepository<EntityFieldDefinition, Long> {

    List<EntityFieldDefinition> findEntityFieldDefinitionsByDefinitionName(String domainEntityName);
}
