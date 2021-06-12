package com.amalitech.amalitechprojectdashboard.web.dto.issues;

import com.amalitech.amalitechprojectdashboard.models.issues.Issue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AcceptanceCriteriaDto {
	private Long id;
	private String description;
	private boolean isComplete;
	private Long issueId;
	private Issue issue;
}
