package com.rngay.feign.authority;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rngay.feign.dto.BaseDTO;

import java.util.List;

@TableName(value = "ua_role")
public class RoleDTO extends BaseDTO {

    @TableId(type = IdType.ID_WORKER)
    private Long id;
    private Integer orgId;
    private String name;
    private Integer enabled;
    private Integer sort;
    private Long pid;
    @TableField(exist = false)
    private String label;
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

    public Integer getEnabled() {
        return enabled;
    }

    public void setEnabled(Integer enabled) {
        this.enabled = enabled;
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

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<RoleDTO> getChildren() {
        return children;
    }

    public void setChildren(List<RoleDTO> children) {
        this.children = children;
    }
}
