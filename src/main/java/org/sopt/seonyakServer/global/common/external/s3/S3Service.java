package org.sopt.seonyakServer.global.common.external.s3;

import java.net.URL;
import java.time.Duration;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.sopt.seonyakServer.global.common.external.s3.dto.PreSignedUrlResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

@Component
@RequiredArgsConstructor
public class S3Service {

    @Value("${aws-property.s3-bucket-name}")
    private String bucketName;

    private final S3Client s3Client;
    private final S3Presigner s3Presigner;

    // PreSigned URL 만료시간 60분
    private static final Long PRE_SIGNED_URL_EXPIRE_MINUTE = 60L;
    private final static String imagePath = "/profiles";

    public PreSignedUrlResponse getUploadPreSignedUrl() {
        // UUID 파일명 생성
        String uuidFileName = UUID.randomUUID().toString() + ".jpg";
        // 경로 + 파일 이름
        String key = imagePath + uuidFileName;

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        // S3에서 업로드는 PUT 요청
        PutObjectPresignRequest preSignedUrlRequest = PutObjectPresignRequest.builder()
                .signatureDuration(Duration.ofMinutes(PRE_SIGNED_URL_EXPIRE_MINUTE))
                .putObjectRequest(putObjectRequest)
                .build();

        // Persigned URL 생성
        URL url = s3Presigner.presignPutObject(preSignedUrlRequest).url();

        return PreSignedUrlResponse.of(uuidFileName, url.toString());
    }

}
