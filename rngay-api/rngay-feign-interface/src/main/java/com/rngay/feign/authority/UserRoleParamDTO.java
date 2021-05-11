package com.rngay.feign.authority;

import com.rngay.feign.dto.PageQueryDTO;

public class UserRoleParamDTO extends PageQueryDTO {

    private Long roleId;
    private String username;
    private String nickname;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
