package org.sopt.seonyakServer.domain.appointment.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AppointmentCard implements Comparable<AppointmentCard> {

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

    @JsonIgnore
    private LocalDateTime createdAt;

    @JsonIgnore
    private LocalDateTime updatedAt;

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
            String endTime,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
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
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    @Override
    public int compareTo(AppointmentCard other) {
        // 날짜(date)를 기준으로 비교
        int dateComparison = this.date.compareTo(other.date);

        // 날짜가 같으면 시작시간(startTime)을 기준으로 비교
        if (dateComparison == 0) {
            int startTimeComparison = this.startTime.compareTo(other.startTime);

            // 날짜와 시작시간이 모두 같으면 appointmentId를 기준으로 비교
            if (startTimeComparison == 0) {
                return this.appointmentId.compareTo(other.appointmentId);
            }

            return startTimeComparison;
        }

        // 날짜가 다르면 날짜를 기준으로 비교 결과 반환
        return dateComparison;
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
            String endTime,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
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
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
    }
}
