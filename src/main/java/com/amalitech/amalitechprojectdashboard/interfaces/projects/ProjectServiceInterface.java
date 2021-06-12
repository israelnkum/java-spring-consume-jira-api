package com.amalitech.amalitechprojectdashboard.interfaces.projects;

import com.amalitech.amalitechprojectdashboard.models.accounts.Account;
import com.amalitech.amalitechprojectdashboard.models.projects.Project;
import com.amalitech.amalitechprojectdashboard.models.projects.ProjectRelease;
import com.amalitech.amalitechprojectdashboard.responses.ApdAuthException;
import com.amalitech.amalitechprojectdashboard.web.dto.ProjectDto;

import java.util.List;
import java.util.Set;

public interface ProjectServiceInterface {
	Project save(ProjectDto projectDto) throws ApdAuthException;
	
	List<Project> projects();
	
	void editProject(Project project);
	
	Project findProjectById(Long id) throws ApdAuthException;
	
	void deleteProject(Long id);
	
	void assignProjectManager(ProjectDto projectDto);
	
	Set<ProjectRelease> getProjectReleases(ProjectDto projectDto);
	
	Set<Project> projectByAccount(Long id);
	
	Long countProjectByOngoingAndAccount(boolean status, Account account);
	Long countProjectByOngoing(boolean status);
	Long countProjectByAccountId(Long accountId);
}
