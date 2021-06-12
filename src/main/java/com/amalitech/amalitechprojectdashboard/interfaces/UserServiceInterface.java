package com.amalitech.amalitechprojectdashboard.interfaces;

import com.amalitech.amalitechprojectdashboard.models.User;
import com.amalitech.amalitechprojectdashboard.responses.ApdAuthException;
import com.amalitech.amalitechprojectdashboard.web.dto.UserDto;

import java.util.List;

public interface UserServiceInterface {
	
	User save(UserDto userDto) throws ApdAuthException;

	List<User> users();
	
	User findUserById(Long id) throws ApdAuthException;
	
	User findUserByEmail(String email) throws ApdAuthException;
	
	void validateUser(String email, String password ) throws ApdAuthException;
	
	void changePassword(User user, String password);
	
	void editUserInfo(User user);
	
	void deleteUser(Long id);

}
