package org.akip.repository;

import org.akip.domain.DecisionDeployment;
import org.akip.domain.enumeration.StatusDecisionDeployment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
/**
 * Spring Data SQL repository for the DecisionDeployment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DecisionDeploymentRepository extends JpaRepository<DecisionDeployment, Long> {
    List<DecisionDeployment> findByDecisionDefinitionId(Long decisionDefinitionId);

    Optional<DecisionDeployment> findByCamundaDecisionDefinitionId(String camundaDecisionDefinitionId);

    List<DecisionDeployment> findByDecisionDefinitionIdAndStatusAndTenantIsNull(Long decisionDefinitionId, StatusDecisionDeployment status);

    List<DecisionDeployment> findByDecisionDefinitionIdAndStatusAndTenantId(
        Long decisionDefinitionId,
        StatusDecisionDeployment status,
        Long tenantId
    );

    @Modifying
    @Query("update DecisionDeployment set status = ?1 where id = ?2")
    void updateStatusById(StatusDecisionDeployment status, Long id);

    @Modifying
    @Query("update DecisionDeployment set activationDate = ?1 where id = ?2")
    void updateActivationDateById(LocalDateTime localDateTime, Long id);

    @Modifying
    @Query("update DecisionDeployment set inactivationDate = ?1 where id = ?2")
    void updateInactivationDateById(LocalDateTime localDateTime, Long id);

    @Query(
        "from DecisionDeployment where decisionDefinition.id = ?1 and tenant is null and status = org.akip.domain.enumeration.StatusDecisionDeployment.ACTIVE"
    )
    List<DecisionDeployment> findByDecisionDefinitionIdAndStatusIsActiveAndTenantIsNull(Long decisionDefinitionId);

    @Query(
        "from DecisionDeployment where decisionDefinition.id = ?1 and status = org.akip.domain.enumeration.StatusDecisionDeployment.ACTIVE"
    )
    List<DecisionDeployment> findByDecisionDefinitionIdAndStatusIsActive(Long decisionDefinitionId);

    @Query(
        "from DecisionDeployment where decisionDefinition.id = ?1 and tenant is not null and status = org.akip.domain.enumeration.StatusDecisionDeployment.ACTIVE"
    )
    List<DecisionDeployment> findByDecisionDefinitionIdAndStatusIsActiveAndTenantIsNotNull(Long decisionDefinitionId);

    @Query(
        "from DecisionDeployment where decisionDefinition.id = ?1 and tenant.id = ?2 and status = org.akip.domain.enumeration.StatusDecisionDeployment.ACTIVE"
    )
    Optional<DecisionDeployment> findByDecisionDefinitionIdAndStatusIsActiveAndTenantId(Long decisionDefinitionId, Long tenantId);
}
