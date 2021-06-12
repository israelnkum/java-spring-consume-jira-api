package com.amalitech.amalitechprojectdashboard.controllers.auth.Password;

import com.amalitech.amalitechprojectdashboard.controllers.registration.email.SendEmailService;
import com.amalitech.amalitechprojectdashboard.models.User;
import com.amalitech.amalitechprojectdashboard.repositories.user.UserRepository;
import org.passay.CharacterData;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

@Service
public class PasswordResetService implements PasswordResetInterface{
	
	private final PasswordResetRepository passwordResetRepository;
	private final UserRepository userRepository;
	private final SendEmailService sendEmailService;
	
	public PasswordResetService(PasswordResetRepository passwordResetRepository, UserRepository userRepository, SendEmailService sendEmailService) {
		this.passwordResetRepository = passwordResetRepository;
		this.userRepository = userRepository;
		this.sendEmailService = sendEmailService;
	}
	
	
	@Override
	public String generateDefaultPassword() {
		PasswordGenerator gen = new PasswordGenerator();
		CharacterData lowerCaseChars = EnglishCharacterData.LowerCase;
		CharacterRule lowerCaseRule = new CharacterRule(lowerCaseChars);
		lowerCaseRule.setNumberOfCharacters(2);

		CharacterData upperCaseChars = EnglishCharacterData.UpperCase;
		CharacterRule upperCaseRule = new CharacterRule(upperCaseChars);
		upperCaseRule.setNumberOfCharacters(2);

		CharacterData digitChars = EnglishCharacterData.Digit;
		CharacterRule digitRule = new CharacterRule(digitChars);
		digitRule.setNumberOfCharacters(2);

		CharacterData specialChars = new CharacterData() {
			public String getErrorCode() {
				return "Password Format Invalid";
			}

			public String getCharacters() {
				return "!@#$%^&*()_+";
			}
		};
		CharacterRule splCharRule = new CharacterRule(specialChars);
		splCharRule.setNumberOfCharacters(2);

		return gen.generatePassword(10, splCharRule, lowerCaseRule,
				upperCaseRule, digitRule);
	}
	
	@Override
	public void createPasswordResetTokenForUser(String email) {
		User user = userRepository.findByEmail(email);
		if (user == null) {
			throw new UsernameNotFoundException("User credentials not found");
		}
		
		String resetLink = "http://192.168.1.55:8080/password/change?token=" + generatePasswordResetToken(user);
		
		sendEmailService.send(user.getEmail(), sendEmailService.emailBody(user.getFirstName(),resetLink));
	}
	
	@Override
	public String generatePasswordResetToken(User user) {
		String token = UUID.randomUUID().toString();
		PasswordResetToken passwordResetToken = new PasswordResetToken(user,token, LocalDateTime.now(), LocalDateTime.now().plusHours(2));
		passwordResetRepository.save(passwordResetToken);
		
		return token;
	}
	
	@Override
	public PasswordResetToken validatePasswordResetToken(String token) {
		return passwordResetRepository.findByToken(token);
	}
	
	@Override
	public boolean isTokenExpired(PasswordResetToken passwordResetToken) {
		final LocalDateTime now = LocalDateTime.now(ZoneId.systemDefault());
		return passwordResetToken.getExpiresAt().isBefore(now);
	}
	
	@Override
	public boolean isTokenFound(PasswordResetToken passToken) {
		return passToken != null;
	}
	
	
	@Override
	public void deletePasswordResetToken(long id) {
		passwordResetRepository.deleteById(id);
	}
}
