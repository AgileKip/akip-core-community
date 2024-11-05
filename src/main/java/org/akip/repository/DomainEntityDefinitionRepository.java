package org.akip.repository;

import org.akip.domain.DomainEntityDefinition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the DomainEntityDefinition entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DomainEntityDefinitionRepository extends JpaRepository<DomainEntityDefinition, Long> {}
