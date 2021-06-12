package com.amalitech.amalitechprojectdashboard.web.dto;

import com.amalitech.amalitechprojectdashboard.models.utils.AuditModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoleDto extends AuditModel {
	private long id;
	String name;
	private boolean deleted;
}
