package com.rngay.service_authority.model;

import com.baomidou.mybatisplus.annotation.TableName;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@TableName(value = "ua_department_user")
public class UADepartmentUser implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    private Integer departmentId;
    private Integer userId;
    @Column(columnDefinition = "int default 1")
    private Integer checked;
    /**
     * 是否管理员
     */
    @Column(columnDefinition = "int default 0")
    private Integer isManager = 0;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getDepartmentId() {
        return departmentId;
    }
    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }
    public Integer getUserId() {
        return userId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
	public Integer getChecked() {
        return checked;
    }
    public void setChecked(Integer checked) {
        this.checked = checked;
    }
    public Integer getIsManager() {
        return isManager;
    }
    public void setIsManager(Integer isManager) {
        this.isManager = isManager;
    }

}
