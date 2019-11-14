package com.rngay.feign.user.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import com.rngay.feign.dto.CommonDTO;

import javax.validation.constraints.*;
import java.util.Date;

@TableName(value = "ua_user")
public class UASaveUserDTO extends CommonDTO {

    private Integer id;

    @NotEmpty(message = "账户信息不能为空")
    private String account;

    @NotEmpty(message = "邮箱不能为空")
    @Email(message = "请输入一个正确的邮箱")
    private String email;

    @NotEmpty(message = "手机号不能为空")
    @Pattern(regexp = "^((13\\d)|(14[5-9])|(15[0-35-9])|(166)|(17[0-8])|(18\\d)|(19[8-9]))\\d{8}$", message = "请输入正确的手机号")
    private String mobile;

    @NotEmpty(message = "密码不能为空")
    @Pattern(regexp = "(?![0-9A-Z]+$)(?![0-9a-z]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$", message = "密码必须包含大小写字母数字，长度为6~16位")
    private String password;

    @NotEmpty(message = "账户名称不能为空")
    private String name;

    @NotNull(message = "请选择账号状态")
    private Integer enable;

    private Integer orgId;

    private Date update_time;

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

    public Integer getEnable() {
        return enable;
    }

    public void setEnable(Integer enable) {
        this.enable = enable;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
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
}
