package com.rngay.service_authority.model;

import com.rngay.jpa.model.BaseEntity;

import javax.persistence.*;

@Entity
@Table(name = "ua_dept_role")
public class UADeptRole extends BaseEntity {

	@Id
	private Long id;
	private Long deptId;
	private Long roleId;
	private Integer checked;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
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
