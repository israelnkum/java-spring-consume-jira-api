package com.amalitech.amalitechprojectdashboard.controllers.utils;

import com.amalitech.amalitechprojectdashboard.models.projects.Project;
import com.amalitech.amalitechprojectdashboard.models.utils.Status;
import com.amalitech.amalitechprojectdashboard.responses.GenericResponse;
import com.amalitech.amalitechprojectdashboard.services.project.ProjectService;
import com.amalitech.amalitechprojectdashboard.services.utils.StatusService;
import com.amalitech.amalitechprojectdashboard.web.dto.utils.StatusDto;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/statuses")
public class StatusController {
	private final StatusService statusService;
	private final ProjectService projectService;
	public StatusController(StatusService statusService, ProjectService projectService) {
		this.statusService = statusService;
		this.projectService = projectService;
	}
	
	@GetMapping("")
	public List<Status> allStatuses(){
		return statusService.allStatus();
	}
	
	@PostMapping()
	public Status addStatus(@RequestBody StatusDto statusDto){
		return 	statusService.save(statusDto);
	}
	
	@PutMapping("{id}")
	public ResponseEntity<?> updateStatus(@PathVariable(value ="id") Long id, @Validated @RequestBody StatusDto statusDto) {
		
		Project project = projectService.findProjectById(statusDto.getProjectId());
		
		Status status = statusService.findStatusById(id);
		status.setStatusName(statusDto.getStatusName());
		status.setStatusDescription(statusDto.getStatusDescription());
		statusService.editStatus(status);
		return	GenericResponse.responseEntity("Update Successful");
	}
	
	@DeleteMapping("/{id}")
	public  ResponseEntity<?> deleteStatus(@PathVariable long id){
		statusService.deleteStatus(id);
		return	GenericResponse.responseEntity("Status Deleted Successfully");
	}
}
