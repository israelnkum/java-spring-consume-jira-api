package com.amalitech.amalitechprojectdashboard.interfaces.projects;

import com.amalitech.amalitechprojectdashboard.models.Role;
import com.amalitech.amalitechprojectdashboard.models.User;
import com.amalitech.amalitechprojectdashboard.models.projects.Project;
import com.amalitech.amalitechprojectdashboard.models.projects.ProjectManager;
import com.amalitech.amalitechprojectdashboard.responses.ApdAuthException;

import java.util.Set;

public interface ProjectManagerServiceInterface {
	ProjectManager save(Project project, Role role, User user) throws ApdAuthException;
	
	Set<ProjectManager> findByProjectId(Long id) throws ApdAuthException;
}
