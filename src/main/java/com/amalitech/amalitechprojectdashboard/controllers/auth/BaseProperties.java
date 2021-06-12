package com.amalitech.amalitechprojectdashboard.controllers.auth;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:jira-config.properties")
public class BaseProperties {
	
	@Value("${password}")
	private String password;
	
	@Value("${email}")
	private String email;
	
	@Value(("${urlV1}"))
	private String urlV1;
	
	public String getPassword() {
		return this.password;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public String getUrlV1(){
		return this.urlV1;
	}
}
