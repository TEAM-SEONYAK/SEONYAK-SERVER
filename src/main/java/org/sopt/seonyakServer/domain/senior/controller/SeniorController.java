package org.sopt.seonyakServer.domain.senior.controller;

import lombok.RequiredArgsConstructor;
import org.sopt.seonyakServer.domain.senior.dto.SeniorProfileRequest;
import org.sopt.seonyakServer.domain.senior.model.PreferredTimeList;
import org.sopt.seonyakServer.domain.senior.service.SeniorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/senior")
public class SeniorController {

    private final SeniorService seniorService;

    @PatchMapping("/profile")
    public ResponseEntity<Void> patchProfile(
            @RequestBody final SeniorProfileRequest seniorProfileRequest
    ) {
        seniorService.patchSeniorProfile(seniorProfileRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/time/{seniorId}")
    public ResponseEntity<PreferredTimeList> getPreferredTime(
            @PathVariable final Long seniorId
    ) {
        return ResponseEntity.ok(seniorService.getSeniorPreferredTime(seniorId));
    }
}
