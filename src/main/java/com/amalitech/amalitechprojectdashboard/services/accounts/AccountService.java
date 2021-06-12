package com.amalitech.amalitechprojectdashboard.services.accounts;

import com.amalitech.amalitechprojectdashboard.interfaces.accounts.AccountServiceInterface;
import com.amalitech.amalitechprojectdashboard.models.Role;
import com.amalitech.amalitechprojectdashboard.models.User;
import com.amalitech.amalitechprojectdashboard.models.accounts.Account;
import com.amalitech.amalitechprojectdashboard.repositories.accounts.AccountRepository;
import com.amalitech.amalitechprojectdashboard.responses.ApdAuthException;
import com.amalitech.amalitechprojectdashboard.services.roles.RoleService;
import com.amalitech.amalitechprojectdashboard.services.user.UserService;
import com.amalitech.amalitechprojectdashboard.web.dto.AccountDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AccountService implements AccountServiceInterface {
	private final AccountRepository accountRepository;
	private final UserService userService;
	private final AccountManagerService accountManagerService;
	private final RoleService roleService;
	
	public AccountService(AccountRepository accountRepository, UserService userService, AccountManagerService accountManagerService, RoleService roleService) {
		this.accountRepository = accountRepository;
		this.userService = userService;
		this.accountManagerService = accountManagerService;
		this.roleService = roleService;
	}
	
	@Override
	public Account save(AccountDto accountDto) throws ApdAuthException {
		Account account = new Account(accountDto.getAccountName(),accountDto.getDescription());
		accountRepository.save(account);
		return account;
	}
	
	@Override
	public List<Account> accounts() {
		return (List<Account>) accountRepository.findAll();
	}
	
	@Override
	public void editAccount(Account account) {
		accountRepository.save(account);
	}
	
	public Account find(Long id){
		EntityManager entityManager = null;
		assert false;
		
		return entityManager.find(Account.class,id);
	}
	
	@Override
	public Account findAccountById(Long id) throws ApdAuthException {
		Optional<Account> optionalAccount = accountRepository.findById(id);
		Account account;
		if (optionalAccount.isPresent()){
			account = optionalAccount.get();
		}else {
			throw new ApdAuthException("Account not found");
		}
		return  account;
	}
	
	@Override
	public void deleteAccount(Long id) {
		Account account = findAccountById(id);
		account.setDeleted(true);
	}
	
	@Override
	public void assignAccountManager(AccountDto accountDto) {
		Account account = findAccountById(accountDto.getAccountId());
		accountDto.getUserIds().forEach(userId -> {
			User user = userService.findUserById(userId);
			accountDto.getRoleIds().forEach(roleId -> {
				Role role = roleService.findRoleById(roleId);
				accountManagerService.save(account,role,user);
				user.addRole(role);
				userService.editUserInfo(user);
			});
		});
	}
	
	@Override
	public Long countAccountByStatus(boolean status) {
		return accountRepository.countAccountByActive(status);
	}
}
