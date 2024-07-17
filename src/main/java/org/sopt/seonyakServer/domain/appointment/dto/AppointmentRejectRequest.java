package org.sopt.seonyakServer.domain.appointment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record AppointmentRejectRequest(
        @NotNull(message = "약속 Id는 공백일 수 없습니다.")
        @Positive(message = "약속 Id는 양수여야 합니다.")
        Long appointmentId,
        @NotBlank(message = "거절 사유는 공백일 수 없습니다.")
        String rejectReason,
        String rejectDetail
) {
}
