package com.amalitech.amalitechprojectdashboard.services.accounts;

import com.amalitech.amalitechprojectdashboard.interfaces.accounts.AccountManagerServiceInterface;
import com.amalitech.amalitechprojectdashboard.models.Role;
import com.amalitech.amalitechprojectdashboard.models.User;
import com.amalitech.amalitechprojectdashboard.models.accounts.Account;
import com.amalitech.amalitechprojectdashboard.models.accounts.AccountManager;
import com.amalitech.amalitechprojectdashboard.repositories.accounts.AccountManagerRepository;
import com.amalitech.amalitechprojectdashboard.responses.ApdAuthException;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AccountManagerService implements AccountManagerServiceInterface {
	private final AccountManagerRepository accountManagerRepository;
	
	public AccountManagerService(AccountManagerRepository accountManagerRepository) {
		this.accountManagerRepository = accountManagerRepository;
	}
	
	@Override
	public AccountManager save(Account account, Role role, User user) throws ApdAuthException {
		AccountManager accountManager = new AccountManager(account,role,user);
		return accountManagerRepository.save(accountManager);
	}
	
	@Override
	public Set<AccountManager> findByAccountId(Long id) {
		return accountManagerRepository.findByAccountId(id);
	}
	
	@Override
	public void removeAccountManager(Long id) {
		 accountManagerRepository.deleteById(id);
	}
}
