package com.oj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.oj.user.UserEntity;
import com.oj.utils.ResponseResult;


/**
 * 用户表(User)表服务接口
 *
 * @author makejava
 * @since 2022-09-12 14:21:15
 */
public interface UserService extends IService<UserEntity> {
    ResponseResult userInfo();

    ResponseResult UpdateUserInfo(UserEntity user);
}
