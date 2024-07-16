package org.sopt.seonyakServer.domain.appointment.model;

import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AppointmentCardList {

    private final Map<AppointmentStatus, AppointmentCard> appointmentCardList = new HashMap<>();

    public void putAppointmentCardList(AppointmentStatus appointmentStatus, AppointmentCard appointmentCard) {
        appointmentCardList.put(appointmentStatus, appointmentCard);
    }
}
