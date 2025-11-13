package org.akip.repository;

import org.akip.domain.Tenant;
import org.akip.domain.enumeration.StatusProcessDeployment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data SQL repository for the Tenant entity.
 */
@Repository
public interface TenantRepository extends JpaRepository<Tenant, Long> {

    @Query("select pd.tenant from ProcessDeployment pd where pd.processDefinition.bpmnProcessDefinitionId = ?1 and pd.status = ?2")
    List<Tenant> findByProcessDefinitionAndStatus(String bpmnProcessDefinitionId, StatusProcessDeployment status);

}
