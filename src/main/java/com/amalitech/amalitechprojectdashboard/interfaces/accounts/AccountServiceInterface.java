package com.amalitech.amalitechprojectdashboard.interfaces.accounts;

import com.amalitech.amalitechprojectdashboard.models.accounts.Account;
import com.amalitech.amalitechprojectdashboard.responses.ApdAuthException;
import com.amalitech.amalitechprojectdashboard.web.dto.AccountDto;

import java.util.List;

public interface AccountServiceInterface {
	Account save(AccountDto accountDto) throws ApdAuthException;
	
	List<Account> accounts();
	
	void editAccount(Account account);
	
	Account findAccountById(Long id) throws ApdAuthException;
	
	void deleteAccount(Long id);
	
	void assignAccountManager(AccountDto accountDto);
	
	Long countAccountByStatus(boolean status);
}
