package com.oj.user.service;

import com.alibaba.fastjson.JSON;
import com.oj.enums.ResultCode;
import com.oj.utils.ResponseResult;
import com.oj.utils.WebUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        accessDeniedException.printStackTrace();
        // 权限认证失败
        ResponseResult result = ResponseResult.errorResult(ResultCode.NO_PERMISSION);
        //响应给前端
        WebUtils.renderString(response, JSON.toJSONString(result));
    }
}
