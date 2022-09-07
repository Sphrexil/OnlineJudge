package com.oj.constants;

/**
 * description: ProblemConstant
 * date: 2022/9/7 15:41
 * author: zhenyu
 * version: 1.0
 */
public class ProblemConstant {

    /* 问题是否可见 */
    public enum IsProblemVisible {
        ABLE_TO_SEE(1, "可见"),
        UNABLE_TO_SEE(0, "不可见");
        private int code;
        private String msg;

        public int getCode() {
            return code;
        }

        public String getMsg() {
            return msg;
        }

        IsProblemVisible(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }
    }
    /* 问题的难度 */
    public static final String DIFFICULT_PROBLEM = "困难";
    public static final String MEDIUM_PROBLEM = "中等";
    public static final String SIMPLE_PROBLEM = "简单";
}
