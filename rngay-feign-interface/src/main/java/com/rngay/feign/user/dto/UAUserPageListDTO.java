package com.rngay.feign.user.dto;

import com.rngay.feign.dto.PageQueryDTO;

public class UAUserPageListDTO extends PageQueryDTO {

    private String username;

    private Integer enable;

    private String nickname;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getEnable() {
        return enable;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

}
