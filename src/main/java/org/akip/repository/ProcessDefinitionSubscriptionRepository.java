package org.akip.repository;


import org.akip.domain.ProcessDefinitionSubscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data SQL repository for the ProcessDefinitionSubscription entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProcessDefinitionSubscriptionRepository extends JpaRepository<ProcessDefinitionSubscription, Long> {
    Optional<ProcessDefinitionSubscription> findBySubscriberIdAndBpmnProcessDefinitionId(
        String subscriberId,
        String bpmnProcessDefinitionId
    );
}
