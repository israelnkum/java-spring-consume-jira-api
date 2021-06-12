package com.amalitech.amalitechprojectdashboard.controllers.auth.Password;

import org.springframework.data.repository.CrudRepository;

public interface PasswordResetRepository extends CrudRepository<PasswordResetToken, Long> {
	PasswordResetToken findByToken(String token);
}
