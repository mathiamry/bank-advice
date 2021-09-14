package com.baamtu.atelier.bank.repository;

import com.baamtu.atelier.bank.domain.Appointment;
import java.util.List;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Appointment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    @Query("SELECT appointment FROM Appointment appointment WHERE appointment.manager.id=?1")
    List<Appointment> findAllByManagerUser(Long manager_id);

    @Query("SELECT appointment FROM Appointment appointment WHERE appointment.advisor.id=?1")
    List<Appointment> findAllByAdvisorUser(Long advisor_id);
}
