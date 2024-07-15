package org.sopt.seonyakServer.domain.university.dto;

public record SearchDeptResponse(
        String deptName,
        boolean isClosed
) {
    public static SearchDeptResponse of(
            final String deptName,
            final boolean isClosed
    ) {
        return new SearchDeptResponse(
                deptName,
                isClosed
        );
    }
}
