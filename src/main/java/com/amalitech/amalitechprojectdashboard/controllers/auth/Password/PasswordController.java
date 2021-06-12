package com.amalitech.amalitechprojectdashboard.controllers.auth.Password;

import com.amalitech.amalitechprojectdashboard.controllers.auth.security.models.AuthenticationRequest;
import com.amalitech.amalitechprojectdashboard.models.User;
import com.amalitech.amalitechprojectdashboard.responses.ApdAuthException;
import com.amalitech.amalitechprojectdashboard.responses.GenericResponse;
import com.amalitech.amalitechprojectdashboard.services.user.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/password")
public class PasswordController {
	private final UserService userService;
	private final PasswordResetService passwordResetService;
	
	public PasswordController(UserService userService, PasswordResetService passwordResetService) {
		this.userService = userService;
		this.passwordResetService = passwordResetService;
	}
	
	@PostMapping("check")
	public String checkDefaultPassword(@RequestBody AuthenticationRequest authenticationRequest){
		userService.validateUser(authenticationRequest.getUsername(), authenticationRequest.getPassword());
		return "Success";
	}
	
	@PostMapping("reset")
	public String sendPasswordResetToken(@RequestBody AuthenticationRequest authenticationRequest) {
		passwordResetService.createPasswordResetTokenForUser(authenticationRequest.getEmail());
		return "successful";
	}
	
	@PostMapping("change")
	@Transactional
	public ResponseEntity<?> changePassword(@RequestBody PasswordDto passwordDto){
		PasswordResetToken token = passwordResetService.validatePasswordResetToken(passwordDto.getToken());
		if(token == null)
			throw new ApdAuthException("Token Not Found");
		if (!passwordResetService.isTokenExpired(token)){
			User user = userService.findUserById(token.getUser().getId());
			userService.changePassword(user, passwordDto.getNewPassword());
			
			passwordResetService.deletePasswordResetToken(token.getId());
			return GenericResponse.responseEntity("Password Changed Successfully");
		}
		throw new ApdAuthException("Token expired");
	}
}
