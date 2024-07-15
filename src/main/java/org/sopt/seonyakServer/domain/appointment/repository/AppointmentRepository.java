package org.sopt.seonyakServer.domain.appointment.repository;

import java.util.Optional;
import org.sopt.seonyakServer.domain.appointment.model.Appointment;
import org.sopt.seonyakServer.global.exception.enums.ErrorType;
import org.sopt.seonyakServer.global.exception.model.CustomException;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    Optional<Appointment> findAppointmentById(Long id);

    default Appointment findAppointmentByIdOrThrow(Long id) {
        return findAppointmentById(id)
                .orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND_APPOINTMENT_ERROR));
    }
}
