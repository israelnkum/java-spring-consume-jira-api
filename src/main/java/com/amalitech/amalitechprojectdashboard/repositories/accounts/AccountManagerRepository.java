package com.amalitech.amalitechprojectdashboard.repositories.accounts;

import com.amalitech.amalitechprojectdashboard.models.accounts.AccountManager;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface AccountManagerRepository extends CrudRepository<AccountManager, Long> {
	Set<AccountManager> findByAccountId(Long id);
}
