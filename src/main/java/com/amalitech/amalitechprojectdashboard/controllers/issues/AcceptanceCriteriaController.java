package com.amalitech.amalitechprojectdashboard.controllers.issues;

import com.amalitech.amalitechprojectdashboard.models.issues.AcceptanceCriteria;
import com.amalitech.amalitechprojectdashboard.models.issues.Issue;
import com.amalitech.amalitechprojectdashboard.responses.GenericResponse;
import com.amalitech.amalitechprojectdashboard.services.issues.AcceptanceCriteriaService;
import com.amalitech.amalitechprojectdashboard.services.issues.IssueService;
import com.amalitech.amalitechprojectdashboard.web.dto.issues.AcceptanceCriteriaDto;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("acceptance-criteria")
public class AcceptanceCriteriaController {
	private final AcceptanceCriteriaService acceptanceCriteriaService;
	private final IssueService issueService;
	public AcceptanceCriteriaController(AcceptanceCriteriaService acceptanceCriteriaService, IssueService issueService) {
		this.acceptanceCriteriaService = acceptanceCriteriaService;
		this.issueService = issueService;
	}
	
	@PostMapping()
	public AcceptanceCriteria create(@RequestBody AcceptanceCriteriaDto acceptanceCriteriaDto){
		return 	acceptanceCriteriaService.save(acceptanceCriteriaDto);
	}
	
	@GetMapping()
	public List<AcceptanceCriteria> read(){
		return acceptanceCriteriaService.acceptanceCriteria();
	}
	
	@GetMapping("/issue/{id}")
	public List<AcceptanceCriteria> issueCriteria(@PathVariable Long id){
		return acceptanceCriteriaService.issueCriteria(id);
	}
	
	@PutMapping("{id}")
	public ResponseEntity<?> update(@PathVariable(value ="id") Long id, @Validated @RequestBody AcceptanceCriteriaDto acceptanceCriteriaDto) {
		AcceptanceCriteria acceptanceCriteria = acceptanceCriteriaService.findAcceptanceCriteriaById(id);
		acceptanceCriteria.setDescription(acceptanceCriteriaDto.getDescription());
		acceptanceCriteriaService.editAcceptanceCriteria(acceptanceCriteria);
		return	GenericResponse.responseEntity("Update Successful");
	}
	
	@DeleteMapping("/{id}")
	public  ResponseEntity<?> delete(@PathVariable long id){
		acceptanceCriteriaService.deleteAcceptanceCriteria(id);
		return	GenericResponse.responseEntity("Criteria Deleted Successfully");
	}
	
	@PostMapping("status")
	public Set<AcceptanceCriteria> completedCriteria(@RequestBody AcceptanceCriteriaDto acceptanceCriteriaDto){
		Issue issue = issueService.findIssueById(acceptanceCriteriaDto.getIssueId());
		return acceptanceCriteriaService.completedCriteria(acceptanceCriteriaDto.isComplete(), issue);
	}
	
	@GetMapping("dod")
	public  Map<String, Object> countCompleted(){
		Long completed = acceptanceCriteriaService.countAcceptanceCriteria(true);
		Long unCompleted = acceptanceCriteriaService.countAcceptanceCriteria(false);
		
		Map<String, Object> data = new HashMap<>();
		
		data.put("completed", completed);
		data.put("unCompleted", unCompleted);
		return data;
	}
	
	@GetMapping("toggle")
	public boolean toggleStatus(@RequestBody AcceptanceCriteriaDto acceptanceCriteriaDto){
		AcceptanceCriteria acceptanceCriteria = acceptanceCriteriaService.findAcceptanceCriteriaById(acceptanceCriteriaDto.getId());
		acceptanceCriteria.setComplete(!acceptanceCriteria.isComplete());
		acceptanceCriteriaService.editAcceptanceCriteria(acceptanceCriteria);
		
		return acceptanceCriteria.isComplete();
	}
}
