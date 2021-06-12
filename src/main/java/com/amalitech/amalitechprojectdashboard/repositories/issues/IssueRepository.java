package com.amalitech.amalitechprojectdashboard.repositories.issues;

import com.amalitech.amalitechprojectdashboard.models.issues.Issue;
import com.amalitech.amalitechprojectdashboard.models.projects.ProjectRelease;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface IssueRepository extends CrudRepository<Issue, Long> {
	boolean existsByJiraIssueId(String id);
	
	Issue findByJiraIssueId(String id);
	
	Set<Issue> findByProjectReleaseId(Long id);
	
	Long countByStatusId(Long id);
	
	Long countByStatusIdAndProjectReleaseId(long statusId, long releaseId);
	
	Long countByProjectReleaseId(Long id);
	
	@Query("select count(i.id) from Issue i where i.projectRelease=:projectReleaseId and i.status = :statusId")
	long countIssuesByProjectReleaseId(@Param("projectReleaseId") ProjectRelease projectReleaseId, @Param("statusId") Issue statusId);
}
