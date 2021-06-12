package com.amalitech.amalitechprojectdashboard.models;

import com.amalitech.amalitechprojectdashboard.models.accounts.AccountManager;
import com.amalitech.amalitechprojectdashboard.models.projects.ProjectManager;
import com.amalitech.amalitechprojectdashboard.models.utils.AuditModel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "apd_roles")
@NoArgsConstructor
@Where(clause = "deleted = false")
public class Role extends AuditModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	String name;
	
	@Column(name = "deleted", columnDefinition ="boolean default false")
	private boolean deleted;
	
	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "role", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	private Set<AccountManager> accountManagers;
	
	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "role", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	private Set<ProjectManager> projectManagers;
	
	public Role(String name) {
		this.name = name;
		this.deleted=false;
	}
}
