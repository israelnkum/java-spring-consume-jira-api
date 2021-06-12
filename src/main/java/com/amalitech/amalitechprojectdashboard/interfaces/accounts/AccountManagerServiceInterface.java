package com.amalitech.amalitechprojectdashboard.interfaces.accounts;

import com.amalitech.amalitechprojectdashboard.models.Role;
import com.amalitech.amalitechprojectdashboard.models.User;
import com.amalitech.amalitechprojectdashboard.models.accounts.Account;
import com.amalitech.amalitechprojectdashboard.models.accounts.AccountManager;
import com.amalitech.amalitechprojectdashboard.responses.ApdAuthException;

import java.util.Set;

public interface AccountManagerServiceInterface {
	AccountManager save(Account account, Role role, User user) throws ApdAuthException;
	
	Set<AccountManager> findByAccountId(Long id) throws ApdAuthException;
	
	void removeAccountManager(Long id) throws ApdAuthException;
}
