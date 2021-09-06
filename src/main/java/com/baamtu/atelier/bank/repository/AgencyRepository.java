package com.baamtu.atelier.bank.repository;

import com.baamtu.atelier.bank.domain.Agency;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Agency entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AgencyRepository extends JpaRepository<Agency, Long> {}
