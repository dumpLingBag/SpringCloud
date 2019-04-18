package com.rngay.feign.user.dto;

import com.rngay.feign.dto.CommonDTO;

public class UpdatePassword extends CommonDTO {

    private Integer userId;

    private String password;

    private String oldPassword;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }
}
