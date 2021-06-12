package com.amalitech.amalitechprojectdashboard.interfaces.projects;

import com.amalitech.amalitechprojectdashboard.models.projects.ProjectRelease;
import com.amalitech.amalitechprojectdashboard.web.dto.ProjectReleaseDto;

import java.util.List;
import java.util.Set;

public interface ProjectReleaseServiceInterface {
	ProjectRelease save(ProjectReleaseDto projectReleaseDto);
	
	List<ProjectRelease> releases();
	
	void updateRelease(ProjectRelease projectRelease);
	
	ProjectRelease findProjectReleaseById(Long id);
	
	Set<ProjectRelease> findByProjectId(Long id);
	
	Long countCompleted();
	Long countUncompleted();
}
