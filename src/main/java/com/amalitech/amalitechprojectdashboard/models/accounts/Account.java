package com.amalitech.amalitechprojectdashboard.models.accounts;

import com.amalitech.amalitechprojectdashboard.models.projects.Project;
import com.amalitech.amalitechprojectdashboard.models.utils.AuditModel;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "apd_accounts")
@AllArgsConstructor
@Where(clause = "deleted = false")
@NoArgsConstructor
public class Account extends AuditModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String accountName;
	private String description;
	private boolean deleted;
	
	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "account",cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	private Set<AccountManager> accountManagers;
	
	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "account",cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	private Set<Project> projects;
	
	@Column(name = "active", columnDefinition = "boolean default true")
	private boolean active  = true;
	
	public Account(String accountName, String description){
		this.accountName =accountName;
		this.description = description;
		this.deleted = false;
	}
	
}
