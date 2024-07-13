package org.sopt.seonyakServer.domain.senior.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import java.util.List;
import org.sopt.seonyakServer.domain.senior.dto.SeniorListResponse;
import org.sopt.seonyakServer.domain.senior.model.QSenior;

public class SeniorRepositoryImpl implements SeniorRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    private final QSenior senior = QSenior.senior;

    public SeniorRepositoryImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public List<SeniorListResponse> searchSeniorFieldPosition(List<String> fields, List<String> positions) {
        BooleanExpression fieldCondition = createFieldCondition(senior, fields);
        BooleanExpression positionCondition = createPositionCondition(senior, positions);
        return queryFactory
                .select(Projections.fields(SeniorListResponse.class,
                        senior.id.as("seniorId"),
                        senior.company,
                        senior.position,
                        senior.detailPosition,
                        senior.member.nickname,
                        senior.member.field,
                        senior.level))
                .from(senior)
                .where(fieldCondition, positionCondition)
                .orderBy(senior.createdAt.asc())
                .fetch();
    }

    private BooleanExpression createFieldCondition(QSenior senior, List<String> fields) {
        // 계열 검색 키워드가 없는 경우
        if (fields == null || fields.isEmpty()) {
            return null;
        }
        // BooleanExpression을 이용하여 or로 엮음
        BooleanExpression condition = senior.member.field.eq(fields.get(0));
        for (String field : fields.subList(1, fields.size())) {
            condition = condition.or(senior.member.field.eq(field));
        }
        return condition;
    }

    private BooleanExpression createPositionCondition(QSenior senior, List<String> positions) {
        if (positions == null || positions.isEmpty()) {
            return null;
        }

        BooleanExpression condition = senior.position.eq(positions.get(0));
        for (String position : positions.subList(1, positions.size())) {
            condition = condition.or(senior.position.eq(position));
        }
        return condition;
    }
}
