package org.sopt.seonyakServer.domain.member.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.sopt.seonyakServer.domain.senior.model.Senior;
import org.sopt.seonyakServer.global.common.model.BaseTimeEntity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member")
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "social_type", nullable = false)
    private SocialType socialType;

    @Column(name = "social_id", nullable = false)
    private String socialId;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "is_subscribed")
    private Boolean isSubscribed;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "image")
    private String image;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "univ_name")
    private String univName;

    @Column(name = "field")
    private String field;

    @Column(name = "departmentList", columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    private List<String> departmentList;

    @OneToOne(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Senior senior;

    @Builder(access = AccessLevel.PRIVATE)
    private Member(
            final SocialType socialType,
            final String socialId,
            final String email
    ) {
        this.socialType = socialType;
        this.socialId = socialId;
        this.email = email;
    }

    public static Member createMember(
            final SocialType socialType,
            final String socialId,
            final String email
    ) {
        return Member.builder()
                .socialType(socialType)
                .socialId(socialId)
                .email(email)
                .build();
    }

    public void updateMember(
            Boolean isSubscribed,
            String nickname,
            String image,
            String phoneNumber,
            String univName,
            String field,
            List<String> department
    ) {
        if (isSubscribed != null) {
            this.isSubscribed = isSubscribed;
        }
        if (nickname != null) {
            this.nickname = nickname;
        }
        if (image != null) {
            this.image = image;
        }
        if (phoneNumber != null) {
            this.phoneNumber = phoneNumber;
        }
        if (univName != null) {
            this.univName = univName;
        }
        if (field != null) {
            this.field = field;
        }
        if (department != null) {
            this.departmentList = department;
        }
    }
}
