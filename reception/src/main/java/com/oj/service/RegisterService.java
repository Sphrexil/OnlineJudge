package com.oj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.oj.user.UserEntity;
import com.oj.utils.ResponseResult;


public interface RegisterService extends IService<UserEntity> {


    ResponseResult register(UserEntity user);
}
