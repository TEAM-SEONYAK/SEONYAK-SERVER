package org.sopt.seonyakServer.global.common.external.naver;

import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.sopt.seonyakServer.global.common.external.naver.dto.OcrBusinessResponse;
import org.sopt.seonyakServer.global.common.external.naver.dto.OcrUnivResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/ocr")
@RequiredArgsConstructor
public class OcrController {

    private final OcrService ocrService;

    @PostMapping("/univ")
    public ResponseEntity<OcrUnivResponse> ocrUniv(@RequestParam("imageFile") MultipartFile file)
            throws IOException {
        return ResponseEntity.ok(ocrService.ocrUniv(file));
    }

    @PostMapping("/business-card")
    public ResponseEntity<OcrBusinessResponse> ocrBusiness(@RequestParam("imageFile") MultipartFile file)
            throws IOException {
        return ResponseEntity.ok(ocrService.ocrBusiness(file));
    }
}
