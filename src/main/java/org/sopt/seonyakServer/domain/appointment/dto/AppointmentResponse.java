package org.sopt.seonyakServer.domain.appointment.dto;

import org.sopt.seonyakServer.domain.appointment.model.AppointmentCardList;

public record AppointmentResponse(
        String myNickname,
        AppointmentCardList appointmentCardList
) {
    public static AppointmentResponse of(
            final String myNickname,
            AppointmentCardList appointmentCardList
    ) {
        return new AppointmentResponse(
                myNickname,
                appointmentCardList
        );
    }
}
