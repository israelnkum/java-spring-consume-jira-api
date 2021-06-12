package com.amalitech.amalitechprojectdashboard.controllers.registration;

import com.amalitech.amalitechprojectdashboard.models.User;
import com.amalitech.amalitechprojectdashboard.web.dto.UserDto;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("registration")
@AllArgsConstructor
public class RegistrationController {
	
	private final RegistrationService registrationService;
	
	@PostMapping()
	public User register(@RequestBody UserDto userDto) {
		return 	registrationService.register(userDto);
	}
}
