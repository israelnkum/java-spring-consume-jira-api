package com.amalitech.amalitechprojectdashboard.controllers.registration.VerificationToken;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VerificationTokenRepository extends CrudRepository<VerificationToken, Long> {
	
	Optional<VerificationToken> findByToken(String token);
}
