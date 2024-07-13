package org.sopt.seonyakServer.domain.appointment.dto;

public record AppointmentRejectRequest(
        Long appointmentId,
        String rejectReason,
        String rejectDetail
) {

}
