package com.nwoc.a3gs.group.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.nwoc.a3gs.group.app.model.PasswordResetToken;

public interface PasswordResetTokenRepository  extends JpaRepository<PasswordResetToken, Long> {
	PasswordResetToken findByToken(String token);
	public void deleteById(Long id);

}
