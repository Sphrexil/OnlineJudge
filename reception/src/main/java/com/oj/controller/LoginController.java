package com.oj.controller;

import com.oj.pojo.vo.UserReceptionLoginVo;
import com.oj.pojo.vo.ValidateVo;
import com.oj.service.LoginService;
import com.oj.utils.ResponseResult;
import io.swagger.annotations.ApiImplicitParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;


    @PostMapping("/login")
    public ResponseResult login(@RequestBody UserReceptionLoginVo user){
        return loginService.login(user);
    }

    @PostMapping("/loginValidate")
    public ResponseResult loginValidate(@RequestBody ValidateVo data) {
        return loginService.loginValidate(data.getCode(), data.getUserId());
    }

    @PostMapping("/logout")
    @ApiImplicitParam(name = "token", value = "token", required = true, dataType = "String", paramType = "header")
    public ResponseResult logOut(){
        return loginService.logout();
    }

    @PostMapping("/sendMail")
    public ResponseResult sendMail(String to) {
        return loginService.sendMail(to);
    }
}




