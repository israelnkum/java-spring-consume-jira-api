package com.amalitech.amalitechprojectdashboard.controllers.projects;

import com.amalitech.amalitechprojectdashboard.models.accounts.Account;
import com.amalitech.amalitechprojectdashboard.models.projects.Project;
import com.amalitech.amalitechprojectdashboard.models.projects.ProjectManager;
import com.amalitech.amalitechprojectdashboard.models.projects.ProjectRelease;
import com.amalitech.amalitechprojectdashboard.models.utils.Status;
import com.amalitech.amalitechprojectdashboard.responses.GenericResponse;
import com.amalitech.amalitechprojectdashboard.services.accounts.AccountService;
import com.amalitech.amalitechprojectdashboard.services.issues.IssueService;
import com.amalitech.amalitechprojectdashboard.services.project.ProjectManagerService;
import com.amalitech.amalitechprojectdashboard.services.project.ProjectReleaseService;
import com.amalitech.amalitechprojectdashboard.services.project.ProjectService;
import com.amalitech.amalitechprojectdashboard.services.utils.StatusService;
import com.amalitech.amalitechprojectdashboard.web.dto.ProjectDto;
import com.amalitech.amalitechprojectdashboard.web.dto.utils.StatusDto;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/projects")
public class ProjectController {
	private final ProjectService projectService;
	private final AccountService accountService;
	private final ProjectManagerService projectManagerService;
	private final ProjectReleaseService projectReleaseService;
	private final StatusService statusService;
	private final IssueService issueService;
	public ProjectController(ProjectService projectService, AccountService accountService, ProjectManagerService projectManagerService, ProjectReleaseService projectReleaseService, StatusService statusService, IssueService issueService) {
		this.projectService = projectService;
		this.accountService = accountService;
		this.projectManagerService = projectManagerService;
		this.projectReleaseService = projectReleaseService;
		this.statusService = statusService;
		this.issueService = issueService;
	}
	
	@GetMapping()
	public List<Project> projects(){
		return projectService.projects();
	}
	
	@PostMapping()
	public Project project(@RequestBody ProjectDto projectDto){
		return 	projectService.save(projectDto);
	}
	
	@GetMapping("/{id}")
	public Project singleProject(@PathVariable Long id){
		return projectService.findProjectById(id);
	}
	
	@PutMapping("{id}")
	public ResponseEntity<?> updateProject(@PathVariable Long id, @Validated @RequestBody ProjectDto projectDto) {
		Project project = projectService.findProjectById(id);
		Account account = accountService.findAccountById(projectDto.getAccount());
		project.setProjectName(projectDto.getProjectName());
		project.setDescription(projectDto.getDescription());
		project.setAccount(account);
		projectService.editProject(project);
		return	GenericResponse.responseEntity("Update Successful");
	}
	
	@DeleteMapping("/{id}")
	public  ResponseEntity<?> deleteProject(@PathVariable long id){
		projectService.deleteProject(id);
		return	GenericResponse.responseEntity("Project Deleted Successfully");
	}
	
	@PostMapping("/assign")
	public ResponseEntity<?> assignAccountManager(@RequestBody ProjectDto projectDto){
		projectService.assignProjectManager(projectDto);
		return	GenericResponse.responseEntity("Successful");
	}
	
	@PostMapping("/project-managers")
	public Set<ProjectManager> projectManagers(@RequestBody ProjectDto projectDto){
		return projectManagerService.findByProjectId(projectDto.getProjectId());
	}
	
	@PostMapping("releases")
	public Set<ProjectRelease> projectReleases(@RequestBody ProjectDto projectDto){
		return  projectReleaseService.findByProjectId(projectDto.getId());
	}
	
	@PostMapping("accounts")
	public Set<Project> projectsByAccount(@RequestBody ProjectDto projectDto){
		return projectService.projectByAccount(projectDto.getAccountId());
	}
	
	@PostMapping("statuses")
	public Set<Status> projectsStatuses(@RequestBody StatusDto statusDto){
		return statusService.statusByProject(statusDto.getProjectId());
	}
	
	@PostMapping("issues/count")
	public Map<String, Long> countProjectIssues(@RequestBody StatusDto statusDto){
		Project project = projectService.findProjectById(statusDto.getProjectId());
		List<Status> statuses = statusService.allStatus();
		final long[] total = {0};
		Map<String, Long> data = new HashMap<>();
		
		data.put("Releases", (long) project.getProjectReleases().size());
		project.getProjectReleases().forEach(release -> {
			statuses.forEach(status ->{
				long count = issueService.countByStatusIdAndProjectReleaseId(status.getId(), release.getId());
				/*if (data.containsKey(status.getStatusName())){
					data.put(status.getStatusName(),data.get(status.getStatusName()) + count);
				}else{
					data.put(status.getStatusName(), count);
				}*/
				 data.put(status.getStatusName(), !data.containsKey(status.getStatusName()) ?  count : data.get(status.getStatusName()) + count);
			});
		});
		
		return data;
	}
}
