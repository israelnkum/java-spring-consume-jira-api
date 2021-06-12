package com.amalitech.amalitechprojectdashboard.controllers.auth.Password;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PasswordDto {
	private String oldPassword;
	private String token;
	private String newPassword;
}
