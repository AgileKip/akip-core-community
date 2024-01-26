package org.akip.repository;

import org.akip.domain.TaskDefinition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskDefinitionRepository extends JpaRepository<TaskDefinition, Long> {

    public Optional<TaskDefinition> findByBpmnProcessDefinitionIdAndTaskId(String bpmnProcessDefiinitionId, String taskId);

    public List<TaskDefinition> findByBpmnProcessDefinitionId(String bpmnProcessDefiinitionId);
}
