package com.amalitech.amalitechprojectdashboard.controllers.registration;

import com.amalitech.amalitechprojectdashboard.controllers.registration.email.ValidateEmail;
import com.amalitech.amalitechprojectdashboard.models.User;
import com.amalitech.amalitechprojectdashboard.services.user.UserService;
import com.amalitech.amalitechprojectdashboard.web.dto.UserDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationService {
	
	private final ValidateEmail validateEmail;
	private final UserService userService;
	
	public User register(UserDto userDto) {
		validateEmail.test(userDto.getEmail());
		validateEmail.checkIfExist(userDto.getEmail());
		return 	userService.save(userDto);
//		return ResponseEntity.ok("Registration Successful");
	}
}
