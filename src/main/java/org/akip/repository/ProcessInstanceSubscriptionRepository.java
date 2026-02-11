package org.akip.repository;


import org.akip.domain.ProcessInstanceSubscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


/**
 * Spring Data SQL repository for the ProcessInstanceSubscription entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProcessInstanceSubscriptionRepository extends JpaRepository<ProcessInstanceSubscription, Long> {
    Optional<ProcessInstanceSubscription> findBySubscriberIdAndProcessInstanceId(String subscriberId, Long processInstanceId);

    List<ProcessInstanceSubscription> findAllByProcessInstanceId(Long processInstanceId);

    List<ProcessInstanceSubscription> findBySubscriberId(String subscriberId);
}
