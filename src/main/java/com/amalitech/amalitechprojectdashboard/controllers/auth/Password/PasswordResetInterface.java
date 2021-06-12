package com.amalitech.amalitechprojectdashboard.controllers.auth.Password;

import com.amalitech.amalitechprojectdashboard.models.User;

public interface PasswordResetInterface {
	String generateDefaultPassword();
	
	void createPasswordResetTokenForUser(String email);
	
	String generatePasswordResetToken(User user);
	
	PasswordResetToken validatePasswordResetToken(String token);
	
	boolean isTokenExpired(PasswordResetToken passwordResetToken);
	
	boolean isTokenFound(PasswordResetToken passToken);
	
	void deletePasswordResetToken(long id);
}
