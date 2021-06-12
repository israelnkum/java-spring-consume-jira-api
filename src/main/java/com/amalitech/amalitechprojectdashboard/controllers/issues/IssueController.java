package com.amalitech.amalitechprojectdashboard.controllers.issues;

import com.amalitech.amalitechprojectdashboard.models.accounts.Account;
import com.amalitech.amalitechprojectdashboard.models.issues.Issue;
import com.amalitech.amalitechprojectdashboard.models.projects.Project;
import com.amalitech.amalitechprojectdashboard.models.projects.ProjectRelease;
import com.amalitech.amalitechprojectdashboard.models.utils.Status;
import com.amalitech.amalitechprojectdashboard.responses.GenericResponse;
import com.amalitech.amalitechprojectdashboard.services.accounts.AccountService;
import com.amalitech.amalitechprojectdashboard.services.issues.IssueService;
import com.amalitech.amalitechprojectdashboard.services.project.ProjectReleaseService;
import com.amalitech.amalitechprojectdashboard.services.project.ProjectService;
import com.amalitech.amalitechprojectdashboard.services.utils.StatusService;
import com.amalitech.amalitechprojectdashboard.web.dto.issues.IssueDto;
import com.amalitech.amalitechprojectdashboard.web.dto.utils.StatusDto;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/issues")
public class IssueController {
	private final IssueService issueService;
	private final ProjectReleaseService projectReleaseService;
	private final StatusService statusService;
	private final ProjectService projectService;
	private final AccountService accountService;
	
	public IssueController(IssueService issueService, ProjectReleaseService projectReleaseService, StatusService statusService, ProjectService projectService, AccountService accountService) {
		this.issueService = issueService;
		this.projectReleaseService = projectReleaseService;
		this.statusService = statusService;
		this.projectService = projectService;
		this.accountService = accountService;
	}
	
	@GetMapping()
	public List<Issue> issues(){
		return issueService.issues();
	}
	
	
	@GetMapping("/{id}")
	public Issue singleIssue(@PathVariable Long id){
		return issueService.findIssueById(id);
	}
	
	
	@PostMapping()
	public Issue createIssue(@RequestBody IssueDto issueDto){
		return 	issueService.save(issueDto);
	}
	
	
	@PutMapping("{id}")
	public ResponseEntity<?> editIssue(@PathVariable(value ="id") Long id, @Validated @RequestBody IssueDto issueDto) {
		ProjectRelease projectRelease = projectReleaseService.findProjectReleaseById(issueDto.getProjectReleaseId());
		Issue issue = issueService.findIssueById(id);
		issue.setIssueName(issueDto.getIssueName());
		issue.setIssueDescription(issueDto.getIssueDescription());
		issue.setProjectRelease(projectRelease);
		issueService.editIssue(issue);
		return	GenericResponse.responseEntity("Update Successful");
	}
	
	@DeleteMapping("/{id}")
	public  ResponseEntity<?> deleteIssue(@PathVariable long id){
		issueService.deleteIssue(id);
		return	GenericResponse.responseEntity("Issue Deleted Successfully");
	}
	
	@PostMapping("count")
	public Map<String, Long> countIssueStatuses(@RequestBody StatusDto statusDto){
		List<Status> statuses = statusService.allStatus();
		Map<String, Long> data = new HashMap<>();
		final long[] completedRelease = {0,0};
		final long[] total = {0};
		if(statusDto.getProjectId() != null){
			Project project = projectService.findProjectById(statusDto.getProjectId());
			data.put("Releases", (long) project.getProjectReleases().size());
			if (project.getProjectReleases().size() > 0) {
				project.getProjectReleases().forEach(release -> {
					if (release.getCompleteDate() != null) {
						completedRelease[0]++;
					} else {
						completedRelease[1]++;
					}
					statuses.forEach(status -> {
						long count = issueService.countByStatusIdAndProjectReleaseId(status.getId(), release.getId());
						data.put(status.getStatusName(), !data.containsKey(status.getStatusName()) ? count : data.get(status.getStatusName()) + count);
						total[0] += count;
					});
				});
			}else {
				statuses.forEach(status ->{
					long num = 0;
					data.put(status.getStatusName(), num);
				});
			}
			
		}
		else if(statusDto.getAccountId() != null){
			Account  account = accountService.findAccountById(statusDto.getAccountId());
			
			if (account.getProjects().size() > 0){
				account.getProjects().forEach(project -> {
					project.getProjectReleases().forEach(release -> {
						if (release.getCompleteDate() != null) {
							completedRelease[0]++;
						} else {
							completedRelease[1]++;
						}
						statuses.forEach(status ->{
							long count = issueService.countByStatusIdAndProjectReleaseId(status.getId(), release.getId());
							data.put(status.getStatusName(), !data.containsKey(status.getStatusName()) ?  count : data.get(status.getStatusName()) + count);
							total[0] += count;
						});
					});
					long countRelease = !data.containsKey("Releases") ?  project.getProjectReleases().size() : data.get("Releases") + project.getProjectReleases().size();
					data.put("Releases", countRelease);
					data.put("Projects", projectService.countProjectByAccountId(account.getId()));
					data.put("Ongoing Projects", projectService.countProjectByOngoingAndAccount(true, account));
					data.put("Completed Projects", projectService.countProjectByOngoingAndAccount(false, account));
				});
				
			}else {
				statuses.forEach(status ->{
					long num = 0;
					data.put(status.getStatusName(), num);
				});
			}
		}
		else{
			statuses.forEach(status ->{
				data.put(status.getStatusName(), issueService.countIssueStatuses(status.getId()));
				total[0] += issueService.countIssueStatuses(status.getId());
			});
			data.put("Releases", (long) projectReleaseService.releases().size());
			completedRelease[0] = projectReleaseService.countCompleted();
			completedRelease[1] = projectReleaseService.countUncompleted();
			data.put("Accounts", (long) accountService.accounts().size());
			data.put("Active Accounts", accountService.countAccountByStatus(true));
			data.put("In-Active Accounts", accountService.countAccountByStatus(false));
			data.put("Projects",(long) projectService.projects().size());
			data.put("Ongoing Projects", projectService.countProjectByOngoing(true));
			data.put("Completed Projects", projectService.countProjectByOngoing(false));
		}
		data.put("Total", total[0]);
		data.put("Completed Release", completedRelease[0]);
		data.put("Uncompleted Release", completedRelease[1]);
		return data;
	}
}