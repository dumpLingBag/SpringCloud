package com.rngay.service_socket.listener;

import com.riicy.common.cache.RedisUtil;
import com.riicy.socket.util.MessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class StartUpRunner implements CommandLineRunner {

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public void run(String... args) throws Exception {
        MessageUtil.accessToken(redisUtil);
    }
}
