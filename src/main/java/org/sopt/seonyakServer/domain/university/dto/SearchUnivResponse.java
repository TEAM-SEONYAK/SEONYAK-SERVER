package org.sopt.seonyakServer.domain.university.dto;

import java.util.List;

public record SearchUnivResponse(
        List<String> univSearchResult
) {
    public static SearchUnivResponse of(final List<String> univSearchResult) {
        return new SearchUnivResponse(univSearchResult);
    }
}
