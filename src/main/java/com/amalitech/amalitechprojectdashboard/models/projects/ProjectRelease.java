package com.amalitech.amalitechprojectdashboard.models.projects;

import com.amalitech.amalitechprojectdashboard.models.issues.Issue;
import com.amalitech.amalitechprojectdashboard.models.utils.Status;
import com.fasterxml.jackson.annotation.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "apd_project_releases")
public class ProjectRelease {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String releaseName;
	
	@Column(length = 500)
	private String description = null;
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime startDate;
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime endDate;
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime completeDate;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "project_id", nullable = false)
	@JsonProperty("projectId")
	@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
	@JsonIdentityReference(alwaysAsId = true)
	private Project project;
	
	@JsonIgnore
	@OneToMany(mappedBy = "projectRelease")
	private Set<Issue> issues;
	
	private String jiraReleaseId;
	
	
	@JsonIgnore
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "apd_project_release_statuses", joinColumns = @JoinColumn(name = "project_release_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "status_id", referencedColumnName = "id"))
	private Set<Status> statuses = new HashSet<>();
	
	
	public ProjectRelease(String releaseName, String description, LocalDateTime startDate, LocalDateTime endDate,LocalDateTime completeDate, String jiraReleaseId, Project project) {
		this.releaseName = releaseName;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
		this.completeDate = completeDate;
		this.jiraReleaseId = jiraReleaseId;
		this.project = project;
	}
	
}
