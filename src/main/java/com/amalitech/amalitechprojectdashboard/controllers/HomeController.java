package com.amalitech.amalitechprojectdashboard.controllers;

import com.amalitech.amalitechprojectdashboard.controllers.auth.BaseProperties;
import com.amalitech.amalitechprojectdashboard.models.issues.Issue;
import com.amalitech.amalitechprojectdashboard.models.projects.Project;
import com.amalitech.amalitechprojectdashboard.models.projects.ProjectRelease;
import com.amalitech.amalitechprojectdashboard.models.utils.Status;
import com.amalitech.amalitechprojectdashboard.repositories.accounts.AccountRepository;
import com.amalitech.amalitechprojectdashboard.repositories.issues.IssueRepository;
import com.amalitech.amalitechprojectdashboard.repositories.project.ProjectReleaseRepository;
import com.amalitech.amalitechprojectdashboard.repositories.project.ProjectRepository;
import com.amalitech.amalitechprojectdashboard.services.utils.StatusService;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("jira")
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class HomeController {
	
	private final BaseProperties baseProperties;
	
	Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	private final ProjectRepository projectRepository;
	private final AccountRepository accountRepository;
	private final IssueRepository issueRepository;
	private final ProjectReleaseRepository projectReleaseRepository;
	private final StatusService statusService;
	
	public HomeController(BaseProperties baseProperties, ProjectRepository projectRepository, AccountRepository accountRepository, IssueRepository issueRepository, ProjectReleaseRepository projectReleaseRepository, StatusService statusService) {
		this.baseProperties = baseProperties;
		this.projectRepository = projectRepository;
		this.accountRepository = accountRepository;
		this.issueRepository = issueRepository;
		this.projectReleaseRepository = projectReleaseRepository;
		this.statusService = statusService;
	}
	
	@Transactional
	@GetMapping("/project")
	public String pullProjectsFromJira(){
		this.saveProjects();
		return "Success";
	}
	
	@GetMapping("/test")
	public Boolean testId() throws UnirestException {
	/*	HttpResponse<JsonNode> issueResponse = Unirest.get("https://amali-tech.atlassian.net/rest/agile/1.0/board/{boardId}/issue")
				.header("Accept", "application/json")
				.basicAuth("amos.nkum@amalitech.com","SdyuSY3bI0TLkmG2vZ0593D9")
				.routeParam("boardId", String.valueOf(2))
				.asJson();
		JSONArray issues = (JSONArray) issueResponse.getBody().getObject().get("issues");
		
		return issues.toString();*/
		return projectRepository.existsByJiraProjectId("10002");
	}
	
	private void saveProjects(){
		try {
			HttpResponse<JsonNode> jiraBoards = Unirest.get(baseProperties.getUrlV1()+"/board")
					.basicAuth(baseProperties.getEmail(),baseProperties.getPassword())
					.header("Accept", "application/json")
					.asJson();
			
			JSONArray boardValues = (JSONArray) jiraBoards.getBody().getObject().get("values");
			if (boardValues != null &&  boardValues.length() > 0){
				boardValues.forEach(item -> {
					int boardId = (int) ((JSONObject) item).get("id");
					Project project;
					JSONObject jiraBoardProject = (JSONObject) ((JSONObject) item).get("location");
					
					if (projectRepository.existsByJiraProjectId(jiraBoardProject.get("projectId").toString())){
						project = projectRepository.findByJiraProjectId(jiraBoardProject.get("projectId").toString());
						project.setProjectName(jiraBoardProject.get("projectName").toString());
						project.setJiraProjectId(jiraBoardProject.get("projectId").toString());
					}else{
						project = new Project(jiraBoardProject.get("projectName").toString(), jiraBoardProject.get("projectName").toString(), accountRepository.findFirstByOrderByIdAsc(), jiraBoardProject.get("projectId").toString());
					}
					
					projectRepository.save(project);
					// Todo: create project releases
					saveProjectReleases(boardId, project);
				});
			}
			
		} catch (UnirestException e) {
			e.getMessage();
		}
	}
	
	private void saveProjectReleases(int boardId, Project project){
		try {
			HttpResponse<JsonNode>jiraSprint = Unirest.get(baseProperties.getUrlV1()+"/board/{boardId}/sprint")
					.basicAuth(baseProperties.getEmail(),baseProperties.getPassword())
					.header("Accept", "application/json")
					.routeParam("boardId", String.valueOf(boardId))
					.asJson();
			
			JSONArray jiraSprintValues = (JSONArray) jiraSprint.getBody().getObject().get("values");
			
			if (jiraSprintValues != null && jiraSprintValues.length() > 0){
				jiraSprintValues.forEach(sprintValue -> {
					
					JSONObject sprintValueObject = ((JSONObject)sprintValue);
					
					String sprintId =  sprintValueObject.get("id").toString();
					DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
					
					LocalDateTime startDate = (sprintValueObject.has("startDate") ? LocalDateTime.parse(sprintValueObject.get("startDate").toString() , format) : null);
					LocalDateTime endDate = sprintValueObject.has("endDate") ? LocalDateTime.parse(sprintValueObject.get("endDate").toString(), format) : null;
					LocalDateTime completeDate = sprintValueObject.has("completeDate") ? LocalDateTime.parse(sprintValueObject.get("completeDate").toString(), format) : null;
					String goal = sprintValueObject.has("goal") ? sprintValueObject.get("goal").toString() : null;
					ProjectRelease projectRelease;
					if (projectReleaseRepository.existsByJiraReleaseId(sprintId)){
						projectRelease = projectReleaseRepository.findByJiraReleaseId(sprintId);
						projectRelease.setReleaseName(sprintValueObject.get("name").toString());
						projectRelease.setStartDate(startDate);
						projectRelease.setEndDate(endDate);
						projectRelease.setCompleteDate(completeDate);
						projectRelease.setDescription(goal);
					}else{
						projectRelease = new ProjectRelease(sprintValueObject.get("name").toString(), goal, startDate, endDate, completeDate, sprintId, project);
					}
					projectReleaseRepository.save(projectRelease);
					
					// Todo: save Issues
					this.saveIssues(boardId,sprintId, projectRelease);
					
					this.saveBacklogs(boardId,projectRelease);
				});
			}
		} catch (UnirestException e) {
			e.getMessage();
		}
	}
	
	private void saveIssues(int boardId, String sprintId, ProjectRelease projectRelease){
		try {
			HttpResponse<JsonNode> issueResponse = Unirest.get(baseProperties.getUrlV1()+"/board/{boardId}/sprint/{sprintId}/issue")
					.basicAuth(baseProperties.getEmail(),baseProperties.getPassword())
					.header("Accept", "application/json")
					.routeParam("boardId", String.valueOf(boardId))
					.routeParam("sprintId", String.valueOf(sprintId))
					.asJson();
			
			JSONArray jiraIssues = (JSONArray) issueResponse.getBody().getObject().get("issues");
			if(jiraIssues != null && jiraIssues.length() > 0){
				jiraIssues.forEach(issueItem -> {
					JSONObject issueItemObject = ((JSONObject)issueItem);
					String issueId =  issueItemObject.get("id").toString();
					JSONObject fieldObject = (JSONObject) ((JSONObject) issueItem).get("fields");
					
					JSONObject statusObject = (JSONObject) fieldObject.get("status");
					
					Status status = statusService.findByStatusName(statusObject.get("name").toString());
					double points =0;
					if (!fieldObject.isNull("customfield_10016")){
						points = (double) fieldObject.get("customfield_10016");
					}
					Issue issue;
					if (issueRepository.existsByJiraIssueId(issueId)){
						issue = issueRepository.findByJiraIssueId(issueId);
						issue.setIssueName(fieldObject.get("summary").toString());
						issue.setIssueDescription(fieldObject.get("description").toString());
						issue.setJiraIssueId(issueId);
						issue.setStoryPoint(points);
						issue.setProjectRelease(projectRelease);
						issue.setProject(projectRelease.getProject());
						issue.setStatus(status);
					}else{
						
						issue = new Issue(fieldObject.get("summary").toString(), fieldObject.get("description").toString(),issueId,projectRelease,status, points, null);
					}
					issueRepository.save(issue);
				});
			}
		} catch (UnirestException e) {
			e.getMessage();
		}
	}
	
	
	public void saveBacklogs(int boardId, ProjectRelease projectRelease){
		try{
			HttpResponse<JsonNode> backlogResponse = Unirest.get(baseProperties.getUrlV1()+"/board/{boardId}/backlog")
					.basicAuth(baseProperties.getEmail(),baseProperties.getPassword())
					.header("Accept", "application/json")
					.routeParam("boardId", String.valueOf(boardId))
					.asJson();
			JSONArray jiraBacklogs = (JSONArray) backlogResponse.getBody().getObject().get("issues");
			
			Status status = statusService.findByStatusName("Backlog");
			
			if(jiraBacklogs != null && jiraBacklogs.length() > 0){
				jiraBacklogs.forEach(issueItem -> {
					JSONObject issueItemObject = ((JSONObject)issueItem);
					String issueId =  issueItemObject.get("id").toString();
					JSONObject fieldObject = (JSONObject) ((JSONObject) issueItem).get("fields");
					Issue issue;
					if (issueRepository.existsByJiraIssueId(issueId)){
						issue = issueRepository.findByJiraIssueId(issueId);
						issue.setIssueName(fieldObject.get("summary").toString());
						issue.setIssueDescription(fieldObject.get("description").toString());
						issue.setJiraIssueId(issueId);
						issue.setStatus(status);
						issue.setProjectRelease(projectRelease);
					}else{
						issue = new Issue(fieldObject.get("summary").toString(), fieldObject.get("description").toString(),issueId,projectRelease, status, 0, null);
					}
					issueRepository.save(issue);
				});
			}
		} catch (UnirestException e) {
			e.getMessage();
		}
	}
	
}
