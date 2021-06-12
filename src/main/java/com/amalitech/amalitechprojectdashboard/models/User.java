package com.amalitech.amalitechprojectdashboard.models;

import com.amalitech.amalitechprojectdashboard.models.accounts.AccountManager;
import com.amalitech.amalitechprojectdashboard.models.projects.ProjectManager;
import com.amalitech.amalitechprojectdashboard.models.utils.AuditModel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Where(clause = "deleted = false")
@Table(name = "apd_users", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class User extends AuditModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;
	
	@Column(name = "verified", columnDefinition ="boolean default false")
	private boolean verified;
	
	@Column(name = "deleted", columnDefinition ="boolean default false")
	private boolean deleted;
	
	private String firstName;
	private String lastName;
	private String email;
	
	@JsonIgnore
	@Column(name = "password")
	private String password;
	
	@JsonIgnore
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "apd_user_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
	private Set<Role> roles = new HashSet<>();
	
	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	private Set<AccountManager> accountManagers;
	
	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	private Set<ProjectManager> projectManagers;
	
	public User(String firstName, String lastName, String email, String password) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
	}
	
	public User() {
		this.verified=false;
		this.deleted=false;
	}
	
	public String getUsername() {
		return email;
	}
	
	public void addRole(Role role) {
		if(roles == null) {
			roles = new HashSet<>();
		}
		this.roles.add(role);
		roles.add(role);
	}
}
