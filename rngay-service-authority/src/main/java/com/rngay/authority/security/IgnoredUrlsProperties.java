package com.rngay.authority.security;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "ignored")
public class IgnoredUrlsProperties {

    private List<String> urls = new ArrayList<>();
    private List<String> pathsToSkip = new ArrayList<>();

    public List<String> getUrls() {
        return urls;
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }

    public List<String> getPathsToSkip() {
        return pathsToSkip;
    }

    public void setPathsToSkip(List<String> pathsToSkip) {
        this.pathsToSkip = pathsToSkip;
    }

}
