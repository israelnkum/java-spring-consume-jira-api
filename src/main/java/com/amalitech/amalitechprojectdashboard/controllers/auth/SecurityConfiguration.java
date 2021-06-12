package com.amalitech.amalitechprojectdashboard.controllers.auth;

import com.amalitech.amalitechprojectdashboard.controllers.auth.filter.JwtRequestFilter;
import com.amalitech.amalitechprojectdashboard.controllers.auth.services.UserDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	private final UserDetailService userDetailService;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final JwtRequestFilter jwtRequestFilter;
	
	public SecurityConfiguration(UserDetailService userDetailService, BCryptPasswordEncoder bCryptPasswordEncoder, JwtRequestFilter jwtRequestFilter){
		this.userDetailService = userDetailService;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.jwtRequestFilter = jwtRequestFilter;
	}
	
	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception{
		httpSecurity.cors();
		httpSecurity.csrf().disable()
				.authorizeRequests()
				.antMatchers("/auth/wall","/registration").permitAll()
				.antMatchers("/password/**").permitAll()
//				.antMatchers("/roles/**").permitAll()
				.antMatchers("/jira/**").permitAll()
				.anyRequest().authenticated()
				.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
//		auth.userDetailsService(userDetailService);
		auth.authenticationProvider(daoAuthenticationProvider());
	}
	
	@Override
	@Bean
	public AuthenticationManager authenticationManager() throws Exception{
		return super.authenticationManager();
	}
	
	public DaoAuthenticationProvider daoAuthenticationProvider(){
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userDetailService);
		provider.setPasswordEncoder(bCryptPasswordEncoder);
		
		return provider;
	}
}
