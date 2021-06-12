package com.amalitech.amalitechprojectdashboard.interfaces;

import com.amalitech.amalitechprojectdashboard.models.utils.Status;
import com.amalitech.amalitechprojectdashboard.responses.ApdAuthException;
import com.amalitech.amalitechprojectdashboard.web.dto.utils.StatusDto;

import java.util.List;
import java.util.Set;

public interface StatusServiceInterface {
	Status save(StatusDto statusDto) throws ApdAuthException;
	
	List<Status> allStatus();
	
	void editStatus(Status status);
	
	Status findStatusById(Long id) throws ApdAuthException;
	
	void deleteStatus(Long id);
	
	Set<Status> statusByProject(Long id);
	
	Status findByStatusName(String name);
}
