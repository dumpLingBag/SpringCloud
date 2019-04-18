package com.rngay.service_authority.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="ua_user_role", indexes={@Index(name="roleId_index", columnList="roleId"), @Index(name="userId_index", columnList="userId")})
public class UAUserRole implements Serializable {

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Integer id;
	private Integer userId;
	private Integer roleId;
	private Integer checked;

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public Integer getChecked() {
		return checked;
	}
	public void setChecked(Integer checked) {
		this.checked = checked;
	}

}
