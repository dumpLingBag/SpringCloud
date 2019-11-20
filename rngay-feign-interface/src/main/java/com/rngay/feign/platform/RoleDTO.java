package com.rngay.feign.platform;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.List;

@TableName(value = "ua_role")
public class RoleDTO {

    @TableId
    private Integer id;
    private Integer orgId;
    private String name;
    private Integer sort;
    private Integer pid;
    @TableField(exist = false)
    private List<RoleDTO> children;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public List<RoleDTO> getChildren() {
        return children;
    }

    public void setChildren(List<RoleDTO> children) {
        this.children = children;
    }
}
