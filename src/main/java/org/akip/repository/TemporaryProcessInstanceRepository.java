package org.akip.repository;

import org.akip.domain.ProcessInstance;
import org.akip.domain.TemporaryProcessInstance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the TemporaryProcessInstance entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TemporaryProcessInstanceRepository extends JpaRepository<TemporaryProcessInstance, Long> {
    @Modifying
    @Query("update TemporaryProcessInstance t set t.processInstance.id = :processInstanceId where t.id = :temporaryProcessInstance")
    void updateProcessInstanceIdById(@Param("processInstanceId") Long processInstanceId, @Param("temporaryProcessInstance") Long temporaryProcessInstance);
}
