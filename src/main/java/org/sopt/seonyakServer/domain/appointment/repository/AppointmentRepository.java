package org.sopt.seonyakServer.domain.appointment.repository;

import org.sopt.seonyakServer.domain.appointment.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    @Query("SELECT a.googleMeetLink "
            + "FROM Appointment a "
            + "WHERE a.id = :appointmentId")
    String findGoogleMeetLinkById(@Param("appointmentId") Long appointmentId);

    @Query("SELECT a.member.id "
            + "FROM Appointment a "
            + "WHERE a.id = :appointmentId")
    Long findMemberIdById(@Param("appointmentId") Long appointmentId);

    @Query("SELECT a.senior.id "
            + "FROM Appointment a "
            + "WHERE a.id = :appointmentId")
    Long findSeniorIdById(@Param("appointmentId") Long appointmentId);
}
