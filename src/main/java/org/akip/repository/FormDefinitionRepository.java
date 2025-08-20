package org.akip.repository;

import org.akip.domain.FormDefinition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FormDefinitionRepository extends JpaRepository<FormDefinition, Long> {


    //TODO: include where clause`
    @Query("from FormDefinition fd")
    List<FormDefinition> findByProcessDefinitionId(String processDefinitionId);

    @Query("select pd.startFormDefinition from ProcessDefinition  pd where pd.id = ?1")
    Optional<FormDefinition> findStartFormByProcessDefinitionId(Long processDefinitionId);

    @Query("select td.formDefinition from TaskDefinition td where td.id = ?1")
    Optional<FormDefinition> findTaskFormByTaskDefinitionId(Long taskDefinitionId);
}
