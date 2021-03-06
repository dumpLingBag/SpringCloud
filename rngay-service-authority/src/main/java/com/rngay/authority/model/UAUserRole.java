package com.rngay.authority.model;

import com.rngay.jpa.model.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "ua_user_role")
public class UAUserRole extends BaseEntity {

	@Id
	private Long id;
	private Long userId;
	private Long roleId;

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

}
