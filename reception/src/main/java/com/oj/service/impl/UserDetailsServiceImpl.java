package com.oj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.oj.enums.ResultCode;
import com.oj.exceptions.SystemException;
import com.oj.user.LoginUser;
import com.oj.dao.UserDao;
import com.oj.user.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //根据用户名查询用户信息
        LambdaQueryWrapper<UserEntity> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(UserEntity::getUserName,username);
        UserEntity user = userDao.selectOne(queryWrapper);
        //判断是否查询用户，如果没有就抛出异常
        if(Objects.isNull(user))
        {
            throw new SystemException(ResultCode.USER_ACCOUNT_NOT_EXIST);
        }
        //返回Userinfo
        //TODO 查询权限信息封装

        return new LoginUser(user);
    }
}
