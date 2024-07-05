package org.sopt.seonyakServer.global.common.external.client.google;

import org.sopt.seonyakServer.global.common.external.client.dto.GoogleUserInfoResponse;
import org.sopt.seonyakServer.global.config.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "googleUserClient", url = "https://www.googleapis.com", configuration = FeignClientConfig.class)
public interface GoogleUserClient {

    @GetMapping("/userinfo/v2/me")
    GoogleUserInfoResponse getGoogleUserInfo(
            @RequestParam(value = "access_token") final String accessToken
    );
}
