package org.sopt.seonyakServer.global.common.external.univcert;

import lombok.RequiredArgsConstructor;
import org.sopt.seonyakServer.global.common.external.univcert.dto.UnivCertRequest;
import org.sopt.seonyakServer.global.common.external.univcert.dto.UnivCertResponse;
import org.sopt.seonyakServer.global.common.external.univcert.dto.UnivCertVerifyRequest;
import org.sopt.seonyakServer.global.exception.enums.ErrorType;
import org.sopt.seonyakServer.global.exception.model.CustomException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/univ")
@RequiredArgsConstructor
public class UnivCertController {

    private final UnivCertService univCertService;

    @PostMapping("/verify")
    public ResponseEntity<Void> univCert(
            @RequestBody UnivCertRequest univCertRequest
    ) {
        UnivCertResponse univCertResponse = univCertService.univCert(univCertRequest);
        if (univCertResponse.success().equals("true")) {
            return ResponseEntity.ok().build();
        }
        throw new CustomException(ErrorType.INVALID_UNIV_NAME_ERROR);
    }

    @PostMapping("/verifycode")
    public ResponseEntity<Void> univCertVerify(
            @RequestBody UnivCertVerifyRequest univCertVerifyRequest
    ) {
        UnivCertResponse univCertResponse = univCertService.univCertVerify(univCertVerifyRequest);
        if (univCertResponse.success().equals("true")) {
            return ResponseEntity.ok().build();
        }
        throw new CustomException(ErrorType.INVALID_VERIFICATION_CODE_ERROR);
    }
}
