package com.amalitech.amalitechprojectdashboard.web.dto.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StatusDto {
	private long id;
	private String statusName;
	private String statusDescription;
	private Long projectId;
	private Long accountId;
}
