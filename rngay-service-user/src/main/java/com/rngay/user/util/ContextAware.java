package com.rngay.user.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;

@Component
public class ContextAware implements ApplicationContextAware, ServletContextAware {

    private static ApplicationContext applicationContext;
    public static WebApplicationContext webApplicationContext;
    private static ServletContext servletContext;

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        applicationContext = context;
    }

    @Override
    public void setServletContext(ServletContext context) {
        servletContext = context;
        webApplicationContext = (WebApplicationContext) servletContext.getAttribute("org.springframework.web.context.WebApplicationContext.ROOT");
    }

    public boolean check() {
        return applicationContext != null && servletContext != null;
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static ApplicationContext getContext() {
        return applicationContext;
    }

    public static ServletContext getServletContext() {
        return servletContext;
    }

    public static <T> T getService(Class<T> c) {
        return applicationContext.getBean(c);
    }

    public static Object getBean(Class<?> c) {
        return applicationContext.getBean(c);
    }

    public static Object getBean(String name) {
        return applicationContext.getBean(name);
    }

    public static String firstCharToLowerCase(String string) {
        char[] chars = string.toCharArray();
        chars[0] = toLowerCase(chars[0]);
        return String.valueOf(chars);
    }

    public static char toLowerCase(char character) {
        if (65 <= character && character <= 90) {
            character ^= 32;
        }
        return character;
    }

}
