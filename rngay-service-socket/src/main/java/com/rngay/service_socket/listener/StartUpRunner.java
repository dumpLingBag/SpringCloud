package com.rngay.service_socket.listener;

import com.rngay.common.cache.RedisUtil;
import com.rngay.service_socket.contants.RedisKeys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class StartUpRunner implements CommandLineRunner {

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("开始清空socket用户缓存");
        redisUtil.del(RedisKeys.KEY_SET_USER);
        Long aLong = redisUtil.delKeys(RedisKeys.getUserKey("*"));
        aLong = aLong == null ? 0 : aLong;
        System.out.println("成功清除"+ aLong +"条socket缓存数据");
    }
}
