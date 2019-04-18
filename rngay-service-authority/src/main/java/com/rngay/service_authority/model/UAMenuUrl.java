package com.rngay.service_authority.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="ua_menu_url", indexes={@Index(name="menuId_index", columnList="menuId"), @Index(name="urlId_index", columnList="urlId")})
public class UAMenuUrl implements Serializable {

	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Integer id;
	private Integer menuId;
	private String urlId;
	private Integer checked;

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getMenuId() {
		return menuId;
	}
	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}
	public String getUrlId() {
		return urlId;
	}
	public void setUrlId(String urlId) {
		this.urlId = urlId;
	}
	public Integer getChecked() {
		return checked;
	}
	public void setChecked(Integer checked) {
		this.checked = checked;
	}

}
