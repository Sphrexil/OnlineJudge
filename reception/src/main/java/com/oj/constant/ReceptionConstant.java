package com.oj.constant;

/**
 * description: receptionConstant
 * date: 2022/9/12 20:52
 * author: zhenyu
 * version: 1.0
 */
public class ReceptionConstant {



    /* 防止验证码发得过于频繁 */
    public static final String VALIDATE_CODE_TIME_LOCK = "VALIDATE_CODE_TIME_LOCK";
    /* 验证码发送的时间间隔 默认单位为秒，故默认为1分钟 */
    public static final Integer MAIL_SEND_INTERVAL = 60;
    /* 存入的验证码key */
    public static final String MAIL_CODE = "MAIL_CODE";
    /* 验证码有效时间 默认单位位分钟 */
    public static final Integer MAIL_CODE_TTL = 5;
    /* 邮箱验证的模板html文件路径，后期需要将静态资源放在nginx下 */
    public static final String MAIL_HTML_TEMPLATE = "static/validateCode.html";
    /* 邮箱验证模板html验证码div的id */
    public static final String MAIL_TEMPLATE_DIV_ID = "code";
    /* 邮箱验证统一subject */
    public static final String MAIL_UNIFIED_SUBJECT = "ONLINE JUDGE在线邮箱验证";

}
