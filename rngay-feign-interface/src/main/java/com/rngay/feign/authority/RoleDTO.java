package com.rngay.feign.authority;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.List;

@TableName(value = "ua_role")
public class RoleDTO {

    @TableId(type = IdType.ID_WORKER)
    private Long id;
    private Integer orgId;
    private String name;
    private Integer sort;
    private Long pid;
    @TableField(exist = false)
    private List<RoleDTO> children;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public List<RoleDTO> getChildren() {
        return children;
    }

    public void setChildren(List<RoleDTO> children) {
        this.children = children;
    }
}
