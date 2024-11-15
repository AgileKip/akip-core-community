package org.akip.repository;

import org.akip.domain.AkipEmailConnectorConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data SQL repository for the EmailActionConfig entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AkipEmailConnectorConfigRepository extends JpaRepository<AkipEmailConnectorConfig, Long> {

    Optional<AkipEmailConnectorConfig> findByName(String name);

}
