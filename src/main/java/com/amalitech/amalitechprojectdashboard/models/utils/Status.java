package com.amalitech.amalitechprojectdashboard.models.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "apd_statuses")
@AllArgsConstructor
@NoArgsConstructor
public class Status extends AuditModel{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;
	
	private String statusName;
	
	@Column(length = 500)
	private String statusDescription;
	
	
	public Status(String statusName,String statusDescription) {
		this.statusName = statusName;
		this.statusDescription = statusDescription;
	}
}
