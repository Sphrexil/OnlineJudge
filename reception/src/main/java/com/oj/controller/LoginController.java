package com.oj.controller;

import com.oj.pojo.vo.UserReceptionLoginVo;
import com.oj.service.LoginService;
import com.oj.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class LoginController {

    @Autowired
    private LoginService LoginService;


    @PostMapping("/login")
    public ResponseResult login(@RequestBody UserReceptionLoginVo user){
        return LoginService.login(user);
    }
    @PostMapping("/logout")
    public ResponseResult logOut(){
        return LoginService.logout();
    }

    @PostMapping("/sendMail")
    public ResponseResult sendMail(String to) {
        return LoginService.sendMail(to);
    }
}




