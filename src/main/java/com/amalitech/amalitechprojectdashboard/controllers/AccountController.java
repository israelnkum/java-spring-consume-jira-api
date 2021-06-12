package com.amalitech.amalitechprojectdashboard.controllers;

import com.amalitech.amalitechprojectdashboard.models.accounts.Account;
import com.amalitech.amalitechprojectdashboard.models.accounts.AccountManager;
import com.amalitech.amalitechprojectdashboard.models.projects.Project;
import com.amalitech.amalitechprojectdashboard.models.projects.ProjectRelease;
import com.amalitech.amalitechprojectdashboard.responses.GenericResponse;
import com.amalitech.amalitechprojectdashboard.services.accounts.AccountManagerService;
import com.amalitech.amalitechprojectdashboard.services.accounts.AccountService;
import com.amalitech.amalitechprojectdashboard.services.project.ProjectService;
import com.amalitech.amalitechprojectdashboard.web.dto.AccountDto;
import com.amalitech.amalitechprojectdashboard.web.dto.utils.StatusDto;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/accounts")
public class AccountController {
	private final AccountService accountService;
	private final AccountManagerService accountManagerService;
	private final ProjectService projectService;
	
	public AccountController(AccountService accountService, AccountManagerService accountManagerService, ProjectService projectService) {
		this.accountService = accountService;
		this.accountManagerService = accountManagerService;
		this.projectService = projectService;
	}
	
	@GetMapping()
	public List<Account> getAllAccounts(){
		return accountService.accounts();
	}
	
	@GetMapping("{id}")
	public Account singleAccount(@PathVariable Long id){
		return accountService.findAccountById(id);
	}
	
	@PostMapping()
	public Account addAccount(@RequestBody AccountDto accountDto){
		return 	accountService.save(accountDto);
	}
	
	@PutMapping("{id}")
	public ResponseEntity<?> updateAccount(@PathVariable(value ="id") Long accountId, @Validated @RequestBody AccountDto accountDto) {
		Account account = accountService.findAccountById(accountId);
		account.setAccountName(accountDto.getAccountName());
		account.setDescription(accountDto.getDescription());
		accountService.editAccount(account);
		return	GenericResponse.responseEntity("Update Successful");
	}
	
	@DeleteMapping("/{id}")
	public  ResponseEntity<?> deleteEmployee(@PathVariable long id){
		accountService.deleteAccount(id);
		return	GenericResponse.responseEntity("Account Deleted Successfully");
	}
	
	@PostMapping("/assign")
	public ResponseEntity<?> assignAccountManager(@RequestBody AccountDto accountDto){
		accountService.assignAccountManager(accountDto);
		return	GenericResponse.responseEntity("Successful");
	}
	
	@DeleteMapping("/un-assign")
	public ResponseEntity<?> unAssignAccountManager(@RequestBody AccountDto accountDto){
		accountManagerService.removeAccountManager(accountDto.getId());
		return	GenericResponse.responseEntity("User Removed Successfully");
	}
	
	@PostMapping("/account-managers")
	public Set<AccountManager> getAccountManagers(@RequestBody AccountDto accountDto){
		return accountManagerService.findByAccountId(accountDto.getAccountId());
	}
	
	@PostMapping("/stats")
	public List<?> projectReleaseOverview(@RequestBody StatusDto statusDto){
		ArrayList<Estimate> estimates = new ArrayList<>();
		if (statusDto.getProjectId() != null){
			getProjectEstimate(statusDto.getProjectId(), estimates);
		}else if(statusDto.getAccountId() != null){
			return getEstimateForAccountAdmin(statusDto.getAccountId());
		}else {
			getEstimateForMainAdmin(estimates);
		}
		
		return  estimates;
	}
	
	
	private void getEstimateForMainAdmin(ArrayList<Estimate> estimates){
		List<Account> accounts = accountService.accounts();
		accounts.forEach(account -> {
			Estimate estimate = new Estimate();
			estimate.setName(account.getAccountName());
			account.getProjects().forEach(project -> {
				project.getProjectReleases().forEach(projectRelease -> {
					getReleaseEstimate(projectRelease, estimate);
				});
			});
			estimates.add(estimate);
		});
	}
	
	private List<?> getEstimateForAccountAdmin(Long accountId){
		ArrayList<Estimate> estimates = new ArrayList<>();
		Account account = accountService.findAccountById(accountId);
		account.getProjects().forEach(project -> {
			project.getProjectReleases().forEach(projectRelease -> {
				Estimate estimate = new Estimate();
				estimate.setName(projectRelease.getReleaseName());
				estimates.add(getReleaseEstimate(projectRelease, estimate));
			});
		});
		return estimates;
	}
	
	private Estimate getReleaseEstimate(ProjectRelease projectRelease, Estimate estimate){
		
		long estimatedHours = estimate.getEstimatedHours();
		if (projectRelease.getStartDate() != null && projectRelease.getEndDate() != null){
			long diff = ChronoUnit.HOURS.between(projectRelease.getStartDate(),projectRelease.getEndDate());
			estimatedHours += diff;
			estimate.setEstimatedHours(estimatedHours);
		}
		
		long actualHours = estimate.getActualHours();
		if (projectRelease.getStartDate() != null && projectRelease.getCompleteDate() != null){
			long diff = ChronoUnit.HOURS.between(projectRelease.getStartDate(),projectRelease.getCompleteDate());
			actualHours += diff;
			estimate.setActualHours(actualHours);
		}
		return estimate;
	}
	
	private void getProjectEstimate(Long projectId, ArrayList<Estimate> estimates){
		Project project = projectService.findProjectById(projectId);
		project.getProjectReleases().forEach(projectRelease -> {
			Estimate estimate = new Estimate();
			estimate.setName(projectRelease.getReleaseName());
			estimates.add(getReleaseEstimate(projectRelease, estimate));
		});
	}
	
	@Data
	public static class Estimate {
		private String name;
		private Long estimatedHours = 0L;
		private Long actualHours = 0L;
	}
}
