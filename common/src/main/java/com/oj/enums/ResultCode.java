package com.oj.enums;

public enum ResultCode {
    /* 成功 */
    SUCCESS(200, "成功"),
    /* 默认失败 */
    COMMON_FAIL(500, "失败"),
    /* 参数错误：1000～1999 */
    PARAM_NOT_VALID(1001, "参数无效"),
    PARAM_IS_BLANK(1002, "参数为空"),
    PARAM_TYPE_ERROR(1003, "参数类型错误"),
    PARAM_NOT_COMPLETE(1004, "参数缺失"),
    /* 用户错误 */
    USER_NOT_LOGIN(2001, "用户未登录"),
    USER_ACCOUNT_EXPIRED(2002, "账号已过期"),
    USER_CREDENTIALS_ERROR(2003, "用户名或者密码错误"),
    USER_CREDENTIALS_EXPIRED(2004, "密码过期"),
    USER_ACCOUNT_DISABLE(2005, "账号不可用"),
    USER_ACCOUNT_LOCKED(2006, "账号被锁定"),
    USER_ACCOUNT_NOT_EXIST(2007, "账号不存在"),
    USER_ACCOUNT_ALREADY_EXIST(2008, "账号已存在"),
    USER_ACCOUNT_USE_BY_OTHERS(2009, "账号下线"),
    USERNAME_NOT_NULL(2010,"用户名不能为空" ),
    PASSWORD_NOT_NULL(2011,"密码不能为空" ),
    NICKNAME_NOT_NULL(2012,"名称不能为空" ),
    EMAIL_NOT_NULL(2013,"邮箱不能为空" ),
    USER_NOT_EXIST(2014,"用户不存在"),
    OPERATION_TO_FREQUENT(2015, "操作过于频繁"),
    EMAIL_ALREADY_EXIST(2016, "邮箱已经被注册"),
    USER_ACCOUNT_ALREADY_LOGIN(2017, "账户已经登录"),
    /* 业务错误 */
    NO_PERMISSION(3001, "没有权限"),
    /* 系统错误 */
    UNKNOWN_EXCEPTION(4001,"系统未知异常"),
    /* 提交的代码重复 */
    CODE_ALREADY_EXISTS(4000, "代码已经存在"),
    /* 提交错误 */
    CODE_BLANK(5001, "提交代码为空或空串"),
    AUTHOR_BLANK(5002, "作者为空"),
    PROBLEM_BLANK(5003, "问题为空"),
    SUBMIT_RESULT_BLANK(5004, "结果获取失败为空"),
    /*  页码错误 */
    PAGE_BLANK_OR_ZERO(5005, "页码或者页码大小不能为空或0"),
    VALIDATE_CODE_ERROR(5006, "验证码错误"),
    SEND_MAIL_FAIL(5007, "验证码发送失败，请检查邮箱是否正确"),


    /* 文章错误 */
    Article_NOT_EXIST(6001, "文章不存在"), NULL_CONTENT(6002, "评论为空"), FILE_TYPE_ERROR(6003, "图片格式错误");






    private Integer code;
    private String msg;

    ResultCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMessage(String message) {
        this.msg = msg;
    }

    /**
     * 根据code获取message
     *
     * @param code
     * @return null
     */
    public static String getMessageByCode(Integer code) {
        for (ResultCode ele : values()) {
            if (ele.getCode().equals(code)) {
                return ele.getMsg();
            }
        }
        return null;
    }
}
