package org.sopt.seonyakServer.domain.appointment.model;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AppointmentCardList {

    private final List<AppointmentCard> pending = new ArrayList<>();
    private final List<AppointmentCard> scheduled = new ArrayList<>();
    private final List<AppointmentCard> past = new ArrayList<>();

    public void putAppointmentCardList(AppointmentStatus appointmentStatus, AppointmentCard appointmentCard) {
        switch (appointmentStatus) {
            case PENDING -> pending.add(appointmentCard);
            case SCHEDULED -> scheduled.add(appointmentCard);
            case PAST, REJECTED -> past.add(appointmentCard);
        }
    }
}
