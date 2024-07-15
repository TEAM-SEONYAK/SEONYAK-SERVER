package org.sopt.seonyakServer.domain.member.dto;

import java.util.List;

public record MemberJoinRequest(
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
