package org.sopt.seonyakServer.global.common.external.s3;

import lombok.RequiredArgsConstructor;
import org.sopt.seonyakServer.global.common.external.s3.dto.PreSignedUrlResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
public class S3Controller {
    private final S3Service s3Service;

    @GetMapping("/image")
    public ResponseEntity<PreSignedUrlResponse> getPreSignedUrl() {
        return ResponseEntity.ok(s3Service.getUploadPreSignedUrl());
    }

}
