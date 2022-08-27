package com.oj.exceptions.handler;

import com.oj.enums.ResultCode;
import com.oj.utils.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseResult exceptionHandler(Exception e){
        //打印异常信息
        log.error("出现了未知异常！ {}",e);
        //从异常对象中获取提示信息封装返回
        return ResponseResult.errorResult(ResultCode.UNKNOWN_EXCEPTION.getCode(),e.getMessage());
    }

}
