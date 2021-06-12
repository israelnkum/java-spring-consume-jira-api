package com.amalitech.amalitechprojectdashboard.repositories.project;

import com.amalitech.amalitechprojectdashboard.models.projects.ProjectManager;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ProjectManagerRepository extends CrudRepository<ProjectManager, Long> {
	Set<ProjectManager> findByProjectId(Long id);
}
