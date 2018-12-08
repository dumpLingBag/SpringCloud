package com.rngay.common.lock;

public class Lock {

    /**
     * 默认单个业务持有锁的时间10s，防止死锁
     */
    private final static long LOCK_EXPIRE = 10 * 1000L;
    /**
     * 默认50ms尝试一次
     */
    private final static long LOCK_TRY_INTERVAL = 50L;
    /**
     * 默认尝试5s
     */
    private final static long LOCK_TRY_TIMEOUT = 5 * 1000L;
    /**
     * 最大尝试时间20s，防止线程长时间阻塞
     */
    private final static long LOCK_TRY_TIMEOUT_MAX = 20 * 1000L;

    private String name;
    private String value;

    private long expire;
    private long tryInterval;
    private long tryTimeout;

    public Lock(String name, String value){
        this(name, value, LOCK_EXPIRE, LOCK_TRY_INTERVAL, LOCK_TRY_TIMEOUT);
    }

    public Lock(String name, String value, long expire, long tryInterval, long tryTimeout){
        this.name = name;
        this.value = value;
        this.expire = expire;
        this.tryInterval = tryInterval;
        this.setTryTimeout(tryTimeout);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public long getExpire() {
        return expire;
    }

    public void setExpire(long expire) {
        this.expire = expire;
    }

    public long getTryInterval() {
        return tryInterval;
    }

    public void setTryInterval(long tryInterval) {
        this.tryInterval = tryInterval;
    }

    public long getTryTimeout() {
        return tryTimeout;
    }

    public void setTryTimeout(long tryTimeout) {
        this.tryTimeout = tryTimeout;
    }
}
