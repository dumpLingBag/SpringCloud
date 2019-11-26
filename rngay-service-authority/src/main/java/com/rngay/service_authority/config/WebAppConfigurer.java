package com.rngay.service_authority.config;

import com.rngay.service_authority.interceptor.OperatorInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebAppConfigurer implements WebMvcConfigurer {

    @Autowired
    private OperatorInterceptor operatorInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(operatorInterceptor).addPathPatterns("/**").excludePathPatterns(
                "/**/*.html", "/**/*.js", "/**/*.css", "/**/*.jpg", "/**/*.png", "/**/*.gif",
                "/**/*.eot", "/**/*.svg", "/**/*.ttf", "/**/*.woff");
    }

}
