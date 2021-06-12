package com.amalitech.amalitechprojectdashboard.repositories.roles;

import com.amalitech.amalitechprojectdashboard.models.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
	Role findByName(String role);
}
