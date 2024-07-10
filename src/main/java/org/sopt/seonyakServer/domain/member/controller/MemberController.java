package org.sopt.seonyakServer.domain.member.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.sopt.seonyakServer.domain.member.dto.LoginSuccessResponse;
import org.sopt.seonyakServer.domain.member.dto.NicknameRequest;
import org.sopt.seonyakServer.domain.member.service.MemberService;
import org.sopt.seonyakServer.global.common.dto.ResponseDto;
import org.sopt.seonyakServer.global.common.external.client.dto.MemberLoginRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/auth/login")
    public ResponseEntity<LoginSuccessResponse> login(
            @RequestParam final String authorizationCode,
            @RequestBody @Valid final MemberLoginRequest loginRequest
    ) {
        return ResponseEntity.ok(memberService.create(authorizationCode, loginRequest));
    }

    @PostMapping("/nickname")
    public ResponseDto<?> validNickname(
            @RequestBody final NicknameRequest nicknameRequest
    ) {
        memberService.validNickname(nicknameRequest);

        return ResponseDto.success(null);
    }
}
