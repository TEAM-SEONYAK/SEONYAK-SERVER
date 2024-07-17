package org.sopt.seonyakServer.domain.appointment.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import java.util.stream.Collectors;
import org.sopt.seonyakServer.domain.appointment.model.AppointmentCard;
import org.sopt.seonyakServer.domain.appointment.model.AppointmentCardList;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record AppointmentResponse(
        String myNickname,
        List<AppointmentCard> pending,
        List<AppointmentCard> scheduled,
        List<AppointmentCard> past
) {
    public static AppointmentResponse of(String myNickname, AppointmentCardList appointmentCardList) {
        // 새로 생긴 약속이 상단에 위치하도록 정렬 (createdAt 기준 내림차순)
        List<AppointmentCard> sortedPending = appointmentCardList.getPending().stream()
                .sorted((a, b) -> {
                    if (a.getCreatedAt() == null || b.getCreatedAt() == null) {
                        return 0;
                    }
                    return b.getCreatedAt().compareTo(a.getCreatedAt());
                })
                .collect(Collectors.toList());

        // 가까운 약속부터 상단에 위치하도록 정렬 (date, startTime 기준)
        List<AppointmentCard> sortedScheduled = appointmentCardList.getScheduled().stream()
                .sorted()
                .collect(Collectors.toList());

        // 새로 지난 약속으로 옮겨진 약속이 상단에 위치하도록 정렬 (updatedAt 기준 내림차순)
        List<AppointmentCard> sortedPast = appointmentCardList.getPast().stream()
                .sorted((a, b) -> {
                    if (a.getUpdatedAt() == null || b.getUpdatedAt() == null) {
                        return 0;
                    }
                    return b.getUpdatedAt().compareTo(a.getUpdatedAt());
                })
                .collect(Collectors.toList());

        return new AppointmentResponse(
                myNickname,
                sortedPending,
                sortedScheduled,
                sortedPast
        );
    }
}
