package com.rngay.feign.user.dto;

import com.rngay.feign.dto.CommonDTO;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.*;
import java.util.Date;

@Table(name = "ua_user")
public class UAUpdateUserDTO extends CommonDTO {

    @Id
    @NotNull(message = "用户ID为空")
    private Integer id;

    @NotEmpty(message = "账户信息为空")
    private String account;

    @NotEmpty(message = "邮箱为空")
    @Email(message = "请输入一个正确的邮箱")
    private String email;

    @NotEmpty(message = "手机号为空")
    @Pattern(regexp = "^((17[0-9])|(14[0-9])|(13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$", message = "请输入正确的手机号")
    private String mobile;

    @NotEmpty(message = "账户名称为空")
    private String name;

    @NotNull(message = "未选择账号状态")
    private Integer enable;

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
