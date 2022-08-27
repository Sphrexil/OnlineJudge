package com.oj.utils;

import com.oj.enums.ResultCode;

import java.io.Serializable;

public class ResponseResult<T> implements Serializable {

    /**
     * @Title: 公共返回类
     * @Description: TODO
     * @Author: zhenyu
     * @DateTime: 2022/8/27 20:17
     * @param code 状态码
     * @param msg 状态消息
     * @param data 携带的数据
     */

    private Integer code;
    private String msg;
    private T data;

    /* 成功返回 */
    public ResponseResult() {
        this.code = ResultCode.SUCCESS.getCode();
        this.msg = ResultCode.SUCCESS.getMsg();
    }

    public ResponseResult(Integer code, T data) {
        this.code = code;
        this.data = data;
    }

    public ResponseResult(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static ResponseResult okResult() {
        ResponseResult result = new ResponseResult();
        return result;
    }

    public static ResponseResult okResult(int code, String msg) {
        ResponseResult result = new ResponseResult();
        return result.ok(code, null, msg);
    }

    public static ResponseResult okResult(Object data) {
        ResponseResult result = setResultCode(ResultCode.SUCCESS, ResultCode.SUCCESS.getMsg());
        if (data != null) {
            result.setData(data);
        }
        return result;
    }

    /* 失败返回 */
    public static ResponseResult errorResult(int code, String msg) {
        ResponseResult result = new ResponseResult();
        return result.error(code, msg);
    }

    public static ResponseResult errorResult(ResultCode enums) {
        return setResultCode(enums, enums.getMsg());
    }

    public static ResponseResult errorResult(ResultCode enums, String msg) {
        return setResultCode(enums, msg);
    }

    /* 实体设置 */
    public static ResponseResult setResultCode(ResultCode enums) {
        return okResult(enums.getCode(), enums.getMsg());
    }

    private static ResponseResult setResultCode(ResultCode enums, String msg) {
        return okResult(enums.getCode(), msg);
    }

    public ResponseResult<?> error(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
        return this;
    }

    public ResponseResult<?> ok(Integer code, T data) {
        this.code = code;
        this.data = data;
        return this;
    }

    public ResponseResult<?> ok(Integer code, T data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
        return this;
    }

    public ResponseResult<?> ok(T data) {
        this.data = data;
        return this;
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

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
