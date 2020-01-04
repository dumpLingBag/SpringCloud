package com.rngay.service_authority.model;

import com.rngay.jpa.model.BaseEntity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ua_user_token")
public class UAUserToken extends BaseEntity {

    @Id
    private Long id;

    private Date expireTime;

    private String token;

    private Long userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
