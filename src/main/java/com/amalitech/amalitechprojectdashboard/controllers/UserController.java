package com.amalitech.amalitechprojectdashboard.controllers;

import com.amalitech.amalitechprojectdashboard.models.Role;
import com.amalitech.amalitechprojectdashboard.models.User;
import com.amalitech.amalitechprojectdashboard.responses.GenericResponse;
import com.amalitech.amalitechprojectdashboard.services.user.UserService;
import com.amalitech.amalitechprojectdashboard.web.dto.UserDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/users")
public class UserController {
	
	private final UserService userService;
	
	public UserController(UserService userService){
		this.userService = userService;
	}
	
	//list all users
	@GetMapping()
	public List<User> getAllUsers(){
		return 	userService.users();
	}
	
	// Post request to create user account
	@PostMapping()
	public ResponseEntity<?> addUser(@RequestBody UserDto userDto){
		//userService.save(userDto);
//		return (ResponseEntity<?>) userDto.getUserRoles();
		return	GenericResponse.responseEntity("Registered Successfully");
	}
	
	// get single user
	@PostMapping("/user")
	public ResponseEntity<?> getUserData( @Validated @RequestBody UserDto userDto) {
		Map<String, Object> userDetails = details(userDto.getEmail());
	
		return new ResponseEntity<>(userDetails, HttpStatus.OK);
		
	}
	// get user roles
	@PostMapping("/user/roles")
	public Set<Role> getUserRoles(@Validated @RequestBody UserDto userDto) {
		User user = userService.findUserByEmail(userDto.getEmail());
		return  user.getRoles();
	}
	
	// get users account
	@PostMapping("/user/accounts")
	public ResponseEntity<?> getUserAccounts(Authentication authentication) {
		Map<String, Object> userDetails = details(authentication.getName());
		User user = userService.findUserByEmail(authentication.getName());
		//userDetails.put("roles",authentication.getAuthorities());
		
		
		return new ResponseEntity<>(user.getAccountManagers(), HttpStatus.OK);
	}
	
	@GetMapping("/current")
	@ResponseBody
	public ResponseEntity<?> currentUserName(Authentication authentication) {
		Map<String, Object> userDetails = details(authentication.getName());
		userDetails.put("roles",authentication.getAuthorities());
		return new ResponseEntity<>(userDetails, HttpStatus.OK);
	}
	
	public Map<String, Object> details(String email){
		User user = userService.findUserByEmail(email);
		
		Map<String, Object> userDetails = new HashMap<>();
		userDetails.put("id", user.getId());
		userDetails.put("firstName", user.getFirstName());
		userDetails.put("lastName", user.getLastName());
		userDetails.put("email", user.getEmail());
		userDetails.put("roles",user.getRoles());
		userDetails.put("accounts", user.getAccountManagers());
		userDetails.put("projects", user.getProjectManagers());
		return userDetails;
	}
	
	
	// edit user info
	@PutMapping("/{id}")
	public ResponseEntity<?> updateEmployee(@PathVariable(value ="id") Long userId, @Validated @RequestBody UserDto userDto) {
		
		User user = userService.findUserById(userId);
		
		user.setFirstName(userDto.getFirstName());
		user.setLastName(userDto.getLastName());
		user.setEmail(userDto.getEmail());
		
		userService.editUserInfo(user);
		return	GenericResponse.responseEntity("User Info Updated Successfully");
	}
	
	@DeleteMapping("/{id}")
	public  ResponseEntity<?> deleteEmployee(@PathVariable long id){
		userService.deleteUser(id);
		return	GenericResponse.responseEntity("User Account Removed Successfully");
	}
}
