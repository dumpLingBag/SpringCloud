package com.rngay.common.lock;

import com.rngay.common.log.LogService;
import com.rngay.common.exception.BaseException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.function.Supplier;

@Component
public class RedisDistributedLockHandler {

    @Resource
    private StringRedisTemplate template;

    private boolean tryLock(Lock lock){
        try {
            if (StringUtils.isEmpty(lock.getName()) || StringUtils.isEmpty(lock.getValue())){
                return false;
            }
            long startTime = System.currentTimeMillis();
            do {
                if (getLock(lock)){
                    return true;
                } else {
                    //存在锁
                    LogService.debug("lock is existed");
                }
                if (System.currentTimeMillis() - startTime > lock.getTryTimeout()) {
                    // 尝试超过了设定值之后直接返回失败
                    return false;
                }
                Thread.sleep(lock.getTryInterval());
            } while (true);
        } catch (InterruptedException e) {
            LogService.error(e.getMessage());
            return false;
        }
    }

    private boolean getLock(Lock lock) {
        ValueOperations<String, String> ops = template.opsForValue();
        Boolean lean = ops.setIfAbsent(lock.getName(), lock.getValue());
        if (lean != null && lean) {
            template.expire(lock.getName(), lock.getExpire(), TimeUnit.MILLISECONDS);
            return true;
        }
        return false;
    }

    /**
     * 释放锁
     * */
    private void releaseLock(Lock lock){
        if (!StringUtils.isEmpty(lock.getName())){
            template.delete(lock.getName());
        }
    }

    public <T, R> R execute(Lock lock, Function<T, R> function, T param) {
        if (tryLock(lock)) {
            try {
                return function.apply(param);
            } catch (Exception e) {
                throw new BaseException("", e);
            } finally {
                releaseLock(lock);
            }
        }
        return null;
    }

    public <R> R execute(Lock lock, Supplier<R> supplier) {
        if (tryLock(lock)) {
            try {
                return supplier.get();
            } catch (Exception e) {
                throw new BaseException("", e);
            } finally {
                releaseLock(lock);
            }
        }
        return null;
    }

}
