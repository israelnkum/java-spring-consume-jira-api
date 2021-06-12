package com.amalitech.amalitechprojectdashboard.models.issues;

import com.amalitech.amalitechprojectdashboard.models.projects.Project;
import com.amalitech.amalitechprojectdashboard.models.projects.ProjectRelease;
import com.amalitech.amalitechprojectdashboard.models.utils.AuditModel;
import com.amalitech.amalitechprojectdashboard.models.utils.Status;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "apd_issues")
@AllArgsConstructor
@NoArgsConstructor
public class Issue extends AuditModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(length = 500)
	private String issueName;
	
	@Column(columnDefinition="TEXT", length = 99999)
	private String issueDescription;
	
	
	@ManyToOne(optional = false, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonProperty("releaseId")
	@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
	@JsonIdentityReference(alwaysAsId = true)
	private ProjectRelease projectRelease;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonProperty("projectId")
	@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
	@JsonIdentityReference(alwaysAsId = true)
	private Project project;
	
	@OneToMany(mappedBy = "issue", fetch = FetchType.LAZY)
	private Set<AcceptanceCriteria> acceptanceCriteria;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "status_id")
	@JsonProperty("statusId")
	@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
	@JsonIdentityReference(alwaysAsId = true)
	private Status status;
	
	private String jiraIssueId;
	
//	@Column(columnDefinition = "default 0")
	private Double storyPoint;
	
	private String estimatedTime;
	
	public Issue(String issueName, String issueDescription, String jiraIssueId, ProjectRelease projectRelease, Status status, double storyPoint, String estimatedTime) {
		this.issueName = issueName;
		this.issueDescription = issueDescription;
		this.jiraIssueId = jiraIssueId;
		this.projectRelease = projectRelease;
		this.status = status;
		this.storyPoint =  storyPoint;
		this.estimatedTime = estimatedTime;
	}
}
