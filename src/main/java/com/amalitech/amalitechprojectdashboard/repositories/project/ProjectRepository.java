package com.amalitech.amalitechprojectdashboard.repositories.project;

import com.amalitech.amalitechprojectdashboard.models.accounts.Account;
import com.amalitech.amalitechprojectdashboard.models.projects.Project;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ProjectRepository extends CrudRepository<Project, Long> {
	Project findFirstByOrderByIdAsc();
	Project findByJiraProjectId(String id);
	
	Boolean existsByJiraProjectId(String id);
	
	Set<Project> findByAccountId(Long id);
	
	Long countProjectByOngoingAndAccount(boolean status, Account account);
	Long countProjectByOngoing(boolean status);
	Long countProjectByAccountId(Long accountId);
}
