package com.amalitech.amalitechprojectdashboard.models.projects;

import com.amalitech.amalitechprojectdashboard.models.Role;
import com.amalitech.amalitechprojectdashboard.models.User;
import com.fasterxml.jackson.annotation.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "apd_projects_managers")
public class ProjectManager {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonIgnore
	private Long id;
	
	
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "project_id")
	@JsonProperty("projectId")
	@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
	@JsonIdentityReference(alwaysAsId = true)
	private Project project;
	
	
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "role_id")
	@JsonProperty("roleId")
	@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
	@JsonIdentityReference(alwaysAsId = true)
	private Role role;
	
	
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JsonProperty("userId")
	@JoinColumn(name= "user_id")
	@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
	@JsonIdentityReference(alwaysAsId = true)
	private User user;
	
	public ProjectManager(Project project, Role role, User user) {
		this.project = project;
		this.role = role;
		this.user = user;
	}
}
