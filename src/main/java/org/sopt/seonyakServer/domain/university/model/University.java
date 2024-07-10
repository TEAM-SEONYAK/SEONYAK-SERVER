package org.sopt.seonyakServer.domain.university.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "university")
public class University {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "univ_name", nullable = false)
    private String univName;

    @OneToMany(mappedBy = "university", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Department> departmentList;

    @Builder(access = AccessLevel.PRIVATE)
    private University(
            final String univName
    ) {
        this.univName = univName;
    }

    public static University createUniversity(
            final String univName
    ) {
        return University.builder()
                .univName(univName)
                .build();
    }
}
