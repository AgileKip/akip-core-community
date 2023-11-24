package org.akip.repository;

import org.akip.domain.AkipPromptConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data SQL repository for the AkipPromptConfiguration entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AkipPromptConfigurationRepository extends JpaRepository<AkipPromptConfiguration, Long> {

    Optional<AkipPromptConfiguration> findByName(String name);

}
