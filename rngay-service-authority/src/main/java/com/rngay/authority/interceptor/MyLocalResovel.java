package com.rngay.authority.interceptor;

import com.rngay.common.util.CookieUtil;
import com.rngay.common.util.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

@Configuration
public class MyLocalResovel implements LocaleResolver {
    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        String cookie = CookieUtil.getCookie(request, "lang");
        if (StringUtils.isNotBlank(cookie)) {
            String[] lang = cookie.split("_");
            return new Locale(lang[0], lang[1]);
        }
        return null;
    }

    @Override
    public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {

    }

    @Bean//8.把该配置文件配置成容器的Bean,就可以起作用。
    public LocaleResolver localeResolver(){
        return new MyLocalResovel();
    }
}
