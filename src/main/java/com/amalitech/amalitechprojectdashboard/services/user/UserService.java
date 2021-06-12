package com.amalitech.amalitechprojectdashboard.services.user;

import com.amalitech.amalitechprojectdashboard.controllers.auth.Password.PasswordResetService;
import com.amalitech.amalitechprojectdashboard.interfaces.UserServiceInterface;
import com.amalitech.amalitechprojectdashboard.models.User;
import com.amalitech.amalitechprojectdashboard.repositories.user.UserRepository;
import com.amalitech.amalitechprojectdashboard.responses.ApdAuthException;
import com.amalitech.amalitechprojectdashboard.services.roles.RoleService;
import com.amalitech.amalitechprojectdashboard.web.dto.UserDto;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService implements UserServiceInterface {
	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final PasswordResetService passwordResetService;
	private final RoleService roleService;
	
	public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, PasswordResetService passwordResetService, RoleService roleService) {
		this.userRepository = userRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.passwordResetService = passwordResetService;
		this.roleService = roleService;
	}
	
	@Override
	public User save(UserDto userDto) throws ApdAuthException {
		
		assert false;
		User user = new User(userDto.getFirstName(), userDto.getLastName(), userDto.getUsername(),userDto.getPassword());
		userDto.getUserRoles().forEach(item -> {
			user.addRole(roleService.findRoleById(item));
		});
		
		userRepository.save(user);
		// TODO: send confirmation token
		/*String token = UUID.randomUUID().toString();
		VerificationToken verificationToken = new VerificationToken(token, LocalDateTime.now(), LocalDateTime.now().plusMinutes(15),user);
		verificationTokenService.saveVerificationToken(verificationToken);*/
		
		passwordResetService.createPasswordResetTokenForUser(userDto.getEmail());
		return user;
	}
	
	@Override
	public List<User> users() {
		return (List<User>) userRepository.findAll();
	}
	
	@Override
	public User findUserById(Long id) throws ApdAuthException {
		Optional<User> optionalUser = userRepository.findById(id);
		User user;
		if (optionalUser.isPresent()){
			user = optionalUser.get();
		}else {
			throw new ApdAuthException("User not found");
		}
		
		return  user;
	}
	
	@Override
	public User findUserByEmail(String email) throws ApdAuthException {
		return userRepository.findByEmail(email);
	}
	
	@Override
	public void validateUser(String email, String password) throws ApdAuthException {
		if (email != null) email =  email.toLowerCase();
		
		try{
			User user = userRepository.findByEmail(email);
			
			if (user == null)
				throw new ApdAuthException("Credentials do no match our records");
			
			if (!bCryptPasswordEncoder.matches(password,user.getPassword()))
				throw new ApdAuthException("Invalid email/password");
		}catch (EmptyResultDataAccessException e){
			throw new ApdAuthException("Invalid email/password");
		}
	}
	
	@Override
	public void changePassword(User user, String password) {
		user.setPassword(bCryptPasswordEncoder.encode(password));
		userRepository.save(user);
	}
	
	@Override
	public void editUserInfo(User user) {
		userRepository.save(user);
	}
	
	@Override
	public void deleteUser(Long id) {
		User user = findUserById(id);
		user.setDeleted(true);
	}
}
