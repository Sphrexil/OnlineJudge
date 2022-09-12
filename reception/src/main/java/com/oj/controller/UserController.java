package com.oj.controller;


import com.oj.user.UserEntity;
import com.oj.service.RegisterService;
import com.oj.service.UserService;
import com.oj.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private RegisterService RegisterService;

    @GetMapping("/userInfo")
    public ResponseResult userInfo(){
        return userService.userInfo();
    }

    @PutMapping("/userInfo")
    public ResponseResult UpdateUserInfo(
            @RequestBody UserEntity user){
        return userService.UpdateUserInfo(user);
    }
    @PostMapping("/register")
    public ResponseResult register(@RequestBody UserEntity user)
    {
        return RegisterService.register(user);
    }
}
