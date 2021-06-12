package com.amalitech.amalitechprojectdashboard.services.roles;

import com.amalitech.amalitechprojectdashboard.interfaces.RoleServiceInterface;
import com.amalitech.amalitechprojectdashboard.models.Role;
import com.amalitech.amalitechprojectdashboard.repositories.roles.RoleRepository;
import com.amalitech.amalitechprojectdashboard.responses.ApdAuthException;
import com.amalitech.amalitechprojectdashboard.web.dto.RoleDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RoleService implements RoleServiceInterface {
	private final RoleRepository roleRepository;
	
	public RoleService(RoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}
	
	@Override
	public Role save(RoleDto roleDto) throws ApdAuthException {
		Role role = new Role(roleDto.getName());
		return roleRepository.save(role);
	}
	
	@Override
	public List<Role> roles() {
		return (List<Role>) roleRepository.findAll();
	}
	
	@Override
	public void editRole(Role role) {
		roleRepository.save(role);
	}
	
	@Override
	public Role findRoleById(Long id) throws ApdAuthException {
		Optional<Role> optionalRole = roleRepository.findById(id);
		Role role;
		if (optionalRole.isPresent()){
			role = optionalRole.get();
		}else {
			throw new ApdAuthException("Role not found");
		}
		return  role;
	}
	
}
