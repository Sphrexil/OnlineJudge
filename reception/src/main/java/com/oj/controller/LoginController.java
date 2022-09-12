package com.oj.controller;

import com.oj.user.UserEntity;
import com.oj.enums.ResultCode;
import com.oj.exceptions.SystemException;
import com.oj.service.LoginService;
import com.oj.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class LoginController {

    @Autowired
    private LoginService LoginService;

    @PostMapping("/login")
    public ResponseResult login(@RequestBody UserEntity user){
        if(!StringUtils.hasText(user.getUserName())){
            //提示必须要传用户名
            throw new SystemException(ResultCode.USERNAME_NOT_NULL);
        }
        return LoginService.login(user);
    }
    @PostMapping("/logout")
    public ResponseResult logOut(){
        return LoginService.logout();
    }
}




