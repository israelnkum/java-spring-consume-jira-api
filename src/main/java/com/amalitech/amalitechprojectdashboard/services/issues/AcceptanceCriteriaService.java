package com.amalitech.amalitechprojectdashboard.services.issues;

import com.amalitech.amalitechprojectdashboard.interfaces.issues.AcceptanceCriteriaServiceInterface;
import com.amalitech.amalitechprojectdashboard.models.issues.AcceptanceCriteria;
import com.amalitech.amalitechprojectdashboard.models.issues.Issue;
import com.amalitech.amalitechprojectdashboard.repositories.issues.AcceptanceCriteriaRepository;
import com.amalitech.amalitechprojectdashboard.responses.ApdAuthException;
import com.amalitech.amalitechprojectdashboard.web.dto.issues.AcceptanceCriteriaDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class AcceptanceCriteriaService implements AcceptanceCriteriaServiceInterface {
	private final AcceptanceCriteriaRepository acceptanceCriteriaRepository;
	private final IssueService issueService;
	public AcceptanceCriteriaService(AcceptanceCriteriaRepository acceptanceCriteriaRepository, IssueService issueService) {
		this.acceptanceCriteriaRepository = acceptanceCriteriaRepository;
		this.issueService = issueService;
	}
	
	@Override
	public AcceptanceCriteria save(AcceptanceCriteriaDto acceptanceCriteriaDto) throws ApdAuthException {
		Issue issue = issueService.findIssueById(acceptanceCriteriaDto.getIssueId());
		AcceptanceCriteria acceptanceCriteria = new AcceptanceCriteria(acceptanceCriteriaDto.getDescription(),issue);
		acceptanceCriteriaRepository.save(acceptanceCriteria);
		return acceptanceCriteria;
	}
	
	@Override
	public long countAcceptanceCriteria(boolean complete) {
		return acceptanceCriteriaRepository.countByIsComplete(complete);
	}
	
	@Override
	public List<AcceptanceCriteria> acceptanceCriteria() {
		return (List<AcceptanceCriteria>) acceptanceCriteriaRepository.findAll();
	}
	
	@Override
	public List<AcceptanceCriteria> issueCriteria(Long id) {
		return acceptanceCriteriaRepository.findByIssueId(id);
	}
	
	@Override
	public void editAcceptanceCriteria(AcceptanceCriteria acceptanceCriteria) {
		acceptanceCriteriaRepository.save(acceptanceCriteria);
	}
	
	@Override
	public AcceptanceCriteria findAcceptanceCriteriaById(Long id) throws ApdAuthException {
		Optional<AcceptanceCriteria> optionalAcceptanceCriteria = acceptanceCriteriaRepository.findById(id);
		AcceptanceCriteria acceptanceCriteria;
		if (optionalAcceptanceCriteria.isPresent()){
			acceptanceCriteria = optionalAcceptanceCriteria.get();
		}else {
			throw new ApdAuthException("Acceptance Criteria not found");
		}
		return  acceptanceCriteria;
	}
	
	@Override
	public void deleteAcceptanceCriteria(Long id) {
		acceptanceCriteriaRepository.deleteById(id);
	}
	
	
	
	@Override
	public Set<AcceptanceCriteria> completedCriteria(boolean isComplete, Issue issue) {
		return acceptanceCriteriaRepository.findByComplete(isComplete,issue);
	}
}
