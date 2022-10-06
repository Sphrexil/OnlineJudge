package com.oj.aspect;

import com.alibaba.fastjson.JSON;
import com.oj.annoation.SystemLog;
import com.oj.utils.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Slf4j
@Component
public class LogAspect {

    @Pointcut("@annotation(com.oj.annoation.SystemLog)")
    public void pt(){

    }
    @Around("pt()")
    public Object printLog(ProceedingJoinPoint joinPoint) throws Throwable {
        Object ret = null;
        try {
            handlerBefore(joinPoint);
            ret = joinPoint.proceed();
            handlerAfter((ResponseResult) ret);
        }finally {
            log.info("=======End=======" + System.lineSeparator());
        }

        return ret;
    }

    private void handlerAfter(ResponseResult ret) {
        log.info("Response       : {}", JSON.toJSONString(ret));
    }

    private void handlerBefore(ProceedingJoinPoint joinPoint) {

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        //获取被增强方法上的的注解对象
        SystemLog systemLog=getSystemLog(joinPoint);

        log.info("=======Start=======");
        // 打印请求 URL
        log.info("URL            : {}",request.getRequestURL());
        // 打印描述信息
        log.info("BusinessName   : {}",systemLog.BusinessName());
        // 打印 Http method
        log.info("HTTP Method    : {}", request.getMethod());
        // 打印调用 controller 的全路径以及执行方法
        log.info("Class Method   : {}.{}", joinPoint.getSignature().getDeclaringTypeName(),((MethodSignature) joinPoint.getSignature()).getName());
        // 打印请求的 IP
        log.info("IP             : {}",request.getRemoteHost());
        // 打印请求入参
        log.info("Request Args   : {}", JSON.toJSONString(joinPoint.getArgs()));
    }

    private SystemLog getSystemLog(ProceedingJoinPoint joinPoint) {
        MethodSignature methodSignature= (MethodSignature) joinPoint.getSignature();
        SystemLog annotation = methodSignature.getMethod().getAnnotation(SystemLog.class);

        return annotation;
    }


}
