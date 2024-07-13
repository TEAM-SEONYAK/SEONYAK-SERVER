package org.sopt.seonyakServer.domain.senior.repository;

import java.util.List;
import org.sopt.seonyakServer.domain.senior.dto.SeniorListResponse;

public interface SeniorRepositoryCustom {
    List<SeniorListResponse> searchSeniorFieldPosition(List<String> fields, List<String> positions);
}
