package org.akip.repository;

import org.akip.domain.ProcessDeployment;
import org.akip.domain.enumeration.StatusProcessDeployment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Spring Data SQL repository for the ProcessDeployment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProcessDeploymentRepository extends JpaRepository<ProcessDeployment, Long> {
    List<ProcessDeployment> findByProcessDefinitionId(Long processDefinitionId);

    Optional<ProcessDeployment> findByCamundaProcessDefinitionId(String camundaProcessDefinitionId);

    @Query(
        "from ProcessDeployment where processDefinition.id = ?1 and status = org.akip.domain.enumeration.StatusProcessDeployment.ACTIVE"
    )
    Optional<ProcessDeployment> findByProcessDefinitionIdAndStatusIsActive(Long processDefinitionId);

    @Modifying
    @Query("update ProcessDeployment set status = ?1 where id = ?2")
    void updateStatusById(StatusProcessDeployment status, Long id);

    @Modifying
    @Query("update ProcessDeployment set activationDate = ?1 where id = ?2")
    void updateActivationDateById(LocalDateTime localDateTime, Long id);

    @Modifying
    @Query("update ProcessDeployment set inactivationDate = ?1 where id = ?2")
    void updateInactivationDateById(LocalDateTime localDateTime, Long id);
}
