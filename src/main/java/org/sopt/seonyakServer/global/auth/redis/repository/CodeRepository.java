package org.sopt.seonyakServer.global.auth.redis.repository;

import java.util.Optional;
import org.sopt.seonyakServer.global.auth.redis.domain.Code;
import org.springframework.data.repository.CrudRepository;

public interface CodeRepository extends CrudRepository<Code, String> {

    Optional<Code> findByPhoneNumber(final String phoneNumber);
}
