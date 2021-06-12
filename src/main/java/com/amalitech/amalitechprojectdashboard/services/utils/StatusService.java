package com.amalitech.amalitechprojectdashboard.services.utils;

import com.amalitech.amalitechprojectdashboard.interfaces.StatusServiceInterface;
import com.amalitech.amalitechprojectdashboard.models.utils.Status;
import com.amalitech.amalitechprojectdashboard.repositories.utils.StatusRepository;
import com.amalitech.amalitechprojectdashboard.responses.ApdAuthException;
import com.amalitech.amalitechprojectdashboard.web.dto.utils.StatusDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class StatusService implements StatusServiceInterface {
	
	private final StatusRepository statusRepository;
	
	public StatusService(StatusRepository statusRepository) {
		this.statusRepository = statusRepository;
	}
	
	@Override
	public Status save(StatusDto statusDto) throws ApdAuthException {
		Status status = new Status(statusDto.getStatusName(), statusDto.getStatusDescription());
		statusRepository.save(status);
		return status;
	}
	
	@Override
	public List<Status> allStatus() {
		return (List<Status>) statusRepository.findAll();
	}
	
	
	@Override
	public void editStatus(Status status) {
		statusRepository.save(status);
	}
	
	@Override
	public Status findStatusById(Long id) throws ApdAuthException {
		Optional<Status> optionalStatus = statusRepository.findById(id);
		Status status;
		if (optionalStatus.isPresent()){
			status = optionalStatus.get();
		}else {
			throw new ApdAuthException("Status not found");
		}
		return  status;
	}
	
	@Override
	public void deleteStatus(Long id) {
		statusRepository.deleteById(id);
	}
	
	@Override
	public Set<Status> statusByProject(Long id) {
//		return statusRepository.findByProjectId(id);
		return null;
	}
	
	@Override
	public Status findByStatusName(String name) {
		return statusRepository.findByStatusName(name);
	}
}
