package com.rngay.authority.security.jwt;

import com.rngay.feign.user.dto.UaUserDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 *
 * @FileName: JWTUserDetails
 * @Company:
 * @Date      2020年02月12日
 * @version   1.0.0
 * @remark:   jwt用户信息
 * @explain   Spring Security需要我们实现几个东西，第一个是UserDetails：这个接口中规定了用户的几个必须要有的方法，所以我们创建一个JwtUser类来实现这个接口。为什么不直接使用User类？因为这个UserDetails完全是为了安全服务的，它和我们的领域类可能有部分属性重叠，但很多的接口其实是安全定制的，所以最好新建一个类：
 *
 */
public class JwtUserDetails implements UserDetails {

    // 用户ID
    private Long userId;
    // 用户密码
    private String password;
    // 用户名
    private String username;
    // 用户角色权限
    private Collection<? extends GrantedAuthority> authorities;
    // 账号是否过期
    private Boolean isAccountNonExpired;
    // 账户是否锁定
    private Boolean isAccountNonLocked;
    // 密码是否过期
    private Boolean isCredentialsNonExpired;
    // 是否激活
    private Boolean enabled;
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
