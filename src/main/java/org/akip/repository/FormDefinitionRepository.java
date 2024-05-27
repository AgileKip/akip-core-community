package org.akip.repository;

import org.akip.domain.FormDefinition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.stream.DoubleStream;

@Repository
public interface FormDefinitionRepository extends JpaRepository<FormDefinition, Long> {

    @Query("select pd.startFormDefinition from ProcessDefinition  pd where pd.id = ?1")
    Optional<FormDefinition> findByProcessDefinitionId(Long processDefinitionId);

    @Query("select td.formDefinition from TaskDefinition td where td.id = ?1")
    Optional<FormDefinition>  findByTaskDefinitionId(Long taskDefinitionId);
}
