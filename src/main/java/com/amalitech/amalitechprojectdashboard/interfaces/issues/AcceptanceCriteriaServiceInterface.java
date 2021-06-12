package com.amalitech.amalitechprojectdashboard.interfaces.issues;

import com.amalitech.amalitechprojectdashboard.models.issues.AcceptanceCriteria;
import com.amalitech.amalitechprojectdashboard.models.issues.Issue;
import com.amalitech.amalitechprojectdashboard.responses.ApdAuthException;
import com.amalitech.amalitechprojectdashboard.web.dto.issues.AcceptanceCriteriaDto;

import java.util.List;
import java.util.Set;

public interface AcceptanceCriteriaServiceInterface {
	AcceptanceCriteria save(AcceptanceCriteriaDto acceptanceCriteriaDto) throws ApdAuthException;
	
	long countAcceptanceCriteria(boolean state);
	
	List<AcceptanceCriteria> acceptanceCriteria();
	
	List<AcceptanceCriteria> issueCriteria(Long id);
	
	void editAcceptanceCriteria(AcceptanceCriteria acceptanceCriteria);
	
	AcceptanceCriteria findAcceptanceCriteriaById(Long id) throws ApdAuthException;
	
	void deleteAcceptanceCriteria(Long id);
	
	Set<AcceptanceCriteria> completedCriteria(boolean isComplete, Issue issueId);
}
