package com.rngay.service_authority.model;

import com.rngay.jpa.model.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "ua_user_role")
public class UAUserRole extends BaseEntity {

	@Id
	private Long id;
	private Long userId;
	private Long roleId;
	private Integer checked;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Integer getChecked() {
		return checked;
	}

	public void setChecked(Integer checked) {
		this.checked = checked;
	}

}
