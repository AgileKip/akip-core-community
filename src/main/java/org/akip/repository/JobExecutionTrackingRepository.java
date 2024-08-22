package org.akip.repository;

import org.akip.domain.JobExecutionTracking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data SQL repository for the JobExecutionTracking entity.
 */
@SuppressWarnings("unused")
@Repository
public interface JobExecutionTrackingRepository extends JpaRepository<JobExecutionTracking, Long> {
    Optional<JobExecutionTracking> findByIdentifier(String identifier);
}
