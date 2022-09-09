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
        private Integer code;
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
    public enum ProblemDifficultyDegree {
        DIFFICULT_PROBLEM(3, "困难"),
        MEDIUM_PROBLEM(2, "中等"),
        SIMPLE_PROBLEM(1, "简单");
        private Integer code;
        private String msg;

        public int getCode() {
            return code;
        }

        public String getMsg() {
            return msg;
        }

        ProblemDifficultyDegree(Integer code, String msg) {
            this.code = code;
            this.msg = msg;
        }
    }
}
