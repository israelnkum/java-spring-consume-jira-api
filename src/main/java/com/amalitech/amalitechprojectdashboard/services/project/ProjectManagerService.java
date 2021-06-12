package com.amalitech.amalitechprojectdashboard.services.project;

import com.amalitech.amalitechprojectdashboard.interfaces.projects.ProjectManagerServiceInterface;
import com.amalitech.amalitechprojectdashboard.models.Role;
import com.amalitech.amalitechprojectdashboard.models.User;
import com.amalitech.amalitechprojectdashboard.models.projects.Project;
import com.amalitech.amalitechprojectdashboard.models.projects.ProjectManager;
import com.amalitech.amalitechprojectdashboard.repositories.project.ProjectManagerRepository;
import com.amalitech.amalitechprojectdashboard.responses.ApdAuthException;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ProjectManagerService implements ProjectManagerServiceInterface {
	private final ProjectManagerRepository projectManagerRepository;
	
	public ProjectManagerService(ProjectManagerRepository projectManagerRepository) {
		this.projectManagerRepository = projectManagerRepository;
	}
	
	@Override
	public ProjectManager save(Project project, Role role, User user) throws ApdAuthException {
		ProjectManager projectManager = new ProjectManager(project, role, user);
		return projectManagerRepository.save(projectManager);
	}
	
	@Override
	public Set<ProjectManager> findByProjectId(Long id) throws ApdAuthException {
		return projectManagerRepository.findByProjectId(id);
	}
}
