package org.akip.repository;

import org.akip.domain.ProcessInstance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Spring Data SQL repository for the ProcessInstance entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProcessInstanceRepository extends JpaRepository<ProcessInstance, Long> {
    Optional<ProcessInstance> findByCamundaProcessInstanceId(String camundaProcessInstanceId);

    List<ProcessInstance> findByProcessDefinitionId(Long processDefinitionId);

    List<ProcessInstance> findByProcessDefinitionIdAndStartDateBetween(Long processDefinitionId, LocalDateTime from, LocalDateTime to);

    @Modifying
    @Query("update ProcessInstance set data = ?1 where id = ?2")
    void updateDataById(String dataAsString, Long id);

    @Modifying
    @Query("update ProcessInstance set accessTokenNumber = ?1, accessTokenExpirationDate = ?2 where id = ?3")
    void updateAccessTokenInfoById(String accessTokenNumber, LocalDate localDate, Long id);
}
