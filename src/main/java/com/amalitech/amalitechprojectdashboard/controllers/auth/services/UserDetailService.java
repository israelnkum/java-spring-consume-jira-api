package com.amalitech.amalitechprojectdashboard.controllers.auth.services;

import com.amalitech.amalitechprojectdashboard.models.Role;
import com.amalitech.amalitechprojectdashboard.repositories.user.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class UserDetailService implements UserDetailsService {
	private final UserRepository userRepository;
	
	public UserDetailService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		com.amalitech.amalitechprojectdashboard.models.User user = userRepository.findByEmail(userName);
		if (user == null){
			throw new UsernameNotFoundException("Username not found");
		}
		
		return new User(user.getUsername(),user.getPassword(),getAuthorities(user.getRoles()));
//		return User.withUsername(user.getUsername()).password(user.getPassword()).authorities(getAuthorities(user)).build();
//		return User.withUsername(user.getUsername()).password(user.getPassword()).disabled(enabled).authorities(new ArrayList<>()).build();
	}
	
	/*private static Collection<? extends GrantedAuthority> getAuthorities(com.amalitech.amalitechprojectdashboard.models.User user){
		String [] userRoles = user.getRoles().stream().map(Role::getName).toArray(String[]::new);
		return AuthorityUtils.createAuthorityList(userRoles);
	}*/
	
	private static ArrayList<? extends GrantedAuthority> getAuthorities(Collection<Role> roles){
		return (ArrayList<? extends GrantedAuthority>) roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
	}
}
