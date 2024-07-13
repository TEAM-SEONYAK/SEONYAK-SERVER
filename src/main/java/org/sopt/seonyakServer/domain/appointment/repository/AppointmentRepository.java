package org.sopt.seonyakServer.domain.appointment.repository;

import org.sopt.seonyakServer.domain.appointment.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
}
