package com.oj.constants;

/**
 * description: SubmissionConstant
 * date: 2022/9/7 16:04
 * author: zhenyu
 * version: 1.0
 */
public class SubmissionConstant {

    /* redis锁脚本 */
    public static final String LOCK_SCRIPT = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
    /* redis锁过期时间 */
    public static final Integer LOCK_TTL = 300;
    /* redis锁自旋休眠时间 */
    public static final Integer LOCK_SPIN_TIME = 100;
    /* redis锁key */
    public static final String LOCK_KEY = "lock";
    /* redis锁分区名 */
    public static final String CACHE_GROUP = "submission";
}
