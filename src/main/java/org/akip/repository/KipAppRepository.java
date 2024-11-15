package org.akip.repository;

import org.akip.domain.KipApp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the KipApp entity.
 */
@SuppressWarnings("unused")
@Repository
public interface KipAppRepository extends JpaRepository<KipApp, Long> {}
