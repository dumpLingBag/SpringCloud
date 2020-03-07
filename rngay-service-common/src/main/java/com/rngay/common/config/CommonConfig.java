package com.rngay.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "common")
@PropertySource(value = {
        "classpath:common-${spring.profiles.active}.properties"
}, ignoreResourceNotFound = true)
public class CommonConfig {
}
