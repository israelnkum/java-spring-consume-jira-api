package com.amalitech.amalitechprojectdashboard.models.utils;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {
	
	@Override
	public Optional<String> getCurrentAuditor() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null){
			return Optional.ofNullable(auth.getName());
		}
		return Optional.of("SystemGenerated");
	}
}
