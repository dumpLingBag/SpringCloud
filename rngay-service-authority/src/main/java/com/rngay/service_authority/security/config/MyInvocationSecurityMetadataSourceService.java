package com.rngay.service_authority.security.config;

import com.rngay.feign.authority.RoleMenuAllDTO;
import com.rngay.service_authority.security.util.UrlMatcher;
import com.rngay.service_authority.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/***
 *
 * @FileName: MyInvocationSecurityMetadataSourceService
 * @Company:
 * @author
 * @Date      2020年02月11日
 * @version   1.0.0
 * @remark:   加载资源与权限的对应关系
 * @explain 实现FilterInvocationSecurityMetadataSource接口也是必须的。 首先，这里从数据库中获取信息。 其中loadResourceDefine方法不是必须的，
 *           这个只是加载所有的资源与权限的对应关系并缓存起来，避免每次获取权限都访问数据库（提高性能），然后getAttributes根据参数（被拦截url）返回权限集合。
 *           这种缓存的实现其实有一个缺点，因为loadResourceDefine方法是放在构造器上调用的，而这个类的实例化只在web服务器启动时调用一次，那就是说loadResourceDefine方法只会调用一次，
 *           如果资源和权限的对应关系在启动后发生了改变，那么缓存起来的权限数据就和实际授权数据不一致，那就会授权错误了。但如果资源和权限对应关系是不会改变的，这种方法性能会好很多。
 *           要想解决 权限数据的一致性 可以直接在getAttributes方法里面调用数据库操作获取权限数据，通过被拦截url获取数据库中的所有权限，封装成Collection<ConfigAttribute>返回就行了。（灵活、简单
 *
 *           器启动加载顺序：1：调用loadResourceDefine()方法  2：调用supports()方法   3：调用getAllConfigAttributes()方法
 *
 *
 */
@Component
public class MyInvocationSecurityMetadataSourceService implements FilterInvocationSecurityMetadataSource {

    // 存放资源配置对象
    private Map<String, Collection<ConfigAttribute>> resourceMap = null;
    @Autowired
    private UrlMatcher urlMatcher;
    @Autowired
    private RoleService roleService;

    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        if (resourceMap == null) {
            loadResourceDefine();
        }

        String url = ((FilterInvocation) o).getRequestUrl();
        if (urlMatcher.pathMatchesUrl("/authorityLogin/captcha", url)) {
            return null;
        }
        int firstQuestionMarkIndex = url.indexOf("?");
        if (firstQuestionMarkIndex != -1) {
            url = url.substring(0, firstQuestionMarkIndex);
        }
        // 循环已有的角色配置对象 进行url匹配
        for (String resUrl : resourceMap.keySet()) {
            if (urlMatcher.pathMatchesUrl(resUrl, url)) {
                return resourceMap.get(url);
            }
        }
        return SecurityConfig.createList("ROLE_NOT_USER");
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }

    /**
     * 初始化资源，提取系统中的所有权限，加载所有url和权限（或角色）的对应关系，web容器启动就会执行
     * 如果启动@PostConstruct 注解，则web容器启动就会执行
     */
    @PostConstruct
    public void loadResourceDefine() {
        resourceMap = new ConcurrentHashMap<>();
        // 获取所有分配的角色
        List<RoleMenuAllDTO> roles = roleService.loadAllRole();
        if (!CollectionUtils.isEmpty(roles)) {
            for (RoleMenuAllDTO roleMenu : roles) {
                String authorizedSigns = roleMenu.getName() + ":" + roleMenu.getOrgId();
                ConfigAttribute configAttributes = new SecurityConfig(authorizedSigns);
                if (resourceMap.containsKey(roleMenu.getMenuUrl())) {
                    Collection<ConfigAttribute> attributes = resourceMap.get(roleMenu.getMenuUrl());
                    attributes.add(configAttributes);
                    resourceMap.put(roleMenu.getMenuUrl(), attributes);
                } else {
                    Collection<ConfigAttribute> config = new ArrayList<>();
                    config.add(configAttributes);
                    resourceMap.put(roleMenu.getMenuUrl(), config);
                }
            }
        }
    }

}
