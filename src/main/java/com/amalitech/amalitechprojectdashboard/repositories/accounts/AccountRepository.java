package com.amalitech.amalitechprojectdashboard.repositories.accounts;

import com.amalitech.amalitechprojectdashboard.models.accounts.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends CrudRepository<Account, Long> {
	Account findFirstByOrderByIdAsc();
	
	Account findByAccountName(String name);
	
	Long countAccountByActive(boolean status);
}
