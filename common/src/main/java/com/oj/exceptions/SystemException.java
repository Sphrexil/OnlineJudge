package com.oj.exceptions;


import com.oj.enums.ResultCode;


/**
 * @Title: 全局系统异常
 * @Description: 将一些服务通用的异常进行枚举统一管理，再统一创建
 * @Author: zhenyu
 * @DateTime: 2022/8/28 15:10
 */
public class SystemException extends RuntimeException{

    private int code;

    private String msg;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public SystemException(ResultCode httpCodeEnum) {
        super(httpCodeEnum.getMsg());
        this.code = httpCodeEnum.getCode();
        this.msg = httpCodeEnum.getMsg();
    }

}
