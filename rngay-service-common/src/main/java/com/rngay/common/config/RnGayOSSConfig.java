package com.rngay.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "rngay-oss")
@PropertySource(value = {
        "classpath:rngay-oss-${spring.profiles.active}.properties",
        "file:rngay-oss-${spring.profiles.active}.properties",
        "file:config/rngay-oss-${spring.profiles.active}.properties"
}, ignoreResourceNotFound = true)
public class RnGayOSSConfig {

    private String address;
    private int port;
    private String userName;
    private String password;
    private String basePath;
    private String baseUrl;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBasePath() {
        return basePath;
    }

    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }
}
