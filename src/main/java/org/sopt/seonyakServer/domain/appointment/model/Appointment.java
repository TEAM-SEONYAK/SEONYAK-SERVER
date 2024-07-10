package org.sopt.seonyakServer.domain.appointment.model;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.Map;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sopt.seonyakServer.domain.member.model.Member;
import org.sopt.seonyakServer.domain.senior.model.Senior;
import org.sopt.seonyakServer.domain.util.JsonConverter;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "appointment")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_id", referencedColumnName = "id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "senior_id", referencedColumnName = "id", nullable = false)
    private Senior senior;

    @Column(name = "time_list", columnDefinition = "jsonb", nullable = false)
    @Convert(converter = JsonConverter.class)
    private Map<String, Object> timeList;

    @Column(name = "topic")
    private String topic;

    @Column(name = "personal_topic")
    private String personalTopic;

    @Enumerated(EnumType.STRING)
    @Column(name = "appointment_status", nullable = false)
    private AppointmentStatus appointmentStatus;

    @Column(name = "google_meet_link")
    private String googleMeetLink;

    @Column(name = "reject_reason")
    private String rejectReason;

    @Builder(access = AccessLevel.PRIVATE)
    private Appointment(
            Member member,
            Senior senior,
            AppointmentStatus appointmentStatus,
            Map<String, Object> timeList
    ) {
        this.member = member;
        this.senior = senior;
        this.appointmentStatus = appointmentStatus;
        this.timeList = timeList;
    }

    public static Appointment createAppointment(
            Member member,
            Senior senior,
            AppointmentStatus appointmentStatus,
            Map<String, Object> timeList
    ) {
        return Appointment.builder()
                .member(member)
                .senior(senior)
                .appointmentStatus(appointmentStatus)
                .timeList(timeList)
                .build();
    }
}
