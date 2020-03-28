package com.rngay.feign.user.dto;

import com.rngay.feign.dto.PageQueryDTO;

public class UaUserPageListDTO extends PageQueryDTO {

    private String username;

    private Integer enabled;

    private String nickname;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getEnabled() {
        return enabled;
    }

    public void setEnabled(Integer enabled) {
        this.enabled = enabled;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

}
