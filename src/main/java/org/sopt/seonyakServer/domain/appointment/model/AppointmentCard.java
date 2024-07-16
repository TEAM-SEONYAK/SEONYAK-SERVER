package org.sopt.seonyakServer.domain.appointment.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AppointmentCard {

    private Long appointmentId;
    private AppointmentStatus appointmentStatus;
    private String nickname;
    private String image;
    private String field;
    private String department;
    private List<String> topic;
    private String personalTopic;
    private String company;
    private String position;
    private String detailPosition;
    private String level;
    private String date;
    private String startTime;
    private String endTime;

    @Builder(access = AccessLevel.PRIVATE)
    private AppointmentCard(
            Long appointmentId,
            AppointmentStatus appointmentStatus,
            String nickname,
            String image,
            String field,
            String department,
            List<String> topic,
            String personalTopic,
            String company,
            String position,
            String detailPosition,
            String level,
            String date,
            String startTime,
            String endTime
    ) {
        this.appointmentId = appointmentId;
        this.appointmentStatus = appointmentStatus;
        this.nickname = nickname;
        this.image = image;
        this.field = field;
        this.department = department;
        this.topic = topic;
        this.personalTopic = personalTopic;
        this.company = company;
        this.position = position;
        this.detailPosition = detailPosition;
        this.level = level;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public static AppointmentCard create(
            Long appointmentId,
            AppointmentStatus appointmentStatus,
            String nickname,
            String image,
            String field,
            String department,
            List<String> topic,
            String personalTopic,
            String company,
            String position,
            String detailPosition,
            String level,
            String date,
            String startTime,
            String endTime
    ) {
        return AppointmentCard.builder()
                .appointmentId(appointmentId)
                .appointmentStatus(appointmentStatus)
                .nickname(nickname)
                .image(image)
                .field(field)
                .department(department)
                .topic(topic)
                .personalTopic(personalTopic)
                .company(company)
                .position(position)
                .detailPosition(detailPosition)
                .level(level)
                .date(date)
                .startTime(startTime)
                .endTime(endTime)
                .build();
    }
}
