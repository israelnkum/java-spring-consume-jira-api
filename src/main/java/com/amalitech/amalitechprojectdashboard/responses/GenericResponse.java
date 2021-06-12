package com.amalitech.amalitechprojectdashboard.responses;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.HashMap;
import java.util.Map;

public class GenericResponse{
	public static ResponseEntity<Map<String, String>> responseEntity(String value){
		Map<String, String> map = new HashMap<>();
		map.put("message",value);
		return new ResponseEntity<>(map, HttpStatus.OK);
	}
	
}
