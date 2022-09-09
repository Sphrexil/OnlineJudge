package com.oj.utils.redisson;

import lombok.Getter;

import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * redis锁支持
 *
 * @author JustryDeng
 * @since 2022/4/19 9:36
 */
public interface RedisLockSupport {

    /**
     * 执行同步逻辑
     * <br />
     * 此逻辑，应由redis lock保证全局同步
     *
     * @param function
     *            业务逻辑块
     * @param param
     *            参数
     *
     * @throws NotAcquiredRedisLockException 获取redis锁时抛出失败
     * @return 逻辑执行结果
     */
    <P, R> R exec(Function<P, R> function, P param) throws NotAcquiredRedisLockException;

    /**
     * 执行同步逻辑
     * <br />
     * 此逻辑，应由redis lock保证全局同步
     *
     * @param function
     *            业务逻辑块
     * @return 执行结果
     *
     * @throws NotAcquiredRedisLockException 获取redis锁时抛出失败
     */
    <R> R exec(NoArgFunction<R> function) throws NotAcquiredRedisLockException;

    /**
     * 执行同步逻辑
     * <br />
     * 此逻辑，应由redis lock保证全局同步
     *
     * @param consumer
     *            业务逻辑块
     * @param param
     *            参数
     *
     * @throws NotAcquiredRedisLockException 获取redis锁时抛出失败
     */
    <P> void voidExec(Consumer<P> consumer, P param) throws NotAcquiredRedisLockException;

    /**
     * 执行同步逻辑
     * <br />
     * 此逻辑，应由redis lock保证全局同步
     *
     * @param consumer
     *            业务逻辑块
     * @throws NotAcquiredRedisLockException 获取redis锁时抛出失败
     */
    void voidExec(NoArgConsumer consumer) throws NotAcquiredRedisLockException;

    /**
     * 获取redis lock失败
     *
     * @author JustryDeng
     * @since 2022/4/19 10:44
     */
    @Getter
    class NotAcquiredRedisLockException extends RuntimeException{

        /** 锁 key */
        private final String lockKey;

        /** 等待获取锁的最大时长 */
        private final long waitTime;

        /** waitTime的时间单位 */
        private final TimeUnit timeUnit;

        public NotAcquiredRedisLockException(String lockKey, long waitTime, TimeUnit timeUnit) {
            super(String.format("lockKey=%s, waitTime=%d, timeUnit=%s", lockKey, waitTime, timeUnit));
            this.lockKey = lockKey;
            this.waitTime = waitTime;
            this.timeUnit = timeUnit;
        }
    }

}
