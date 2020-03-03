package com.rngay.service_authority.security.filter;

import com.rngay.common.cache.RedisUtil;
import com.rngay.common.util.ip.IPUtil;
import com.rngay.common.util.ResultUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @FileName: MyUsernamePasswordAuthenticationFilter
 * @Company:
 * @author    ljy
 * @Date      2020年02月12日
 * @version   1.0.0
 * @remark:   自定义 登录校验
 * @explain   调用登录接口时会进入到此类的attemptAuthentication方法 进行相关校验操作
 *
 */
public class MyUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Autowired
    private RedisUtil redisUtil;

    public  MyUsernamePasswordAuthenticationFilter(AuthenticationManager authenticationManager, AuthenticationSuccessHandler successHandler, AuthenticationFailureHandler failureHandler){
        /*
        * 这句代码很重要，设置登陆的url 要和 WebSecurityConfig
        * 配置类中的.loginProcessingUrl("/authorityLogin/login") 一致
        * 如果不配置则无法执行 重写的attemptAuthentication
        * 方法里面而是执行了父类UsernamePasswordAuthenticationFilter的attemptAuthentication()
        * */
        this.setFilterProcessesUrl("/authorityLogin/login");
        // AuthenticationManager 是必须的
        this.setAuthenticationManager(authenticationManager);
        // 设置自定义登陆成功后的业务处理
        this.setAuthenticationSuccessHandler(successHandler);
        // 设置自定义登陆失败后的业务处理
        this.setAuthenticationFailureHandler(failureHandler);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        // 验证账号密码是否出错次数过多
        if (!verificationFailCount(request)) {
            ResultUtil.writeJson(response, 2, "密码出错次数过多，请两小时后再试");
            return null;
        }
        // 效验验证码
        String code = request.getParameter("code");
        String codeKey = request.getParameter("codeKey");
        if (StringUtils.isNotBlank(code) && StringUtils.isNotBlank(codeKey)) {
            String text = (String) redisUtil.get(codeKey); // 存在 redis 的验证码
            if (StringUtils.isNotBlank(text)) {
                if (!text.equalsIgnoreCase(code)) {
                    ResultUtil.writeJson(response, 2, "验证码错误");
                    return null;
                }
            } else {
                ResultUtil.writeJson(response, 2, "验证码已过期");
                return null;
            }
        }
        // 设置获取 username 的属性字段 js传到后台接收数据的参数名
        this.setUsernameParameter("account");
        // 设置获取password 的属性字段 js传到后台接收数据的参数名
        this.setPasswordParameter("password");
        return super.attemptAuthentication(request, response);
    }

    private boolean verificationFailCount(HttpServletRequest request) {
        String account = request.getParameter("account");
        String key = request.getServerName() + "_" + IPUtil.getIPAddress(request) + "_" + account;
        Object value = redisUtil.get(key);
        if (value != null) {
            return (int) value <= 5;
        }
        return true;
    }

}
