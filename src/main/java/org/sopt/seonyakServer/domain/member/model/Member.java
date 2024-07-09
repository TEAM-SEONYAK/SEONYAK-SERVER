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
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sopt.seonyakServer.domain.senior.model.Senior;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "member")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long memberId;

    @Enumerated(EnumType.STRING)
    @Column(name = "social_type", nullable = false)
    private SocialType socialType;

    @Column(name = "social_id", nullable = false, length = 255)
    private String socialId;

    @Column(name = "email", nullable = false, length = 255)
    private String email;

    @Column(name = "is_subscribed")
    private Boolean isSubscribed;

    @Column(name = "nickname", length = 20)
    private String nickname;

    @Column(name = "image", length = 255)
    private String image;

    @Column(name = "phone_number", length = 255)
    private String phoneNumber;

    @Column(name = "univ_name", length = 255)
    private String univName;

    @Column(name = "field", length = 255)
    private String field;

    @Column(name = "department", length = 255)
    private String department;

    @OneToOne(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Senior senior;

    public static Member createMember(SocialType socialType, String socialId, String email) {
        return Member.builder()
                .socialType(socialType)
                .socialId(socialId)
                .email(email)
                .build();
    }
}
