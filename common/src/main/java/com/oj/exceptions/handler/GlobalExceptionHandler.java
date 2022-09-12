package com.oj.exceptions.handler;

import com.oj.enums.ResultCode;
import com.oj.exceptions.SystemException;
import com.oj.utils.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * @Title: 全局异常处理
 * @Description:
 * @Author: zhenyu
 * @DateTime: 2022/8/28 15:11
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseResult exceptionHandler(Exception e){
        //打印异常信息
        log.error("出现了未知异常:",e);
        //从异常对象中获取提示信息封装返回
        return ResponseResult.errorResult(ResultCode.UNKNOWN_EXCEPTION.getCode(), ResultCode.UNKNOWN_EXCEPTION.getMsg());
    }
    @ExceptionHandler(SystemException.class)
    public ResponseResult systemExceptionHandler(SystemException e) {
        return ResponseResult.okResult(e.getCode(), e.getMsg());
    }

    @ExceptionHandler(InternalAuthenticationServiceException.class)
    public ResponseResult authExceptionHandler(InternalAuthenticationServiceException e) {

        if (e.getCause() instanceof SystemException) {
            SystemException systemException = (SystemException) e.getCause();
            return ResponseResult.errorResult(systemException.getCode(), systemException.getMsg());
        }
        return ResponseResult.errorResult(ResultCode.UNKNOWN_EXCEPTION.getCode(), e.getCause().getMessage());
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseResult validateExceptionHandler(MethodArgumentNotValidException e){
        //打印异常信息
        String msg = e.getBindingResult().getFieldError().getDefaultMessage();
        log.error("出现了异常!:",e);
        if (ResultCode.AUTHOR_BLANK.getMsg().equals(msg)) {
            return ResponseResult.errorResult(ResultCode.AUTHOR_BLANK);
        } else if (ResultCode.CODE_BLANK.getMsg().equals(msg)) {
            return ResponseResult.errorResult(ResultCode.CODE_BLANK);
        } else if (ResultCode.PROBLEM_BLANK.getMsg().equals(msg)) {
            return ResponseResult.errorResult(ResultCode.PROBLEM_BLANK);
        }
        //从异常对象中获取提示信息封装返回
        return ResponseResult.errorResult(ResultCode.UNKNOWN_EXCEPTION.getCode(),e.getMessage());
    }
}
