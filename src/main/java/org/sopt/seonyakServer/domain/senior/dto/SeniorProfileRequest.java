package org.sopt.seonyakServer.domain.senior.dto;

import org.sopt.seonyakServer.domain.senior.model.PreferredTimeList;

public record SeniorProfileRequest(
        String catchphrase,
        String career,
        String award,
        String story,
        PreferredTimeList preferredTimeList
) {
}
