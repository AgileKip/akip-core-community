package org.akip.dashboard.repository;

import org.akip.dashboard.domain.DashboardConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data SQL repository for the DashboardConfig entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DashboardConfigRepository extends JpaRepository<DashboardConfig, Long> {
    List<DashboardConfig> findByProcessDefinitionId(Long bpmnProcessDefinitionId);
}
