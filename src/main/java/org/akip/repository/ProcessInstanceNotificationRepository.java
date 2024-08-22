package org.akip.repository;


import org.akip.domain.ProcessInstanceNotification;
import org.akip.domain.enumeration.ProcessInstanceNotificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data SQL repository for the ProcessInstanceNotification entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProcessInstanceNotificationRepository extends JpaRepository<ProcessInstanceNotification, Long> {

    List<ProcessInstanceNotification> findTop6BySubscriberIdOrderByIdDesc(String subscriberId);

    long countBySubscriberIdAndStatus(String subscriberId, ProcessInstanceNotificationStatus status);


}
