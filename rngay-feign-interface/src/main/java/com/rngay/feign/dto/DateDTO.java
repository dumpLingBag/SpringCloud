package com.rngay.feign.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;

@JsonIgnoreProperties({ "createTime", "updateTime" })
public class DateDTO {

    @TableId(type = IdType.ID_WORKER)
    private Long id;

    private Date createTime;

    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        if (this.id != null) {
            this.createTime = createTime;
        } else {
            this.createTime = new Date();
        }
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        if (updateTime == null) {
            this.updateTime = new Date();
        } else {
            this.updateTime = updateTime;
        }
    }
}
