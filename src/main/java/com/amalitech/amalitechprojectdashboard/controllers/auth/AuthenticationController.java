package com.amalitech.amalitechprojectdashboard.controllers.auth;

import com.amalitech.amalitechprojectdashboard.controllers.auth.security.models.AuthenticationRequest;
import com.amalitech.amalitechprojectdashboard.controllers.auth.security.models.AuthenticationResponse;
import com.amalitech.amalitechprojectdashboard.controllers.auth.services.UserDetailService;
import com.amalitech.amalitechprojectdashboard.controllers.auth.util.JwtUtil;
import com.amalitech.amalitechprojectdashboard.responses.ApdAuthException;
import com.amalitech.amalitechprojectdashboard.services.user.UserService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
public class AuthenticationController {
	private final AuthenticationManager authenticationManager;
	private final UserDetailService userDetailService;
	private final JwtUtil jwtUtil;
	private final UserService userService;
	
	private final ApplicationEventPublisher applicationEventPublisher;
	
	public AuthenticationController(UserDetailService userDetailService, JwtUtil jwtUtil, AuthenticationManager authenticationManager, UserService userService, ApplicationEventPublisher applicationEventPublisher){
		this.userDetailService = userDetailService;
		this.jwtUtil = jwtUtil;
		this.authenticationManager = authenticationManager;
		this.userService = userService;
		this.applicationEventPublisher = applicationEventPublisher;
	}
	
	@PostMapping("/wall")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception{
		try{
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
			);
		}catch (BadCredentialsException e){
			throw new ApdAuthException("Credentials do not match our records!");
		}
		
		
		final UserDetails userDetails = userDetailService.loadUserByUsername(authenticationRequest.getUsername());
		final String token = jwtUtil.generateToken(userDetails);
		
		return ResponseEntity.ok(new AuthenticationResponse(token));
		/*Map<String, String> map = new HashMap<>();
		map.put("username",authenticationRequest.getUsername());
		map.put("password",authenticationRequest.getPassword());
		return ResponseEntity.ok(map);*/
	}
	
	
	@PostMapping("/change-password")
	public ResponseEntity<?> changePassword(){
		return null;
	}
	
	/*@PostMapping("/auth-register")
	public ResponseEntity<Map<String, String>> registerUserAccount(@RequestBody UserDto userDto, HttpServletRequest request, Errors errors) {
		User registered = userService.save(userDto);
		//String appUrl = request.getContextPath();
		
		//applicationEventPublisher.publishEvent(new OnRegistrationCompleteEvent(registered, request.getLocale(), appUrl));
		
		Map<String, String> map = new HashMap<>();
		map.put("message","Registered Successfully");
		return new ResponseEntity<>(map, HttpStatus.OK);
	}*/
	
}
