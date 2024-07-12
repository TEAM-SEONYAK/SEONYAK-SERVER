package org.sopt.seonyakServer.global.common.external.univcert.feign;

import org.sopt.seonyakServer.global.common.external.univcert.dto.UnivCertClientRequest;
import org.sopt.seonyakServer.global.common.external.univcert.dto.UnivCertResponse;
import org.sopt.seonyakServer.global.common.external.univcert.dto.UnivCertVerifyClientRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "univCert", url = "https://univcert.com/api/v1")
public interface UnivCertClient {

    @PostMapping("/certify")
    UnivCertResponse sendVerifyCode(
            @RequestBody UnivCertClientRequest sendRequest
    );

    @PostMapping("/certifycode")
    UnivCertResponse verifyCode(
            @RequestBody UnivCertVerifyClientRequest verifyRequest
    );
}