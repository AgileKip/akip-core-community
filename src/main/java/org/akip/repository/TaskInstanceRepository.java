package org.akip.repository;

import org.akip.domain.TaskInstance;
import org.akip.domain.enumeration.StatusTaskInstance;
import org.akip.domain.enumeration.TypeTaskInstance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * Spring Data SQL repository for the TaskInstance entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TaskInstanceRepository extends JpaRepository<TaskInstance, Long> {
    Optional<TaskInstance> findByTaskId(String taskId);

    @Query("from TaskInstance where cast(processDefinition.id as char) = ?1 or processDefinition.bpmnProcessDefinitionId = ?1")
    List<TaskInstance> findByProcessDefinitionIdOrBpmnProcessDefinitionId(String idOrBpmnProcessDefinitionId);

    List<TaskInstance> findByTaskDefinitionKeyAndProcessInstanceIdAndProcessInstanceAccessTokenNumberOrderByIdDesc(String taskDefinitionKey, Long processInstanceId, String accessTokenNumber);

    List<TaskInstance> findByProcessInstanceId(Long processInstanceId);

    List<TaskInstance> findByAssigneeAndStatusIn(String assignee, List<StatusTaskInstance> statusTaskInstances);

    List<TaskInstance> findByProcessDefinitionIdAndTypeAndCreateDateBetween(Long processDefinitionId, TypeTaskInstance type, Instant startDate, Instant endDate);

    List<TaskInstance> findByStatus(StatusTaskInstance status);
}
