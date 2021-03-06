package com.rngay.common.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtil {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public Long delKeys(String key) {
        Set<String> keys = redisTemplate.keys(key);
        if (keys != null && keys.size() > 0) {
            return redisTemplate.delete(keys);
        }
        return null;
    }

    public Boolean del(String key) {
        return redisTemplate.delete(key);
    }

    public Set<String> keys(String key) {
        return redisTemplate.keys(key);
    }

    public Boolean exists(String key) {
        return redisTemplate.hasKey(key);
    }

    public Boolean expire(String key, int expireSeconds) {
        return redisTemplate.expire(key, expireSeconds, TimeUnit.SECONDS);
    }

    public Boolean expireAt(String key, Date expiredTime) {
        return redisTemplate.expireAt(key, expiredTime);
    }

    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public Object getSet(String key, Object value) {
        return redisTemplate.opsForValue().getAndSet(key, value);
    }

    public Long increment(String key) {
        return increment(key, 1L);
    }

    public Long increment(String key, long delta) {
        return redisTemplate.opsForValue().increment(key, delta);
    }

    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public void set(String key, Object value, Date expiredTime) {
        redisTemplate.opsForValue().set(key, value);
        redisTemplate.expireAt(key, expiredTime);
    }

    public void set(String key, Object value, int expireSeconds) {
        redisTemplate.opsForValue().set(key, value, expireSeconds, TimeUnit.SECONDS);
    }

    public Boolean setNx(String key, Object value) {
        return redisTemplate.opsForValue().setIfAbsent(key, value);
    }

    public Boolean setNx(String key, Object value, Date expiredTime) {
        Boolean flag = setNx(key, value);
        if (flag) {
            redisTemplate.expireAt(key, expiredTime);
        }
        return flag;
    }

    public Boolean setNx(String key, Object value, int expireSeconds) {
        Boolean flag = setNx(key, value);
        if (flag) {
            redisTemplate.expire(key, expireSeconds, TimeUnit.SECONDS);
        }
        return flag;
    }

    public Boolean zAdd(String key, double score, Object value) {
        return redisTemplate.opsForZSet().add(key, value, score);
    }

    public Boolean zAdd(String key, double score, Object value, int expireSeconds) {
        Boolean add = redisTemplate.opsForZSet().add(key, value, score);
        if (add != null && add) {
            redisTemplate.expire(key, expireSeconds, TimeUnit.SECONDS);
        }
        return add;
    }

    public Long zCard(String key) {
        return redisTemplate.opsForZSet().zCard(key);
    }

    public Double zIncrBy(String key, double score, Object value) {
        return redisTemplate.opsForZSet().incrementScore(key, value, score);
    }

    public Object range(String key, long index) {
        Set<Object> set = range(key, index, index);
        Iterator<Object> iterator = set.iterator();
        if (iterator.hasNext()) {
            return iterator.next();
        }
        return null;
    }

    public Set<Object> range(String key, long start, long end) {
        return redisTemplate.opsForZSet().range(key, start, end);
    }

    public Set<Object> zRangeByScore(String key, double min, double max) {
        return redisTemplate.opsForZSet().rangeByScore(key, min, max);
    }

    public Long zRank(String key, Object field) {
        return redisTemplate.opsForZSet().rank(key, field);
    }

    public Long zRem(String key, Object... values) {
        return redisTemplate.opsForZSet().remove(key, values);
    }

    public Object reverseRange(String key, long index) {
        Set<Object> set = reverseRange(key, index, index);
        Iterator<Object> iterator = set.iterator();
        if (iterator.hasNext()) {
            return iterator.next();
        }
        return null;
    }

    public Set<Object> reverseRange(String key, long start, long end) {
        return redisTemplate.opsForZSet().reverseRange(key, start, end);
    }

    public Long zRevRank(String key, Object field) {
        return redisTemplate.opsForZSet().reverseRank(key, field);
    }

    public Double zScore(String key, Object field) {
        return redisTemplate.opsForZSet().score(key, field);
    }

    public Long rightPushAll(String key, List<Object> objects) {
        return redisTemplate.opsForList().rightPushAll(key, objects);
    }

    public Long rightPush(String key, Object object) {
        return redisTemplate.opsForList().rightPush(key, object);
    }

    public Object leftPop(String key) {
        return redisTemplate.opsForList().leftPop(key);
    }

    public void zHashPut(String key, Object filed, Object value) {
        redisTemplate.opsForHash().put(key, filed, value);
    }

    /*public void zHashPut(String key, FiledEnum filedEnum, Object value) {
        zHashPut(key, filedEnum.getMsg(), value);
    }*/

    public Long zHashDel(String key, Object... value) {
        return redisTemplate.opsForHash().delete(key, value);
    }

    public Object getHashVal(String key, Object filed) {
        return redisTemplate.opsForHash().get(key, filed);
    }

    /*public Object getHashVal(String key, FiledEnum filedEnum) {
        return getHashVal(key, filedEnum.getMsg());
    }*/

    public Long getExpire(String key) {
        return redisTemplate.getExpire(key);
    }

}
