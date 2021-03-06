package com.rngay.feign.authority;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rngay.feign.dto.CommonDTO;

import java.util.List;

@TableName(value = "ua_url")
public class UrlDTO extends CommonDTO {

    @TableId(type = IdType.ID_WORKER)
    private String id;
    private String url;
    private String pid;
    private String name;
    private Integer common;
    @TableField(exist = false)
    private List<UrlDTO> children;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCommon() {
        return common;
    }

    public void setCommon(Integer common) {
        this.common = common;
    }

    public List<UrlDTO> getChildren() {
        return children;
    }

    public void setChildren(List<UrlDTO> children) {
        this.children = children;
    }
}
