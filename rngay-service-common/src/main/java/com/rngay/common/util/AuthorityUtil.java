package com.rngay.common.util;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

public class AuthorityUtil {

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

}
