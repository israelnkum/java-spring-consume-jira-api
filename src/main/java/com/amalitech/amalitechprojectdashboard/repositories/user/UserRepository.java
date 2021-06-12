package com.amalitech.amalitechprojectdashboard.repositories.user;

import com.amalitech.amalitechprojectdashboard.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User,Long> {
	
//	@Query("select user.id, user.firstName, user.lastName from User user")
//	@NotNull
//	List<Object> findAllUsers();
	
	User findByEmail(String email);
	
//	User findByUsername(String username);
}
