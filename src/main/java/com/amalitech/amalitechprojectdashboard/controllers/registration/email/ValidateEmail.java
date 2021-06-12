package com.amalitech.amalitechprojectdashboard.controllers.registration.email;

import com.amalitech.amalitechprojectdashboard.responses.ApdAuthException;
import com.amalitech.amalitechprojectdashboard.repositories.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Predicate;
import java.util.regex.Pattern;
@AllArgsConstructor
@Service
public class ValidateEmail implements Predicate<String> {
	private final UserRepository userRepository;
	
	@Override
	public boolean test(String email) {
		Pattern pattern = Pattern.compile("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");
		if (!pattern.matcher(email).matches())
			throw  new ApdAuthException("Invalid Email Format");
		return true;
	}
	
	public void checkIfExist(String email){
		if (userRepository.findByEmail(email.toLowerCase()) != null)
			throw new ApdAuthException("Email already in use");
	}
}
