package com.amalitech.amalitechprojectdashboard.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDto {
	private Long id;
	private Long projectId;
	private String projectName;
	private Long accountId;
	private String description;
	private boolean deleted;
	private ArrayList<Long> userIds;
	private ArrayList<Long> roleIds;
	
	public Long getAccount() {
		return accountId;
	}
}
