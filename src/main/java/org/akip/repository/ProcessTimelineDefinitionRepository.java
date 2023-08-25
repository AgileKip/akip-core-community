package org.akip.repository;

import org.akip.domain.ProcessTimelineDefinition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProcessTimelineDefinitionRepository extends JpaRepository<ProcessTimelineDefinition, Long> {
    List<ProcessTimelineDefinition> findAllByProcessDefinitionBpmnProcessDefinitionId(String processDefinitionId);
}
