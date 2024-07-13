package org.sopt.seonyakServer.domain.university.controller;

import lombok.RequiredArgsConstructor;
import org.sopt.seonyakServer.domain.university.dto.SearchUnivResponse;
import org.sopt.seonyakServer.domain.university.service.UnivService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UnivController {

    private final UnivService univService;

    @GetMapping("/search")
    public ResponseEntity<SearchUnivResponse> searchUniv(
            @RequestParam final String univName
    ) {
        return ResponseEntity.ok(univService.searchUniv(univName));
    }
}
