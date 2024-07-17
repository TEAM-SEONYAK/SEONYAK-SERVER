package org.sopt.seonyakServer.domain.appointment.dto;

import jakarta.validation.constraints.NotBlank;
import java.util.List;
import org.sopt.seonyakServer.domain.appointment.model.DataTimeRange;

public record AppointmentAcceptRequest(
        @NotBlank(message = "약속 Id는 공백일 수 없습니다.")
        Long appointmentId,
        @NotBlank(message = "구글 미트 링크는 공백일 수 없습니다.")
        String googleMeetLink,
        @NotBlank(message = "timeList는 공백일 수 없습니다.")
        List<DataTimeRange> timeList
) {
}
