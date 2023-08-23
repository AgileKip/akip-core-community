package org.akip.repository;

import org.akip.domain.DecisionDefinition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * Spring Data SQL repository for the DecisionDefinition entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DecisionDefinitionRepository extends JpaRepository<DecisionDefinition, Long> {
    Optional<DecisionDefinition> findByDmnDecisionDefinitionId(String dmnDecisionDefinitionId);
}
