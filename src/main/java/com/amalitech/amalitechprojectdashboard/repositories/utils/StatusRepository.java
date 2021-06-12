package com.amalitech.amalitechprojectdashboard.repositories.utils;

import com.amalitech.amalitechprojectdashboard.models.utils.Status;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusRepository extends CrudRepository<Status, Long> {
	//Set<Status> findByProjectId(Long id);
	
	Status findByStatusName(String name);
}
