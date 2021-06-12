package com.amalitech.amalitechprojectdashboard.interfaces;

import com.amalitech.amalitechprojectdashboard.models.Role;
import com.amalitech.amalitechprojectdashboard.responses.ApdAuthException;
import com.amalitech.amalitechprojectdashboard.web.dto.RoleDto;

import java.util.List;

public interface RoleServiceInterface {
	Role save(RoleDto roleDto) throws ApdAuthException;
	
	List<Role> roles();
	
	void editRole(Role role);
	
	Role findRoleById(Long id) throws ApdAuthException;
	
}
