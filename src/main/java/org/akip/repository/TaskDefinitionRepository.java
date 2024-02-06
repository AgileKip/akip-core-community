package org.akip.repository;

import org.akip.domain.TaskDefinition;
import org.akip.domain.TaskInstance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskDefinitionRepository extends JpaRepository<TaskDefinition, Long> {

    List<TaskDefinition> findByBpmnProcessDefinitionId(String bpmnProcessDefinitionId);

    Optional<TaskDefinition> findByBpmnProcessDefinitionIdAndTaskId(String bpmnProcessDefinitionId, String taskId);

}