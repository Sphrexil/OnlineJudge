package com.oj.controller;


import com.oj.pojo.vo.UserRegisterVo;
import com.oj.user.UserEntity;
import com.oj.service.RegisterService;
import com.oj.service.UserService;
import com.oj.utils.ResponseResult;
import io.swagger.annotations.ApiImplicitParam;
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
    @ApiImplicitParam(name = "token", value = "token", required = true, dataType = "String", paramType = "header")
    public ResponseResult userInfo(){
        return userService.userInfo();
    }

    @PutMapping("/userInfo")
    public ResponseResult UpdateUserInfo(
            @RequestBody UserEntity user){
        return userService.UpdateUserInfo(user);
    }
    @PostMapping("/register")
    public ResponseResult register(@RequestBody UserRegisterVo user)
    {
        return RegisterService.register(user);
    }

}
