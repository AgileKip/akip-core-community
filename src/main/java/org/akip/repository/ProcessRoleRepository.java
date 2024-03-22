package org.akip.repository;

import org.akip.domain.ProcessRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProcessRoleRepository extends JpaRepository<ProcessRole, Long> {

    List<ProcessRole> findByProcessDefinitionId(Long id);
}
