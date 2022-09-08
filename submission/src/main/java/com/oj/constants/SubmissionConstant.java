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
    /* redis锁查询List过期时间 */
    public static final Integer LOCK_TTL_LIST = 300;
    /* redis锁提交过期时间 */
    public static final Integer LOCK_TTL_SUBMIT = 300;
    /* redis锁自旋休眠时间 */
    public static final Integer LOCK_SPIN_TIME = 100;
    /* redis锁集合LIST key */
    public static final String SUBMISSION_LIST_LOCK = "SUBMISSION_LIST_LOCK";
    /* redis锁提交SUBIT key */
    public static final String SUBMIT_LOCK = "SUBMIT_LOCK";
    /* redis锁分区名 */
    public static final String CACHE_GROUP = "submission";
}
