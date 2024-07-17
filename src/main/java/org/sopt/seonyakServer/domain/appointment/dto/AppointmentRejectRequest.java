package org.sopt.seonyakServer.domain.appointment.dto;

import jakarta.validation.constraints.NotBlank;

public record AppointmentRejectRequest(
        @NotBlank(message = "약속 Id는 공백일 수 없습니다.")
        Long appointmentId,
        @NotBlank(message = "거절 사유는 공백일 수 없습니다.")
        String rejectReason,
        String rejectDetail
) {
}
