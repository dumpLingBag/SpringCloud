package com.rngay.feign.user.query;

import com.rngay.feign.dto.CommonDTO;

/**
 * 查询用户参数实体
 * @Author: pengcheng
 * @Date: 2020/4/15
 */
public class UserQuery extends CommonDTO {

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
