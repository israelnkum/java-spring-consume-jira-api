package com.amalitech.amalitechprojectdashboard.repositories.project;

import com.amalitech.amalitechprojectdashboard.models.projects.ProjectRelease;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ProjectReleaseRepository extends CrudRepository<ProjectRelease, Long> {
	ProjectRelease findByJiraReleaseId(String id);
	boolean existsByJiraReleaseId(String id);
	
	Set<ProjectRelease> findByProjectId(Long id);
	Long countByCompleteDateNull();
	Long countByCompleteDateNotNull();
}
