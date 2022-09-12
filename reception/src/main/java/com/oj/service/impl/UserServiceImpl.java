package com.oj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oj.dao.UserDao;
import com.oj.user.UserEntity;
import com.oj.pojo.vo.UserInfoVo;
import com.oj.service.UserService;
import com.oj.utils.BeanCopyUtils;
import com.oj.utils.ResponseResult;
import com.oj.utils.SecurityUtils;
import org.springframework.stereotype.Service;


/**
 * 用户表(User)表服务实现类
 *
 * @author makejava
 * @since 2022-04-10 15:41:07
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserDao, UserEntity> implements UserService {

    @Override
    public ResponseResult userInfo() {
        //读取当前用户信息
        UserEntity user = getById(SecurityUtils.getUserId());
        UserInfoVo vo= BeanCopyUtils.copyBean(user, UserInfoVo.class);
        //根据用户id查询用户信息
        //封装成成userInfo
        return ResponseResult.okResult(vo);
    }

    @Override
    public ResponseResult UpdateUserInfo(UserEntity user) {


        //读取当前用户信息
        updateById(user);

        //根据用户id修改用户信息
        //封装成userInfo
        return ResponseResult.okResult();

    }
}
