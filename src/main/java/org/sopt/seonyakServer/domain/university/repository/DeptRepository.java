package org.sopt.seonyakServer.domain.university.repository;

import java.util.List;
import org.sopt.seonyakServer.domain.university.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DeptRepository extends JpaRepository<Department, Long> {

    @Query("SELECT d "
            + "FROM Department d "
            + "WHERE d.university.univName = :univName "
            + "AND d.deptName "
            + "LIKE %:deptName%")
    List<Department> findByUnivIdAndDeptNameContaining(
            @Param("univName") String univName,
            @Param("deptName") String deptName
    );
}
