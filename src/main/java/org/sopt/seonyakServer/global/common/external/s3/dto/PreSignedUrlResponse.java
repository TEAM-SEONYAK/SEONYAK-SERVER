package org.sopt.seonyakServer.global.common.external.s3.dto;

public record PreSignedUrlResponse(
        String fileName,
        String url
) {
    public static PreSignedUrlResponse of(String fileName, String url) {
        return new PreSignedUrlResponse(fileName, url);
    }
}
