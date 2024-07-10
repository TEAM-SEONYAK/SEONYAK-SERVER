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

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "department")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "univ_id", referencedColumnName = "id", nullable = false)
    private University university;

    @Column(name = "dept_name", nullable = false, length = 20)
    private String deptName;

    @Column(name = "is_closed", nullable = false)
    private Boolean isClosed;

    @Builder(access = AccessLevel.PRIVATE)
    private Department(
            final University university,
            final String deptName,
            final Boolean isClosed
    ) {
        this.university = university;
        this.deptName = deptName;
        this.isClosed = isClosed;
    }

    public static Department createDepartment(
            final University university,
            final String deptName,
            final Boolean isClosed
    ) {
        return Department.builder()
                .university(university)
                .deptName(deptName)
                .isClosed(isClosed)
                .build();
    }
}
