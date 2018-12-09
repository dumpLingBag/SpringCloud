package com.rngay.service_authority.config;

import com.rngay.service_authority.interceptor.OperatorInterceptor;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

import static org.springframework.web.cors.CorsConfiguration.ALL;

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

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins(ALL).allowedMethods(ALL)
                .allowedHeaders(ALL).allowCredentials(true);
    }

    //public ServletRegistrationBean<SecurityCodeServlet> servletRegistrationBean()
}
