package org.sopt.seonyakServer.domain.university.repository;

import java.util.List;
import org.sopt.seonyakServer.domain.university.model.University;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UnivRepository extends JpaRepository<University, Long> {

    @Query("SELECT u.univName "
            + "FROM University u "
            + "WHERE u.univName "
            + "LIKE %:univNamePart%")
    List<String> findByUnivNameContaining(@Param("univNamePart") String univNamePart);

    boolean existsByUnivName(String UnivName);
}
