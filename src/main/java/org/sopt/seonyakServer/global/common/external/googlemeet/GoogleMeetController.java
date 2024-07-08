package org.sopt.seonyakServer.global.common.external.googlemeet;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sopt.seonyakServer.global.common.external.googlemeet.dto.GoogleMeetUrlResponse;
import org.sopt.seonyakServer.global.exception.enums.ErrorType;
import org.sopt.seonyakServer.global.exception.model.CustomException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
@Slf4j
public class GoogleMeetController {
    private final GoogleMeetService googleMeetService;

    @PostMapping("/google-meet")
    public ResponseEntity<GoogleMeetUrlResponse> createSpace() {
        try {
            return ResponseEntity.ok(googleMeetService.createMeetingSpace());
        } catch (Exception e) {
            log.info(e.getMessage());
            throw new CustomException(ErrorType.GET_GOOGLE_MEET_URL_ERROR);
        }
    }
}
