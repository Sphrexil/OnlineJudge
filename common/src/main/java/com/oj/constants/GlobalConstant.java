package com.oj.constants;

/**
 * description: GlobalConstants
 * date: 2022/9/7 19:27
 * author: zhenyu
 * version: 1.0
 */
public class GlobalConstant {

    /* 删除字段 */
    public static final Integer LOGIC_DELETE = 0;
    public static final Integer NOT_LOGIC_DELETE = 1;
    /* judge回调消息redis的key */
    public static final String JUDGED_RESULT_KEY = "JUDGED_RESULT_KEY";
    /* judge提交消息中消息头对应的唯一表示 */
    public static final String SUBMIT_UNIQUE_TOKEN = "SUBMIT_UNIQUE_TOKEN";
}
