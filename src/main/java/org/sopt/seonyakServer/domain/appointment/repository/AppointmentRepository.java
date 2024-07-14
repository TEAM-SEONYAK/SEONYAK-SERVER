package org.sopt.seonyakServer.domain.appointment.repository;

import java.util.Optional;
import org.sopt.seonyakServer.domain.appointment.model.Appointment;
import org.sopt.seonyakServer.global.exception.enums.ErrorType;
import org.sopt.seonyakServer.global.exception.model.CustomException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    Optional<Appointment> findById(Long appointmentId);

    default Appointment findAppointmentByIdOrThrow(Long appointmentId) {
        return findById(appointmentId)
                .orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND_APPOINTMENT_ERROR));
    }

    @Query("SELECT a.member.id "
            + "FROM Appointment a "
            + "WHERE a.id = :appointmentId")
    Long findMemberIdById(@Param("appointmentId") Long appointmentId);

    @Query("SELECT a.senior.member.id "
            + "FROM Appointment a "
            + "WHERE a.id = :appointmentId")
    Long findSeniorIdById(@Param("appointmentId") Long appointmentId);
}
