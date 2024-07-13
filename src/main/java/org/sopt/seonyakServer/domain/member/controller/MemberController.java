package org.sopt.seonyakServer.domain.member.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.sopt.seonyakServer.domain.member.dto.LoginSuccessResponse;
import org.sopt.seonyakServer.domain.member.dto.NicknameRequest;
import org.sopt.seonyakServer.domain.member.dto.SendCodeRequest;
import org.sopt.seonyakServer.domain.member.dto.VerifyCodeRequest;
import org.sopt.seonyakServer.domain.member.service.MemberService;
import org.sopt.seonyakServer.domain.member.service.MessageService;
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
    private final MessageService messageService;

    @PostMapping("/auth/login")
    public ResponseEntity<LoginSuccessResponse> login(
            @RequestParam final String authorizationCode,
            @Valid @RequestBody final MemberLoginRequest loginRequest
    ) {
        return ResponseEntity.ok(memberService.create(authorizationCode, loginRequest));
    }

    @PostMapping("/nickname")
    public ResponseEntity<Void> validNickname(
            @Valid @RequestBody final NicknameRequest nicknameRequest
    ) {
        memberService.validNickname(nicknameRequest);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/phone/verify")
    public ResponseEntity<Void> sendCode(
            @Valid @RequestBody final SendCodeRequest sendCodeRequest
    ) {
        messageService.sendMessage(sendCodeRequest);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/phone/verifycode")
    public ResponseEntity<Void> verifyCode(
            @Valid @RequestBody final VerifyCodeRequest verifyCodeRequest
    ) {
        messageService.verifyCode(verifyCodeRequest);
        messageService.validPhoneNumberDuplication(verifyCodeRequest);

        return ResponseEntity.ok().build();
    }
}
