package com.oj.service.impl;


import com.oj.constant.ReceptionConstant;
import com.oj.constants.GlobalConstant;
import com.oj.enums.ResultCode;
import com.oj.exceptions.SystemException;
import com.oj.pojo.vo.UserInfoVo;
import com.oj.pojo.vo.UserLoginVo;
import com.oj.pojo.vo.UserReceptionLoginVo;
import com.oj.user.LoginUser;
import com.oj.user.UserEntity;
import com.oj.service.LoginService;
import com.oj.utils.BeanCopyUtils;
import com.oj.utils.JwtUtil;
import com.oj.utils.RedisCache;
import com.oj.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    @Override
    public ResponseResult login(UserReceptionLoginVo user) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        //判断是否认证通过
        if(Objects.isNull(authenticate)){
            throw new SystemException(ResultCode.USER_CREDENTIALS_ERROR);
        }
        //获取userid 生成token
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
        String jwt = JwtUtil.createJWT(userId);
        //把用户信息存入redis
        redisCache.setCacheObject(GlobalConstant.RECEPTION_LOGIN_TOKEN + "::" + userId,loginUser);

        //把token和userinfo封装 返回
        //把User转换成UserInfoVo
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(loginUser.getUser(), UserInfoVo.class);
        UserLoginVo vo = new UserLoginVo(jwt,userInfoVo);
        return ResponseResult.okResult(vo);
    }

    @Override
    public ResponseResult logout() {
        //获取token 解析获取userid
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        //获取userid
        Long userId = loginUser.getUser().getId();
        //删除redis中的用户信息
        redisCache.deleteObject(GlobalConstant.RECEPTION_LOGIN_TOKEN + "::" + userId);
        return ResponseResult.okResult();
    }
}
