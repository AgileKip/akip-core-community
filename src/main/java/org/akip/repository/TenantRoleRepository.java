package org.akip.repository;

import org.akip.domain.TenantRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TenantRoleRepository extends JpaRepository<TenantRole, Long> {

    List<TenantRole> findByTenantId(Long id);

}
