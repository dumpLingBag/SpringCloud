package com.rngay.feign.user.dto;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rngay.feign.authority.RoleDTO;
import com.rngay.feign.authority.UserRoleDTO;
import com.rngay.feign.dto.BaseDTO;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Date;
import java.util.List;

@TableName(value = "ua_user")
public class UaUserDTO extends BaseDTO {

    @Excel(name = "用户id")
    @TableId(type = IdType.ID_WORKER)
    private Long id;

    @Excel(name = "用户名")
    @NotBlank(message = "用户名不能为空")
    @Pattern(regexp = "^[A-Za-z0-9_]{3,16}$", message = "用户名只能为字母，数字，下划线长度为3-16个字符")
    private String username;

    @NotBlank(message = "密码不能为空")
    private String password;

    @Excel(name = "用户密码")
    @NotBlank(message = "用户昵称不能为空")
    @Length(min = 2, max = 16, message = "用户昵称为3-16个字符")
    private String nickname;

    @Excel(name = "机构id")
    private Long orgId;

    @Excel(name = "头像")
    private String avatar;

    @Excel(name = "邮箱")
    @Email(message = "邮箱格式不对")
    @NotBlank(message = "邮箱不能为空")
    private String email;

    @Excel(name = "手机号")
    @NotBlank(message = "手机号码不能为空")
    private String mobile;

    @Excel(name = "用户状态", replace = {"启用_1", "禁用_0"})
    private Integer enabled;

    @Excel(name = "性别", replace = {"男_0", "女_1"})
    private Integer sex;

    @Excel(name = "父级id")
    private Long parentId;

    @TableField(exist = false)
    private Date expireTime;

    @TableField(exist = false)
    private Boolean checked;

    @TableField(exist = false)
    private List<UserRoleDTO> roles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
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

    public Integer getEnabled() {
        return enabled;
    }

    public void setEnabled(Integer enabled) {
        this.enabled = enabled;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public List<UserRoleDTO> getRoles() {
        return roles;
    }

    public void setRoles(List<UserRoleDTO> roles) {
        this.roles = roles;
    }
}
