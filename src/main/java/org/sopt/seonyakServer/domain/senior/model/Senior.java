package org.sopt.seonyakServer.domain.senior.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.sopt.seonyakServer.domain.member.model.Member;
import org.sopt.seonyakServer.global.common.model.BaseTimeEntity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "senior")
public class Senior extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne(optional = false)
    @JoinColumn(name = "member_id", referencedColumnName = "id", nullable = false)
    private Member member;

    @Column(name = "business_card", nullable = false)
    private String businessCard;

    @Column(name = "company")
    private String company;

    @Column(name = "position")
    private String position;

    @Column(name = "detail_position", nullable = false)
    private String detailPosition;

    @Column(name = "level", nullable = false)
    private String level;

    @Column(name = "catchphrase")
    private String catchphrase;

    @Column(name = "career")
    private String career;

    @Column(name = "award")
    private String award;

    @Column(name = "story")
    private String story;

    @Column(name = "preferred_time_list", columnDefinition = "json")
    @JdbcTypeCode(SqlTypes.JSON)
    private PreferredTimeList preferredTimeList;

    @Builder(access = AccessLevel.PRIVATE)
    private Senior(
            Member member,
            String detailPosition,
            String company,
            String position,
            String level
    ) {
        this.member = member;
        this.detailPosition = detailPosition;
        this.company = company;
        this.position = position;
        this.level = level;
    }

    public static Senior create(
            Member member,
            String detailPosition,
            String company,
            String position,
            String level
    ) {
        return Senior.builder()
                .member(member)
                .detailPosition(detailPosition)
                .company(company)
                .position(position)
                .level(level)
                .build();
    }

    public void updateSenior(
            String catchphrase,
            String career,
            String award,
            String story,
            PreferredTimeList preferredTimeList
    ) {
        this.catchphrase = catchphrase;
        this.career = career;
        this.award = award;
        this.story = story;
        this.preferredTimeList = preferredTimeList;
    }

    public void addBusinessCard(
            String businessCard
    ) {
        this.businessCard = businessCard;
    }
}
