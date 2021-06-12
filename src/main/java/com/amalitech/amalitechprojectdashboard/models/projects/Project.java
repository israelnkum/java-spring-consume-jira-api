package com.amalitech.amalitechprojectdashboard.models.projects;

import com.amalitech.amalitechprojectdashboard.models.accounts.Account;
import com.amalitech.amalitechprojectdashboard.models.utils.AuditModel;
import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "apd_projects")
@AllArgsConstructor
@NoArgsConstructor
@Where(clause = "deleted = false")
public class Project extends AuditModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String projectName;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.ALL)
	@JoinColumn(name = "account_id", nullable = false)
	@JsonProperty("accountId")
	@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
	@JsonIdentityReference(alwaysAsId = true)
	private Account account;
	
	@Column(length = 500)
	private String description;
	
	@Column(name = "deleted", columnDefinition ="boolean default false")
	private boolean deleted;
	
	private String jiraProjectId;
	
	@JsonIgnore
	@OneToMany(mappedBy = "project", fetch = FetchType.LAZY)
	private Set<ProjectRelease> projectReleases;
	
	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "project", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	private Set<ProjectManager> projectManagers;
	
	@Column(name = "ongoing", columnDefinition = "boolean default true")
	private boolean ongoing  = true;
	
	public Project(String projectName, String description,Account account ) {
		this.projectName = projectName;
		this.account = account;
		this.description = description;
		this.deleted = false;
	}
	
	public Project(String projectName,  String description, Account account, String jiraProjectId) {
		this.projectName = projectName;
		this.description = description;
		this.account = account;
		this.deleted = false;
		this.jiraProjectId = jiraProjectId;
	}
}
