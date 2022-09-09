package com.oj.utils.redisson;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Function;

@Getter
@Slf4j
public class DefaultRedisLockSupport implements RedisLockSupport {

    /** 默认的redisson客户端 */
    private static volatile RedissonClient defaultRedissonClient;

    /** redisson客户端（优先级高于defaultRedissonClient，当redissonClient不为null时，使用redissonClient） */
    protected RedissonClient redissonClient;

    /** 锁 key */
    protected final String lockKey;

    /** 等待获取锁的最大时长 */
    protected long waitTime = 1L;

    /** 释放锁的最大时长 */
    protected long leaseTime = 3L;

    /** WaitTime和LeaseTime的时间单位 */
    protected TimeUnit unit = TimeUnit.SECONDS;

    public DefaultRedisLockSupport(String lockKey) {
        this.lockKey = lockKey;
    }

    public DefaultRedisLockSupport(RedissonClient redissonClient, String lockKey) {
        this.redissonClient = redissonClient;
        this.lockKey = lockKey;
    }

    public DefaultRedisLockSupport(String lockKey, long waitTime, long leaseTime) {
        this.lockKey = lockKey;
        this.waitTime = waitTime;
        this.leaseTime = leaseTime;
    }

    public DefaultRedisLockSupport(RedissonClient redissonClient, String lockKey, long waitTime, long leaseTime) {
        this.redissonClient = redissonClient;
        this.lockKey = lockKey;
        this.waitTime = waitTime;
        this.leaseTime = leaseTime;
    }

    public DefaultRedisLockSupport(String lockKey, long waitTime, long leaseTime, TimeUnit unit) {
        this.lockKey = lockKey;
        this.waitTime = waitTime;
        this.leaseTime = leaseTime;
        this.unit = unit;
    }

    public DefaultRedisLockSupport(RedissonClient redissonClient, String lockKey, long waitTime, long leaseTime, TimeUnit unit) {
        this.redissonClient = redissonClient;
        this.lockKey = lockKey;
        this.waitTime = waitTime;
        this.leaseTime = leaseTime;
        this.unit = unit;
    }

    @Override
    public <P, R> R exec(Function<P, R> function, P param) throws NotAcquiredRedisLockException {
        RedissonClient client = redissonClient();
        RLock lock = client.getLock(lockKey);
        boolean obtainLock = false;
        try {
            obtainLock = lock.tryLock(waitTime, leaseTime, unit);
        } catch (InterruptedException e) {
            // ignore
        }
        if (obtainLock) {
            try {
                return function.apply(param);
            } finally {

                if (lock.isHeldByCurrentThread()) {
                    lock.unlock();
                }
            }
        }
        throw new NotAcquiredRedisLockException(lockKey, waitTime, unit);
    }

    @Override
    public <R> R exec(NoArgFunction<R> function) throws NotAcquiredRedisLockException {
        RedissonClient client = redissonClient();
        RLock lock = client.getLock(lockKey);
        boolean obtainLock = false;
        try {
            obtainLock = lock.tryLock(waitTime, leaseTime, unit);
        } catch (InterruptedException e) {
            // ignore
        }
        if (obtainLock) {
            try {
                return function.apply();
            } finally {
                if (lock.isHeldByCurrentThread()) {
                    lock.unlock();
                }
            }
        }
        throw new NotAcquiredRedisLockException(lockKey, waitTime, unit);
    }

    @Override
    public <P> void voidExec(Consumer<P> consumer, P param) throws NotAcquiredRedisLockException {
        RedissonClient client = redissonClient();
        RLock lock = client.getLock(lockKey);
        boolean obtainLock = false;
        try {
            obtainLock = lock.tryLock(waitTime, leaseTime, unit);
        } catch (InterruptedException e) {
            // ignore
        }
        if (obtainLock) {
            try {
                consumer.accept(param);
                return;
            } finally {
                if (lock.isHeldByCurrentThread()) {
                    lock.unlock();
                }
            }
        }
        throw new NotAcquiredRedisLockException(lockKey, waitTime, unit);
    }

    @Override
    public void voidExec(NoArgConsumer consumer) throws NotAcquiredRedisLockException {
        RedissonClient client = redissonClient();
        RLock lock = client.getLock(lockKey);
        boolean obtainLock = false;
        try {
            obtainLock = lock.tryLock(waitTime, leaseTime, unit);
        } catch (InterruptedException e) {
            // ignore
        }
        if (obtainLock) {
            try {
                consumer.accept();
                return;
            } finally {
                if (lock.isHeldByCurrentThread()) {
                    lock.unlock();
                }
            }
        }
        throw new NotAcquiredRedisLockException(lockKey, waitTime, unit);
    }

    /**
     * 获取RedissonClient实例
     *
     * @return  RedissonClient实例
     */
    protected RedissonClient redissonClient() {
        if (this.redissonClient != null) {
            return this.redissonClient;
        }
        if (DefaultRedisLockSupport.defaultRedissonClient != null) {
            return DefaultRedisLockSupport.defaultRedissonClient;
        }
        throw new IllegalStateException("There is not redissonClient available.");
    }

    /**
     * 初始化默认的Redisson客户端
     *
     * @param redissonClient
     *            Redisson客户端实例
     */
    public static void initDefaultRedissonClient(RedissonClient redissonClient) {
        if (DefaultRedisLockSupport.defaultRedissonClient != null && !DefaultRedisLockSupport.defaultRedissonClient.equals(redissonClient)) {
            throw new IllegalStateException("defaultRedissonClient already been initialized.");
        }
        synchronized (DefaultRedisLockSupport.class) {
            if (DefaultRedisLockSupport.defaultRedissonClient != null) {
                if (DefaultRedisLockSupport.defaultRedissonClient.equals(redissonClient)) {
                    return;
                }
                throw new IllegalStateException("defaultRedissonClient already been initialized.");
            }
            DefaultRedisLockSupport.defaultRedissonClient = redissonClient;
        }
    }
}
