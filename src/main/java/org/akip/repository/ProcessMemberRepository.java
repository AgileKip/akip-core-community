package org.akip.repository;

import org.akip.domain.ProcessMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProcessMemberRepository extends JpaRepository<ProcessMember, Long> {

    List<ProcessMember> findByProcessDefinitionId(Long id);

    @Query("SELECT CONCAT(pm.processDefinition.bpmnProcessDefinitionId, '.', pr.name) FROM ProcessMember pm JOIN pm.processRoles pr WHERE pm.username = ?1")
    List<String> findProcessRolesWithDefinitionIdPrefixByUsername(String username);

    @Query("SELECT CONCAT(pm.processDefinition.bpmnProcessDefinitionId, '.*') FROM ProcessMember pm WHERE pm.username = ?1")
    List<String> findProcessWildcardPrefixByUsername (String username);

}