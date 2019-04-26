package com.rngay.service_authority.util;

import com.rngay.common.cache.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class AuthorityUtil {

    @Autowired
    private RedisUtil redisUtil;

    public static final String AUTHORITY_USER_ID = "authority_user_id";
    public static final String AUTHORITY_USER = "authority_user";
    public static final String AUTHORITY_USER_ORGANIZATION = "authority_user_organization";
    public static final String AUTHORITY_USER_ACCOUNT = "authority_user_accout";
    public static final String AUTHORITY_USER_PASSWORD = "authority_user_password";
    public static final String AUTHORITY_USER_LOGIN_STATUS = "authority_user_login_status";
    public static final String APPLICATION_COMMON_URL_KEY = "application_common_url";
    public static final String APPLICATION_ORGANIZATION = "application_organization";

    /**
     * 获取请求的token
     */
    public static String getRequestToken(HttpServletRequest request){
        //从header中获取token
        String token = request.getHeader("token");
        //如果header中不存在token，则从参数中获取token
        if(StringUtils.isBlank(token)){
            token = request.getParameter("token");
        }
        return token;
    }

    /**
    * 获取主机真实 ip 地址
    * @Author: pengcheng
    * @Date: 2018/12/14
    */
    public static String getIPAddress(HttpServletRequest request) {

        String ip = request.getHeader("X-Forwarded-For");

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_CLIENT_IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("X-Real-IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
            }
        } else if (ip.length() > 15) {
            String[] ips = ip.split(",");
            for (String strIp : ips) {
                if (!("unknown".equalsIgnoreCase(strIp))) {
                    ip = strIp;
                    break;
                }
            }
        }
        return ip;
    }

}
