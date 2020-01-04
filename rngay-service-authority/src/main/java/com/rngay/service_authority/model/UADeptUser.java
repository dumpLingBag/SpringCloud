package com.rngay.service_authority.model;

import com.rngay.jpa.model.BaseEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "ua_dept_user")
public class UADeptUser extends BaseEntity {

    @Id
    private Long id;
    private Long deptId;
    private Long userId;
    @Column(columnDefinition = "char(1) default 1")
    private Integer checked;
    /**
     * 是否管理员
     */
    @Column(columnDefinition = "char(1) default 0")
    private Integer isManager = 0;

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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
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
