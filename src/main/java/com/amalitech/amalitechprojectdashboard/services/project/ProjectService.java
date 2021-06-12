package com.amalitech.amalitechprojectdashboard.services.project;

import com.amalitech.amalitechprojectdashboard.interfaces.projects.ProjectServiceInterface;
import com.amalitech.amalitechprojectdashboard.models.Role;
import com.amalitech.amalitechprojectdashboard.models.User;
import com.amalitech.amalitechprojectdashboard.models.accounts.Account;
import com.amalitech.amalitechprojectdashboard.models.projects.Project;
import com.amalitech.amalitechprojectdashboard.models.projects.ProjectRelease;
import com.amalitech.amalitechprojectdashboard.repositories.project.ProjectRepository;
import com.amalitech.amalitechprojectdashboard.responses.ApdAuthException;
import com.amalitech.amalitechprojectdashboard.services.accounts.AccountService;
import com.amalitech.amalitechprojectdashboard.services.roles.RoleService;
import com.amalitech.amalitechprojectdashboard.services.user.UserService;
import com.amalitech.amalitechprojectdashboard.web.dto.ProjectDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ProjectService implements ProjectServiceInterface {
	private final ProjectRepository projectRepository;
	private final AccountService accountService;
	private final UserService userService;
	private final RoleService roleService;
	private final ProjectManagerService projectManagerService;
	
	public ProjectService(ProjectRepository projectRepository, AccountService accountService, UserService userService, RoleService roleService, ProjectManagerService projectManagerService) {
		this.projectRepository = projectRepository;
		this.accountService = accountService;
		this.userService = userService;
		this.roleService = roleService;
		this.projectManagerService = projectManagerService;
	}
	
	@Override
	public Project save(ProjectDto projectDto) throws ApdAuthException {
		Account account = accountService.findAccountById(projectDto.getAccount());
		Project project = new Project(projectDto.getProjectName(), projectDto.getDescription(),account);
		projectRepository.save(project);
		return project;
	}
	
	@Override
	public List<Project> projects() {
		return (List<Project>) projectRepository.findAll();
	}
	
	@Override
	public void editProject(Project project) {
		projectRepository.save(project);
	}
	
	@Override
	public Project findProjectById(Long id) throws ApdAuthException {
		Optional<Project> optionalProject = projectRepository.findById(id);
		Project project;
		if (optionalProject.isPresent()){
			project = optionalProject.get();
		}else {
			throw new ApdAuthException("Project not found");
		}
		return  project;
	}
	
	@Override
	public void deleteProject(Long id) {
		Project project = findProjectById(id);
		project.setDeleted(true);
	}
	
	@Override
	public void assignProjectManager(ProjectDto projectDto) {
		Project project = findProjectById(projectDto.getProjectId());
		projectDto.getUserIds().forEach(usr -> {
			User user = userService.findUserById(usr);
			projectDto.getRoleIds().forEach(roleId -> {
				Role role = roleService.findRoleById(roleId);
				projectManagerService.save(project,role,user);
				user.addRole(role);
				userService.editUserInfo(user);
			});
		});
	}
	
	@Override
	public Set<ProjectRelease> getProjectReleases(ProjectDto projectDto) {
		return null;
	}
	
	@Override
	public Set<Project> projectByAccount(Long id) {
		return projectRepository.findByAccountId(id);
	}
	
	@Override
	public Long countProjectByOngoingAndAccount(boolean status, Account account) {
		return projectRepository.countProjectByOngoingAndAccount(status, account);
	}
	
	@Override
	public Long countProjectByOngoing(boolean status) {
		return projectRepository.countProjectByOngoing(status);
	}
	
	@Override
	public Long countProjectByAccountId(Long accountId) {
		return projectRepository.countProjectByAccountId(accountId);
	}
}
