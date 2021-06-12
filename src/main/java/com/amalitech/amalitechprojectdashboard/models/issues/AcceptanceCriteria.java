package com.amalitech.amalitechprojectdashboard.models.issues;

import com.amalitech.amalitechprojectdashboard.models.utils.AuditModel;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "apd_acceptance_criteria")
@AllArgsConstructor
@NoArgsConstructor
public class AcceptanceCriteria extends AuditModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String description;
	
	@Column(name = "is_complete", columnDefinition ="boolean default false")
	private boolean isComplete;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "issue_id", nullable = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonProperty("issueId")
	@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
	@JsonIdentityReference(alwaysAsId = true)
	private Issue issue;
	
	public AcceptanceCriteria(String description, Issue issue) {
		this.description = description;
		this.issue = issue;
	}
	
}
