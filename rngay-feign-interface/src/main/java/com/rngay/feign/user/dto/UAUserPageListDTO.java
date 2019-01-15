package com.rngay.feign.user.dto;

import com.rngay.feign.dto.PageQueryDTO;

public class UAUserPageListDTO extends PageQueryDTO {

    private String account;

    private Integer enable;

    private String name;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Integer getEnable() {
        return enable;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
