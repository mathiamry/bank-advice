package com.baamtu.atelier.bank.repository;

import com.baamtu.atelier.bank.domain.Manager;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Manager entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ManagerRepository extends JpaRepository<Manager, Long> {
    @Query("select manager from Manager manager where manager.user.login = ?#{principal.username}")
    Manager findByUserIsCurrentUser();
}
