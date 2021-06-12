package com.amalitech.amalitechprojectdashboard.services.issues;

import com.amalitech.amalitechprojectdashboard.interfaces.issues.IssueServiceInterface;
import com.amalitech.amalitechprojectdashboard.models.issues.AcceptanceCriteria;
import com.amalitech.amalitechprojectdashboard.models.issues.Issue;
import com.amalitech.amalitechprojectdashboard.models.projects.ProjectRelease;
import com.amalitech.amalitechprojectdashboard.repositories.issues.AcceptanceCriteriaRepository;
import com.amalitech.amalitechprojectdashboard.repositories.issues.IssueRepository;
import com.amalitech.amalitechprojectdashboard.responses.ApdAuthException;
import com.amalitech.amalitechprojectdashboard.services.project.ProjectReleaseService;
import com.amalitech.amalitechprojectdashboard.services.utils.StatusService;
import com.amalitech.amalitechprojectdashboard.web.dto.issues.IssueDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class IssueService implements IssueServiceInterface {
	private final IssueRepository issueRepository;
	private final ProjectReleaseService projectReleaseService;
	private final AcceptanceCriteriaRepository acceptanceCriteriaRepository;
	private final StatusService statusService;
	public IssueService(IssueRepository issueRepository, ProjectReleaseService projectReleaseService, AcceptanceCriteriaRepository acceptanceCriteriaRepository, StatusService statusService) {
		this.issueRepository = issueRepository;
		this.projectReleaseService = projectReleaseService;
		this.acceptanceCriteriaRepository = acceptanceCriteriaRepository;
		this.statusService = statusService;
	}
	
	@Override
	public Issue save(IssueDto issueDto) throws ApdAuthException {
		ProjectRelease projectRelease = projectReleaseService.findProjectReleaseById(issueDto.getProjectReleaseId());
		
		Issue issue = new Issue(issueDto.getIssueName(),issueDto.getIssueDescription(), null, projectRelease,statusService.findStatusById(issueDto.getStatusId()), issueDto.getStoryPoint(), issueDto.getEstimatedTime());
		issueRepository.save(issue);
		
		issueDto.getAcceptanceCriteria().forEach(description -> {
			AcceptanceCriteria acceptanceCriteria = new AcceptanceCriteria(description,issue);
			acceptanceCriteriaRepository.save(acceptanceCriteria);
		});
		return issue;
	}
	
	@Override
	public List<Issue> issues() {
		return (List<Issue>) issueRepository.findAll();
	}
	
	@Override
	public void editIssue(Issue issue) {
		issueRepository.save(issue);
	}
	
	@Override
	public Issue findIssueById(Long id) throws ApdAuthException {
		Optional<Issue> optionalIssue = issueRepository.findById(id);
		Issue issue;
		if (optionalIssue.isPresent()){
			issue = optionalIssue.get();
		}else {
			throw new ApdAuthException("Issue not found");
		}
		return  issue;
	}
	
	@Override
	public void deleteIssue(Long id) {
		issueRepository.deleteById(id);
	}
	
	@Override
	public Set<Issue> findByProjectReleaseId(Long id) {
		return issueRepository.findByProjectReleaseId(id);
	}
	
	@Override
	public long countIssueStatuses(Long id) {
		return issueRepository.countByStatusId(id);
	}
	
	@Override
	public long countIssueByProjectReleaseId(Long id) {
		return issueRepository.countByProjectReleaseId(id);
	}
	
	@Override
	public Long countByStatusIdAndProjectReleaseId(long statusId, long releaseId) {
		return issueRepository.countByStatusIdAndProjectReleaseId(statusId, releaseId);
	}
	
	@Override
	public long countIssuesByProjectReleaseId(ProjectRelease projectRelease, Issue issue) {
		return issueRepository.countIssuesByProjectReleaseId(projectRelease, issue);
	}
}
