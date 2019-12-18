package com.fredastone.pandacore.repository;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import com.fredastone.pandacore.entity.PasswordResetToken;

public interface PasswordResetTokenRepository extends CrudRepository<PasswordResetToken, Long>{

	Optional<PasswordResetToken> findByToken(String token);

}
