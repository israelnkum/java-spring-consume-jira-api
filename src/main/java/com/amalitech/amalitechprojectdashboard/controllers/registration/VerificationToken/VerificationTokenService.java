package com.amalitech.amalitechprojectdashboard.controllers.registration.VerificationToken;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class VerificationTokenService {
	private final VerificationTokenRepository verificationTokenRepository;
	
	public void saveVerificationToken(VerificationToken verificationToken){
		verificationTokenRepository.save(verificationToken);
	}
}
