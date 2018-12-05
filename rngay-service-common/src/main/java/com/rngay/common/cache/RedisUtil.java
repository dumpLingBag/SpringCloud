package com.rngay.common.cache;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtil {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    public Boolean delete(String key){
        return redisTemplate.delete(key);
    }

    public Boolean hasKey(String key){
        return redisTemplate.hasKey(key);
    }

    public Boolean expire(String key, Integer expireSeconds){
        return redisTemplate.expire(key, expireSeconds, TimeUnit.SECONDS);
    }

    public Boolean expireAt(String key, Date expiredTime){
        return redisTemplate.expireAt(key, expiredTime);
    }

    public Object get(String key){
        return redisTemplate.opsForValue().get(key);
    }

    public Object getAndSet(String key, Object value){
        return redisTemplate.opsForValue().getAndSet(key, value);
    }

    public Long incr(String key){
        return increment(key, 1L);
    }

    public Long increment(String key, long delta){
        return redisTemplate.opsForValue().increment(key, delta);
    }

    public void set(String key, Object value){
        redisTemplate.opsForValue().set(key, value);
    }

    public void set(String key, Object value, Date expiredTime){
        redisTemplate.opsForValue().set(key, value);
        redisTemplate.expireAt(key, expiredTime);
    }
}
