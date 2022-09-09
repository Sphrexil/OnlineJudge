package com.oj.utils.redisson;

@FunctionalInterface
public interface NoArgFunction<R> {

    /**
     * 执行逻辑
     *
     * @return 执行结果
     */
    R apply();
}
