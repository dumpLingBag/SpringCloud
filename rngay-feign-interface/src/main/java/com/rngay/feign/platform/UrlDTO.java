package com.rngay.feign.platform;

import com.rngay.feign.dto.CommonDTO;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "ua_url")
public class UrlDTO extends CommonDTO {

    @Id
    private String id;
    private String url;
    private String pid;
    private Integer common;

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
}
