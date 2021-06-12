package com.amalitech.amalitechprojectdashboard.controllers.auth.security.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {
	private String username;
	private String email;
	private String password;
	
}
