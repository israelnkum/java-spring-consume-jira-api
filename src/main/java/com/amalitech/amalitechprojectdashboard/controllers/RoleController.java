package com.amalitech.amalitechprojectdashboard.controllers;

import com.amalitech.amalitechprojectdashboard.models.Role;
import com.amalitech.amalitechprojectdashboard.responses.GenericResponse;
import com.amalitech.amalitechprojectdashboard.services.roles.RoleService;
import com.amalitech.amalitechprojectdashboard.web.dto.RoleDto;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
public class RoleController {
	
	private final RoleService roleService;
	
	public RoleController(RoleService roleService) {
		this.roleService = roleService;
	}
	
	@GetMapping()
	public List<Role> getAllUsers(){
		return 	roleService.roles();
	}
	
	
	@PostMapping()
	public ResponseEntity<?> createRole(@RequestBody RoleDto roleDto){
		roleService.save(roleDto);
		return	GenericResponse.responseEntity("Role Added Successfully");
	}
	
	@PutMapping("{id}")
	public ResponseEntity<?> updateRole(@PathVariable(value ="id") Long roleId, @Validated @RequestBody RoleDto roleDto) {
		
		Role role = roleService.findRoleById(roleId);
		role.setName(roleDto.getName());
		roleService.editRole(role);
		return	GenericResponse.responseEntity("Update Successful");
	}
	
}
