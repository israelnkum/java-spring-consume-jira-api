package com.amalitech.amalitechprojectdashboard.controllers.auth;

import com.amalitech.amalitechprojectdashboard.models.Role;
import com.amalitech.amalitechprojectdashboard.models.User;
import com.amalitech.amalitechprojectdashboard.models.accounts.Account;
import com.amalitech.amalitechprojectdashboard.models.utils.Status;
import com.amalitech.amalitechprojectdashboard.repositories.accounts.AccountRepository;
import com.amalitech.amalitechprojectdashboard.repositories.roles.RoleRepository;
import com.amalitech.amalitechprojectdashboard.repositories.user.UserRepository;
import com.amalitech.amalitechprojectdashboard.repositories.utils.StatusRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements ApplicationRunner {
	private final RoleRepository roleRepository;
	private final AccountRepository accountRepository;
	private final StatusRepository statusRepository;
	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	public DataLoader(RoleRepository roleRepository, AccountRepository accountRepository, StatusRepository statusRepository, UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.roleRepository = roleRepository;
		this.accountRepository = accountRepository;
		this.statusRepository = statusRepository;
		this.userRepository = userRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		/*createRoles(defaultRoles);
		createAccounts(defaultAccounts);
		createStatuses(defaultStatuses);
		createUser();*/
	}
	
	private final String[] defaultRoles = {"Main Admin","Main Observer","Account Admin","Account Observer","Project Admin","Developer"};
	private final String[][] defaultAccounts = {{"Amalitech","Default Account"}};
	private final String[][] defaultStatuses = {{"Backlog","Backlog"},{"To Do","To Do"},{"In Progress","In Progress"},{"Done","Done"}};
	
	
	private void createRoles(String[] roles){
		for (String role : roles) {
			Role role1 = roleRepository.findByName(role);
			if (role1 == null)
				roleRepository.save(new Role(role));
		}
	}
	
	private void createAccounts(String[][] accounts){
		for (String[] account : accounts) {
			for (int i = 0; i < account.length; i++) {
				Account account1 = accountRepository.findByAccountName(account[0]);
				if (account1 == null)
					accountRepository.save(new Account(account[0],account[1]));
			}
		}
	}
	
	private void createStatuses(String[][] statuses){
		for (String[] status : statuses) {
			for (int i = 0; i < status.length; i++) {
				Status status1 = statusRepository.findByStatusName(status[0]);
				if (status1 == null)
					statusRepository.save(new Status(status[0],status[1]));
			}
		}
	}
	
	private void createUser(){
		User findUser = userRepository.findByEmail("main.admin@amalitech.com");
		if (findUser == null){
			User user = new User("Main","Admin","main.admin@amalitech.com",bCryptPasswordEncoder.encode("main.admin"));
			user.addRole(roleRepository.findByName("Main Admin"));
			userRepository.save(user);
		}
	}
	
}
