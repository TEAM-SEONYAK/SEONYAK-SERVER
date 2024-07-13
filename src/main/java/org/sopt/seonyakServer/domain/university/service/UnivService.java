package org.sopt.seonyakServer.domain.university.service;

import lombok.RequiredArgsConstructor;
import org.sopt.seonyakServer.domain.university.dto.SearchUnivResponse;
import org.sopt.seonyakServer.domain.university.repository.UnivRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UnivService {

    private final UnivRepository univRepository;

    public SearchUnivResponse searchUniv(String univNamePart) {
        return SearchUnivResponse.of(univRepository.findByUnivNameContaining(univNamePart));
    }
}
