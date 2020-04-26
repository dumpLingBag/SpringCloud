package com.rngay.authority.security.jwt;

import com.rngay.feign.user.dto.UaUserDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * Spring Security需要我们实现几个东西，第一个是UserDetails：这个接口中规定了用户的几个必须要有的方法，所以我们创建一个JwtUser类来实现这个接口。为什么不直接使用User类？因为这个UserDetails完全是为了安全服务的，它和我们的领域类可能有部分属性重叠，但很多的接口其实是安全定制的，所以最好新建一个类：
 * @Author: pengcheng
 * @Date: 2020/4/22
 */
public class JwtUserDetails implements UserDetails {

    /**
     * 用户ID
     * @Author: pengcheng
     * @Date: 2020/4/22
     */
    private Long userId;
    /**
     * 用户密码
     * @Author: pengcheng
     * @Date: 2020/4/22
     */
    private String password;
    /**
     * 用户名
     * @Author: pengcheng
     * @Date: 2020/4/22
     */
    private String username;
    /**
     * 用户角色权限
     * @Author: pengcheng
     * @Date: 2020/4/22
     */
    private Collection<? extends GrantedAuthority> authorities;
    /**
     * 账号是否过期
     * @Author: pengcheng
     * @Date: 2020/4/22
     */
    private Boolean isAccountNonExpired;
    /**
     * 账户是否锁定
     * @Author: pengcheng
     * @Date: 2020/4/22
     */
    private Boolean isAccountNonLocked;
    /**
     * 密码是否过期
     * @Author: pengcheng
     * @Date: 2020/4/22
     */
    private Boolean isCredentialsNonExpired;
    /**
     * 是否禁用
     * @Author: pengcheng
     * @Date: 2020/4/22
     */
    private Boolean enabled;
    /**
     * 用户信息实体类
     * @Author: pengcheng
     * @Date: 2020/4/22
     */
    private UaUserDTO userInfo;

    public JwtUserDetails(UaUserDTO userInfo, Collection<? extends GrantedAuthority> authorities) {
        this.userInfo = userInfo;
        if (userInfo != null && StringUtils.isNotBlank(userInfo.getUsername())) {
            this.userId = userInfo.getId();
            this.username = userInfo.getUsername();
            this.password = userInfo.getPassword();
            this.enabled = userInfo.getEnabled() == 1;
            this.isAccountNonExpired = true;
            this.isAccountNonLocked = true;
            this.isCredentialsNonExpired = true;
            this.authorities = authorities;
        } else {
            throw new IllegalArgumentException("Cannot pass null or empty values to constructor");
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public UaUserDTO getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UaUserDTO userInfo) {
        this.userInfo = userInfo;
    }
}
