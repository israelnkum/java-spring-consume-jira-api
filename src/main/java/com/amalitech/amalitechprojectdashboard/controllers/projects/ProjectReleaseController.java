package com.amalitech.amalitechprojectdashboard.controllers.projects;

import com.amalitech.amalitechprojectdashboard.models.issues.Issue;
import com.amalitech.amalitechprojectdashboard.models.projects.Project;
import com.amalitech.amalitechprojectdashboard.models.projects.ProjectRelease;
import com.amalitech.amalitechprojectdashboard.responses.GenericResponse;
import com.amalitech.amalitechprojectdashboard.services.issues.IssueService;
import com.amalitech.amalitechprojectdashboard.services.project.ProjectReleaseService;
import com.amalitech.amalitechprojectdashboard.services.project.ProjectService;
import com.amalitech.amalitechprojectdashboard.web.dto.ProjectReleaseDto;
import com.amalitech.amalitechprojectdashboard.web.dto.issues.IssueDto;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/project-release")
public class ProjectReleaseController {
	
	private final ProjectReleaseService projectReleaseService;
	private final ProjectService projectService;
	private final IssueService issueService;
	public ProjectReleaseController(ProjectReleaseService projectReleaseService, ProjectService projectService, IssueService issueService) {
		
		this.projectReleaseService = projectReleaseService;
		this.projectService = projectService;
		this.issueService = issueService;
	}
	
	@GetMapping()
	public List<ProjectRelease> projectReleases(){
		return projectReleaseService.releases();
	}
	
	
	@PostMapping()
	public ProjectRelease addRelease(@RequestBody ProjectReleaseDto projectReleaseDto){
		return projectReleaseService.save(projectReleaseDto);
	}
	
	@PutMapping("{id}")
	public ResponseEntity<?> updateRelease(@PathVariable(value ="id") Long releaseId, @Validated @RequestBody ProjectReleaseDto projectReleaseDto) {
		Project project = projectService.findProjectById(projectReleaseDto.getProjectId());
		
		ProjectRelease projectRelease = projectReleaseService.findProjectReleaseById(releaseId);
		
		projectRelease.setReleaseName(projectReleaseDto.getReleaseName());
		projectRelease.setDescription(projectReleaseDto.getDescription());
		projectRelease.setProject(project);
		projectReleaseService.updateRelease(projectRelease);
		
		return	GenericResponse.responseEntity("Update Successful");
	}
	
	@PostMapping("issues")
	public Set<Issue> projectReleaseIssues(@RequestBody IssueDto issueDto){
		return issueService.findByProjectReleaseId(issueDto.getProjectReleaseId());
	}
	
	@PostMapping("count")
	public Long countProjectReleaseIssues(@RequestBody IssueDto issueDto){
		return issueService.countIssueByProjectReleaseId(issueDto.getProjectReleaseId());
	}
}
