package com.oj.service.impl;



import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oj.constant.ReceptionConstant;
import com.oj.enums.ResultCode;
import com.oj.exceptions.SystemException;
import com.oj.dao.UserDao;
import com.oj.pojo.vo.UserRegisterVo;
import com.oj.user.UserEntity;
import com.oj.service.RegisterService;
import com.oj.utils.BeanCopyUtils;
import com.oj.utils.MailUtils;
import com.oj.utils.RedisCache;
import com.oj.utils.ResponseResult;;
import io.jsonwebtoken.lang.Strings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Objects;

@Service
@Slf4j
public class RegisterServiceImpl extends ServiceImpl<UserDao, UserEntity> implements RegisterService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private MailUtils mailUtils;
    @Override
    public ResponseResult register(UserRegisterVo user) {

        //数据非空判断
        if(Objects.isNull(user.getUserName())&& !Strings.hasText(user.getUserName())){
            throw new SystemException(ResultCode.USERNAME_NOT_NULL);
        }
        if(Objects.isNull(user.getPassword())&& !Strings.hasText(user.getPassword())){
            throw new SystemException(ResultCode.PASSWORD_NOT_NULL);
        }
        if(Objects.isNull(user.getNickName())&& !Strings.hasText(user.getNickName())){
            throw new SystemException(ResultCode.NICKNAME_NOT_NULL);
        }
        if(Objects.isNull(user.getEmail())&& !Strings.hasText(user.getEmail())){
            throw new SystemException(ResultCode.EMAIL_NOT_NULL);
        }
        //解析请求中的数据,对用户密码进行加密
        if(userNameExist(user.getUserName()))
        {
            throw new SystemException(ResultCode.USER_ACCOUNT_ALREADY_EXIST);
        }
        if (userEmailExist(user.getEmail())) {
            throw new SystemException(ResultCode.EMAIL_ALREADY_EXIST);
        }
        mailUtils.checkMailCode(user.getCode(), user.getEmail());

        String encode = passwordEncoder.encode(user.getPassword());
        //
        user.setPassword(encode);
        UserEntity userEntity = BeanCopyUtils.copyBean(user, UserEntity.class);
        save(userEntity);
        //插入到数据库中
        return ResponseResult.okResult();
    }

    private boolean userNameExist(String userName) {

        LambdaQueryWrapper<UserEntity> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(UserEntity::getUserName,userName);
        int count = count(queryWrapper);
        return count > 0;
    }
    private boolean userEmailExist(String mail) {

        LambdaQueryWrapper<UserEntity> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(UserEntity::getEmail,mail);
        int count = count(queryWrapper);
        return count > 0;
    }
}
