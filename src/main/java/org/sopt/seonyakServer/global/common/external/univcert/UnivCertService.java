package org.sopt.seonyakServer.global.common.external.univcert;

import lombok.RequiredArgsConstructor;
import org.sopt.seonyakServer.global.common.external.univcert.dto.UnivCertClientRequest;
import org.sopt.seonyakServer.global.common.external.univcert.dto.UnivCertRequest;
import org.sopt.seonyakServer.global.common.external.univcert.dto.UnivCertResponse;
import org.sopt.seonyakServer.global.common.external.univcert.dto.UnivCertVerifyClientRequest;
import org.sopt.seonyakServer.global.common.external.univcert.dto.UnivCertVerifyRequest;
import org.sopt.seonyakServer.global.common.external.univcert.feign.UnivCertClient;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UnivCertService {
    private final UnivCertClient univCertClient;
    private final UnivCertConfig univCertConfig;

    public UnivCertResponse univCert(UnivCertRequest univCertRequest) {
        String apiKey = univCertConfig.getUnivKey();
        return univCertClient.sendVerifyCode(
                UnivCertClientRequest.of(apiKey, univCertRequest.email(), univCertRequest.univName(), true));
    }

    public UnivCertResponse univCertVerify(UnivCertVerifyRequest univCertVerifyRequest) {
        String apiKey = univCertConfig.getUnivKey();
        return univCertClient.verifyCode(
                UnivCertVerifyClientRequest.of(apiKey, univCertVerifyRequest.email(), univCertVerifyRequest.univName(),
                        univCertVerifyRequest.code()));
    }
}
