package com.rngay.service_authority.listener;

import com.rngay.service_authority.util.ContextAware;
import com.rngay.service_authority.util.UrlUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class StartUpRunner implements CommandLineRunner {

    @Value(value = "${platform.clearUrlOnRestart}")
    private Boolean clearUrlOnRestart;

    @Override
    public void run(String... args) {
        ContextAware.getService(UrlUtil.class).getUrl(clearUrlOnRestart);
    }

}
