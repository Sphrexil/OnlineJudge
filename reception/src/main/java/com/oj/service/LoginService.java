package com.oj.service;

import com.oj.pojo.vo.UserReceptionLoginVo;
import com.oj.utils.ResponseResult;

/**
 * 用户表(User)表服务接口
 *
 * @author makejava
 * @since 2022-04-06 17:10:56
 */
public interface LoginService {

    ResponseResult login(UserReceptionLoginVo user);

    ResponseResult logout();

    ResponseResult sendMail(String to);

    ResponseResult loginValidate(String code, String userId);
}


