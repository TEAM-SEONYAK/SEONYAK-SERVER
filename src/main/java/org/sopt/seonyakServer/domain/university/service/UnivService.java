package org.sopt.seonyakServer.domain.university.service;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.sopt.seonyakServer.domain.university.dto.SearchDeptResponse;
import org.sopt.seonyakServer.domain.university.dto.SearchUnivResponse;
import org.sopt.seonyakServer.domain.university.model.Department;
import org.sopt.seonyakServer.domain.university.repository.DeptRepository;
import org.sopt.seonyakServer.domain.university.repository.UnivRepository;
import org.sopt.seonyakServer.global.exception.enums.ErrorType;
import org.sopt.seonyakServer.global.exception.model.CustomException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UnivService {

    private final UnivRepository univRepository;
    private final DeptRepository deptRepository;

    public SearchUnivResponse searchUniv(final String univNamePart) {
        if (univNamePart == null || univNamePart.trim().isEmpty()) {
            return SearchUnivResponse.of(List.of());
        }

        return SearchUnivResponse.of(univRepository.findByUnivNameContaining(univNamePart));
    }

    public List<SearchDeptResponse> searchDept(
            final String univName,
            final String deptName
    ) {
        if (!univRepository.existsByUnivName(univName)) {
            throw new CustomException(ErrorType.INVALID_UNIV_NAME_ERROR);
        }

        List<Department> departments = deptRepository.findByUnivIdAndDeptNameContaining(univName, deptName);

        return departments.stream()
                .map(department -> SearchDeptResponse.of(
                        department.getDeptName(),
                        department.isClosed())
                )
                .collect(Collectors.toList());
    }
}
