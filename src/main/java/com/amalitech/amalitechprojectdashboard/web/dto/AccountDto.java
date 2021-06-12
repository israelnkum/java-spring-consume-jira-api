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
public class AccountDto {
	private Long id;
	
	private String accountName;
	
	private String description;
	
	private boolean deleted;
	
	private Long accountId;
	
	private ArrayList<Long> userIds;
	
	private ArrayList<Long> roleIds;
}
