package com.amalitech.amalitechprojectdashboard.interfaces.issues;

import com.amalitech.amalitechprojectdashboard.models.issues.Issue;
import com.amalitech.amalitechprojectdashboard.models.projects.ProjectRelease;
import com.amalitech.amalitechprojectdashboard.responses.ApdAuthException;
import com.amalitech.amalitechprojectdashboard.web.dto.issues.IssueDto;

import java.util.List;
import java.util.Set;

public interface IssueServiceInterface {
	Issue save(IssueDto issueDto) throws ApdAuthException;
	
	List<Issue> issues();
	
	void editIssue(Issue issue);
	
	Issue findIssueById(Long id) throws ApdAuthException;
	
	void deleteIssue(Long id);
	
	Set<Issue> findByProjectReleaseId(Long id);
	
	long countIssueStatuses(Long id);
	
	long countIssueByProjectReleaseId(Long id);
	
	Long countByStatusIdAndProjectReleaseId(long statusId, long releaseId);
	
	long countIssuesByProjectReleaseId(ProjectRelease projectRelease, Issue issue);
}
