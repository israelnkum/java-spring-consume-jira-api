package com.amalitech.amalitechprojectdashboard.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto{
	private int id;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private ArrayList<Long> userRoles;
	private boolean verified;
	private boolean deleted;
	
	public String getUsername(){
		return this.email;
	}
	
}
