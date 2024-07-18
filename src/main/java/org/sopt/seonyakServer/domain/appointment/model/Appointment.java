package org.sopt.seonyakServer.domain.appointment.model;

import jakarta.persistence.Column;
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
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.sopt.seonyakServer.domain.member.model.Member;
import org.sopt.seonyakServer.domain.senior.model.Senior;
import org.sopt.seonyakServer.global.common.model.BaseTimeEntity;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "appointment")
public class Appointment extends BaseTimeEntity {

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
    @JdbcTypeCode(SqlTypes.JSON)
    private List<DateTimeRange> timeList;

    @Column(name = "topic")
    @JdbcTypeCode(SqlTypes.JSON)
    private List<String> topic;

    @Column(name = "personal_topic")
    private String personalTopic;

    @Enumerated(EnumType.STRING)
    @Column(name = "appointment_status", nullable = false)
    private AppointmentStatus appointmentStatus;

    @Column(name = "google_meet_link")
    private String googleMeetLink;

    @Column(name = "reject_reason")
    private String rejectReason;

    @Column(name = "reject_detail")
    private String rejectDetail;

    @Builder(access = AccessLevel.PRIVATE)
    private Appointment(
            Member member,
            Senior senior,
            AppointmentStatus appointmentStatus,
            List<DateTimeRange> timeList,
            List<String> topic,
            String personalTopic
    ) {
        this.member = member;
        this.senior = senior;
        this.appointmentStatus = appointmentStatus;
        this.timeList = timeList;
        this.topic = topic;
        this.personalTopic = personalTopic;
    }

    public static Appointment create(
            Member member,
            Senior senior,
            AppointmentStatus appointmentStatus,
            List<DateTimeRange> timeList,
            List<String> topic,
            String personalTopic
    ) {
        return Appointment.builder()
                .member(member)
                .senior(senior)
                .appointmentStatus(appointmentStatus)
                .timeList(timeList)
                .topic(topic)
                .personalTopic(personalTopic)
                .build();
    }

    public void acceptAppointment(
            List<DateTimeRange> timeList,
            String googleMeetLink,
            AppointmentStatus appointmentStatus
    ) {
        this.timeList = timeList;
        this.googleMeetLink = googleMeetLink;
        this.appointmentStatus = appointmentStatus;
    }

    public void rejectAppointment(
            String rejectReason,
            String rejectDetail,
            AppointmentStatus appointmentStatus
    ) {
        this.rejectReason = rejectReason;
        this.rejectDetail = rejectDetail;
        this.appointmentStatus = appointmentStatus;
    }

    public void setAppointmentPast() {
        this.appointmentStatus = AppointmentStatus.PAST;
    }
}
