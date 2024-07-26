package org.sopt.seonyakServer.domain.member.dto;

import jakarta.validation.constraints.NotBlank;
import java.util.List;

public record MemberJoinRequest(
        @NotBlank(message = "role 공백일 수 없습니다.")
        String role,
        Boolean isSubscribed,
        String nickname,
        String image,
        String phoneNumber,
        String univName,
        String field,
        List<String> departmentList,
        String businessCard,
        String company,
        String position,
        String detailPosition,
        String level
) {
}
