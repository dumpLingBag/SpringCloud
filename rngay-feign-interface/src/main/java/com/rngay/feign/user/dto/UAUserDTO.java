package com.rngay.feign.user.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rngay.feign.dto.CommonDTO;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Table(name = "ua_user")
public class UAUserDTO extends CommonDTO {

    @Id
    private Integer id;

    private String account;

    private String password;

    private String name;

    private Integer orgId;

    private String photo;

    private String email;

    private String mobile;

    private Integer enable;

    private List<Map<String, Object>> menuList;

    private Set<String> urlSet;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date update_time;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date create_time;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getEnable() {
        return enable;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
    }

    public Date getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public List<Map<String, Object>> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<Map<String, Object>> menuList) {
        this.menuList = menuList;
    }

    public Set<String> getUrlSet() {
        return urlSet;
    }

    public void setUrlSet(Set<String> urlSet) {
        this.urlSet = urlSet;
    }
}
