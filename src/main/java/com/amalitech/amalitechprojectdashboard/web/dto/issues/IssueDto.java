package com.amalitech.amalitechprojectdashboard.web.dto.issues;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IssueDto {
	private Long id;
	private String issueName;
	private String issueDescription;
	private ArrayList<String> acceptanceCriteria;
	private Long projectReleaseId;
	private Long statusId;
	private int storyPoint;
	private String estimatedTime;
}
