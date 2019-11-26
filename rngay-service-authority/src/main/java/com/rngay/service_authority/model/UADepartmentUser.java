package com.rngay.service_authority.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "ua_department_user")
public class UADepartmentUser implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private Long departmentId;
    private Long userId;
    @Column(columnDefinition = "int default 1")
    private Integer checked;
    /**
     * 是否管理员
     */
    @Column(columnDefinition = "int default 0")
    private Integer isManager = 0;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
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
