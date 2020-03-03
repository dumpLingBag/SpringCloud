package com.rngay.service_authority.security.config;

import com.rngay.common.util.JsonUtil;
import com.rngay.feign.user.dto.UaUserDTO;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collection;

/**
 *
 * @FileName: MyAccessDecisionManager
 * @Company:
 * @author
 * @Date      2020年02月11日
 * @version   1.0.0
 * @remark:   资源权限认证器  证用户是否拥有所请求资源的权限
 * @explain   接口AccessDecisionManager也是必须实现的。 decide方法里面写的就是授权策略了，需要什么策略，可以自己写其中的策略逻辑
 *             认证通过就返回，不通过抛异常就行了，spring security会自动跳到权限不足处理类（WebSecurityConfig 类中 配置文件上配的）
 *
 *
 */
@Component
public class MyAccessDecisionManager implements AccessDecisionManager {

    /**
     *  授权策略
     *
     * decide()方法在url请求时才会调用，服务器启动时不会执行这个方法
     *
     * @param configAttributes 装载了请求的url允许的角色数组 。这里是从MyInvocationSecurityMetadataSource里的loadResourceDefine方法里的atts对象取出的角色数据赋予给了configAttributes对象
     * @param object url
     * @param authentication 装载了从数据库读出来的权限(角色) 数据。这里是从MyUserDetailService里的loadUserByUsername方法里的grantedAuths对象的值传过来给 authentication 对象,简单点就是从spring的全局缓存SecurityContextHolder中拿到的，里面是用户的权限信息
     *
     * 注意： Authentication authentication 如果是前后端分离 则有跨域问题，跨域情况下 authentication 无法获取当前登陆人的身份认证(登陆成功后)，我尝试用token来效验权限
     *
     */
    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        // 超级管理员可以访问所有url
        String principal = (String) authentication.getPrincipal();
        if (principal != null) {
            UaUserDTO uaUserDTO = JsonUtil.string2Obj(principal, UaUserDTO.class);
            if (uaUserDTO != null && uaUserDTO.getParentId() == 0) {
                return;
            }
        }
        // 无权访问
        if (CollectionUtils.isEmpty(configAttributes)) {
            throw new AccessDeniedException("无权访问");
        }
        for (ConfigAttribute configAttribute : configAttributes) {
            String needRole = configAttribute.getAttribute();
            if (needRole.equals("ROLE_NOT_USER")) {
                if (authentication instanceof AnonymousAuthenticationToken) {
                    throw new BadCredentialsException("请登录");
                }
                return;
            }
            for (GrantedAuthority grantedAuthority : authentication.getAuthorities()) {
                // grantedAuthority 为用户所被赋予的权限。 needRole 为访问相应的资源应该具有的权限。
                // 判断两个请求的url的权限和用户具有的权限是否相同，如相同，允许访问 权限就是那些以ROLE_为前缀的角色
                if (needRole.trim().equals(grantedAuthority.getAuthority().trim())) {
                    // 匹配到对应的角色，则允许通过
                    return;
                }
            }
        }
        throw new AccessDeniedException("无权访问");
    }

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
