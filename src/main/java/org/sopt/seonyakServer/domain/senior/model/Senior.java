package org.sopt.seonyakServer.domain.senior.model;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.Map;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sopt.seonyakServer.domain.member.model.Member;
import org.sopt.seonyakServer.domain.util.JsonConverter;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "senior")
public class Senior {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne(optional = false)
    @JoinColumn(name = "member_id", referencedColumnName = "id", nullable = false)
    private Member member;

    @Column(name = "business_card", nullable = false, length = 255)
    private String businessCard;

    @Column(name = "company", length = 255)
    private String company;

    @Column(name = "position", length = 255)
    private String position;

    @Column(name = "detail_position", nullable = false, length = 255)
    private String detailPosition;

    @Column(name = "level", nullable = false)
    private int level;

    @Column(name = "catchphrase", length = 255)
    private String catchphrase;

    @Column(name = "career", length = 255)
    private String career;

    @Column(name = "award", length = 255)
    private String award;

    @Column(name = "story", length = 255)
    private String story;

    @Column(name = "preffered_time_list", columnDefinition = "jsonb")
    @Convert(converter = JsonConverter.class)
    private Map<String, Object> prefferedTimeList;

    @Builder(access = AccessLevel.PRIVATE)
    private Senior(
            Member member,
            String businessCard,
            String detailPosition,
            int level,
    ) {
        this.member = member;
        this.businessCard = businessCard;
        this.detailPosition = detailPosition;
        this.level = level;
    }

    public static Senior createSenior(
            Member member,
            String businessCard,
            String detailPosition,
            int level,
    ) {
        return Senior.builder()
                .member(member)
                .businessCard(businessCard)
                .detailPosition(detailPosition)
                .level(level)
                .build();
    }
}
