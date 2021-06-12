package com.amalitech.amalitechprojectdashboard.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProjectReleaseDto {
	private Long id;
	private String releaseName;
	private String description;
	private Long projectId;
}
