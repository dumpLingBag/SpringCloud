package com.rngay.service_authority.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;

@Entity
@TableName(value = "ua_url")
public class UAUrl implements Serializable {

    @Id
    private String id;
    private String url;
    private String pid;
    private Integer common;
    private String name;
    @TableField(exist = false)
    private List<UAUrl> children;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public Integer getCommon() {
        return common;
    }

    public void setCommon(Integer common) {
        this.common = common;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<UAUrl> getChildren() {
        return children;
    }

    public void setChildren(List<UAUrl> children) {
        this.children = children;
    }
}
