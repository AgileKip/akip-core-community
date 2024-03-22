package org.akip.repository;

import org.akip.domain.Tenant;
import org.akip.domain.TenantMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data SQL repository for the TentantUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TenantMemberRepository extends JpaRepository<TenantMember, Long> {

    List<TenantMember> findByTenantId(Long tenantId);

    List<TenantMember> findTenantMembersByUsername(String username);

    @Query("select tenant from TenantMember where username = ?1")
    List<Tenant> findTenantByTenantMemberUsername(String username);

    @Query("SELECT CONCAT(tm.tenant.identifier, '.', tr.name) FROM TenantMember tm JOIN tm.tenantRoles tr WHERE tm.username = ?1")
    List<String> findTenantRolesWithDefinitionIdPrefixByUsername(String username);

    List<TenantMember> findTenantMembersByTenantId(Long id);

}
