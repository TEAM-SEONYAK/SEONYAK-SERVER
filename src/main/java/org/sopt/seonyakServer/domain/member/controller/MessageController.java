package org.sopt.seonyakServer.domain.member.controller;

import lombok.RequiredArgsConstructor;
import org.sopt.seonyakServer.domain.member.dto.CertifyCodeRequest;
import org.sopt.seonyakServer.domain.member.dto.SendMessageRequest;
import org.sopt.seonyakServer.domain.member.service.MessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/phone")
public class MessageController {

    private final MessageService messageService;

    @PostMapping("/certify")
    public ResponseEntity<Void> sendCertifyCode(
            @RequestBody final SendMessageRequest sendMessageRequest
    ) {
        messageService.sendMessage(sendMessageRequest);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/certifycode")
    public ResponseEntity<Void> certifyCode(
            @RequestBody final CertifyCodeRequest certifyCodeRequest
    ) {
        messageService.certifyCode(certifyCodeRequest);

        return ResponseEntity.ok().build();
    }
}
