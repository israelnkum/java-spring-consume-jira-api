package com.amalitech.amalitechprojectdashboard.services.project;

import com.amalitech.amalitechprojectdashboard.interfaces.projects.ProjectReleaseServiceInterface;
import com.amalitech.amalitechprojectdashboard.models.projects.Project;
import com.amalitech.amalitechprojectdashboard.models.projects.ProjectRelease;
import com.amalitech.amalitechprojectdashboard.repositories.project.ProjectReleaseRepository;
import com.amalitech.amalitechprojectdashboard.responses.ApdAuthException;
import com.amalitech.amalitechprojectdashboard.web.dto.ProjectReleaseDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ProjectReleaseService implements ProjectReleaseServiceInterface {
	private final ProjectReleaseRepository projectReleaseRepository;
	private final ProjectService projectService;
	
	public ProjectReleaseService(ProjectReleaseRepository projectReleaseRepository, ProjectService projectService) {
		this.projectReleaseRepository = projectReleaseRepository;
		this.projectService = projectService;
	}
	
	@Override
	public ProjectRelease save(ProjectReleaseDto projectReleaseDto) {
		
		Project project = projectService.findProjectById(projectReleaseDto.getProjectId());

//		ProjectRelease projectRelease = new ProjectRelease(projectReleaseDto.getReleaseName(),projectReleaseDto.getDescription(), project);
//		return projectReleaseRepository.save(projectRelease);
		return null;
	}
	
	@Override
	public List<ProjectRelease> releases() {
		return (List<ProjectRelease>) projectReleaseRepository.findAll();
	}
	
	@Override
	public void updateRelease(ProjectRelease projectRelease) {
		projectReleaseRepository.save(projectRelease);
	}
	
	@Override
	public ProjectRelease findProjectReleaseById(Long id) {
		Optional<ProjectRelease> optionalAccount = projectReleaseRepository.findById(id);
		ProjectRelease projectRelease;
		if (optionalAccount.isPresent()){
			projectRelease = optionalAccount.get();
		}else {
			throw new ApdAuthException("Project Release not found");
		}
		return  projectRelease;
	}
	
	@Override
	public Set<ProjectRelease> findByProjectId(Long id) {
		return projectReleaseRepository.findByProjectId(id);
	}
	
	@Override
	public Long countCompleted() {
		return projectReleaseRepository.countByCompleteDateNotNull();
	}
	
	@Override
	public Long countUncompleted() {
		return projectReleaseRepository.countByCompleteDateNull();
	}
}
