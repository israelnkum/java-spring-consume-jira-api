package com.amalitech.amalitechprojectdashboard.responses;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class ApdAuthException extends RuntimeException{
	public ApdAuthException(String message){
		super(message);
	}
}
