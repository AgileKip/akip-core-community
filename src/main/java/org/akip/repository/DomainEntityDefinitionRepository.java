package org.akip.repository;

import org.akip.domain.DomainEntityDefinition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data JPA repository for the DomainEntityDefinition entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DomainEntityDefinitionRepository extends JpaRepository<DomainEntityDefinition, Long> {

    Optional<DomainEntityDefinition> findByIdOrName(Long id, String name);

}
