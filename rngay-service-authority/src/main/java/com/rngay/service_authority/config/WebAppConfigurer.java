package com.rngay.service_authority.config;

import com.rngay.service_authority.interceptor.OperatorInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

@Configuration
public class WebAppConfigurer implements WebMvcConfigurer {

    @Resource
    private OperatorInterceptor operatorInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(operatorInterceptor).addPathPatterns("/**").excludePathPatterns(
                "/**/*.html", "/**/*.js", "/**/*.css", "/**/*.jpg", "/**/*.png", "/**/*.gif",
                "/**/*.eot", "/**/*.svg", "/**/*.ttf", "/**/*.woff");
    }

    //public ServletRegistrationBean<SecurityCodeServlet> servletRegistrationBean()
}
