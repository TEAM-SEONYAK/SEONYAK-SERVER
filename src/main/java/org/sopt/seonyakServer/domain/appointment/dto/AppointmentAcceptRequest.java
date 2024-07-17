package org.sopt.seonyakServer.domain.appointment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.List;
import org.sopt.seonyakServer.domain.appointment.model.DataTimeRange;

public record AppointmentAcceptRequest(
        @NotNull(message = "약속 Id는 공백일 수 없습니다.")
        @Positive(message = "약속 Id는 양수여야 합니다.")
        Long appointmentId,
        @NotBlank(message = "구글 미트 링크는 공백일 수 없습니다.")
        String googleMeetLink,
        @NotEmpty(message = "timeList는 공백일 수 없습니다.")
        List<DataTimeRange> timeList
) {
}
