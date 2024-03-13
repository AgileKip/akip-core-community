package org.akip.repository;

import org.akip.domain.ProcessMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ProcessMemberRepository extends JpaRepository<ProcessMember, Long> {

    List<ProcessMember> findByProcessDefinitionId(Long id);

    @Transactional
    List<ProcessMember> findProcessMembersByUser(String user);
}
