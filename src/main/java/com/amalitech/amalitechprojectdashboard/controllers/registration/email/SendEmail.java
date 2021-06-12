package com.amalitech.amalitechprojectdashboard.controllers.registration.email;

import org.springframework.stereotype.Component;

@Component
public interface SendEmail {
	void send(String to, String email);
}
