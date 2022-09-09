package com.oj.utils.redisson;

@FunctionalInterface
public interface NoArgConsumer {

    /**
     * 执行逻辑
     */
    void accept();
}
