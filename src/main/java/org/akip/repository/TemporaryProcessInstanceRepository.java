package org.akip.repository;

import org.akip.domain.TemporaryProcessInstance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the TemporaryProcessInstance entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TemporaryProcessInstanceRepository extends JpaRepository<TemporaryProcessInstance, Long> {}
