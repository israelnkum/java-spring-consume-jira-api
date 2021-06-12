package com.amalitech.amalitechprojectdashboard.repositories.issues;

import com.amalitech.amalitechprojectdashboard.models.issues.AcceptanceCriteria;
import com.amalitech.amalitechprojectdashboard.models.issues.Issue;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface AcceptanceCriteriaRepository extends CrudRepository<AcceptanceCriteria, Long> {
	
	List<AcceptanceCriteria> findByIssueId(Long id);
	
	@Query("SELECT a FROM AcceptanceCriteria a WHERE a.isComplete = :isComplete and a.issue = :issue")
	Set<AcceptanceCriteria> findByComplete(boolean isComplete, Issue issue);
	
	
	Long countByIsComplete(boolean complete);
	
//	long countByComplete(boolean complete);
}
