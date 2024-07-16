package org.sopt.seonyakServer.domain.appointment.repository;

import java.util.List;
import java.util.Optional;
import org.sopt.seonyakServer.domain.appointment.model.Appointment;
import org.sopt.seonyakServer.domain.appointment.model.AppointmentStatus;
import org.sopt.seonyakServer.global.exception.enums.ErrorType;
import org.sopt.seonyakServer.global.exception.model.CustomException;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    Optional<Appointment> findAppointmentById(Long id);

    Optional<Appointment> findAppointmentByMemberIdAndSeniorIdAndAppointmentStatusIn(Long memberId, Long seniorId,
                                                                                     List<AppointmentStatus> statuses);

    default Appointment findAppointmentByIdOrThrow(Long id) {
        return findAppointmentById(id)
                .orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND_APPOINTMENT_ERROR));
    }
}
