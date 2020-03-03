package com.rngay.service_authority.security;

import com.rngay.common.vo.Result;
import com.rngay.feign.authority.RoleDTO;
import com.rngay.feign.user.dto.UaUserDTO;
import com.rngay.feign.user.service.PFUserService;
import com.rngay.service_authority.security.jwt.JwtUserDetails;
import com.rngay.service_authority.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @FileName: MyUserDetailService
 * @Company:
 * @author
 * @Date      2020年02月11日
 * @version   1.0.0
 * @remark:   配置用户权限认证
 * @explain   当用户登录时会进入此类的loadUserByUsername方法对用户进行验证，验证成功后会被保存在当前会话的principal对象中
 *            系统获取当前登录对象信息方法 WebUserDetails webUserDetails = (WebUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
 *
 *              异常信息：
 *              UsernameNotFoundException     用户找不到
 *              BadCredentialsException       坏的凭据
 *              AccountExpiredException       账户过期
 *              LockedException               账户锁定
 *              DisabledException             账户不可用
 *              CredentialsExpiredException   证书过期
 *
 *
 */
@Service
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    private PFUserService pfUserService;
    @Autowired
    private RoleService roleService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Result<UaUserDTO> result = pfUserService.findByAccount(username);
        UaUserDTO uaUser = result.getData();
        if (result.getCode() == 1 || result.getData() == null) {
            throw new UsernameNotFoundException("用户不存在");
        }

        // 获取用户拥有的角色
        List<RoleDTO> roleList = roleService.loadUserRole(uaUser);
        Set<GrantedAuthority> grantedAuth = new HashSet<>();
        if (roleList.size() > 0) {
            roleList.forEach(role -> grantedAuth.add(new SimpleGrantedAuthority(role.getAuthName()+"_"+role.getOrgId())));
        }
        return new JwtUserDetails(uaUser, grantedAuth);
    }
}
