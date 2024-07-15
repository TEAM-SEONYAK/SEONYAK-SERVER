package org.sopt.seonyakServer.domain.university.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sopt.seonyakServer.global.common.model.BaseTimeEntity;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "department")
public class Department extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "univ_id", referencedColumnName = "id", nullable = false)
    private University university;

    @Column(name = "dept_name", nullable = false)
    private String deptName;

    @Column(name = "is_closed", nullable = false)
    private boolean isClosed;

    @Builder(access = AccessLevel.PRIVATE)
    private Department(
            final University university,
            final String deptName,
            final boolean isClosed
    ) {
        this.university = university;
        this.deptName = deptName;
        this.isClosed = isClosed;
    }

    public static Department create(
            final University university,
            final String deptName,
            final boolean isClosed
    ) {
        return Department.builder()
                .university(university)
                .deptName(deptName)
                .isClosed(isClosed)
                .build();
    }
}
