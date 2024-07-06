package org.sopt.seonyakServer.global.common.external.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GoogleAccessTokenResponse(
        @JsonProperty("access_token")
        String accessToken
) {
}
