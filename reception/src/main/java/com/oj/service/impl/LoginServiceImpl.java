package com.oj.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.oj.constant.ReceptionConstant;
import com.oj.constants.GlobalConstant;
import com.oj.enums.ResultCode;
import com.oj.exceptions.SystemException;
import com.oj.mq.channels.MailSink;
import com.oj.mq.channels.MailSource;
import com.oj.pojo.vo.UserInfoVo;
import com.oj.pojo.vo.UserLoginVo;
import com.oj.pojo.vo.UserReceptionLoginVo;
import com.oj.service.UserService;
import com.oj.user.LoginUser;
import com.oj.service.LoginService;
import com.oj.user.UserEntity;
import com.oj.utils.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private MailUtils mailUtils;

    @Autowired
    private UserService userService;
    @Autowired
    private MailSource mailSource;


    @Override
    public ResponseResult login(UserReceptionLoginVo user) {
        if (!StringUtils.hasText(user.getUserName())) {
            //提示必须要传用户名
            throw new SystemException(ResultCode.USERNAME_NOT_NULL);
        }
        //判断是否重复登录
        UserEntity alreadyLoginUser = userService.getOne(new LambdaQueryWrapper<UserEntity>().eq(UserEntity::getUserName, user.getUserName()));
        if (Objects.nonNull(redisCache.getCacheObject(GlobalConstant.RECEPTION_LOGIN_TOKEN + "::" + alreadyLoginUser.getId()))) {
            throw new SystemException(ResultCode.USER_ACCOUNT_ALREADY_LOGIN);
        }
        //进行认证
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        //判断是否认证通过
        if (Objects.isNull(authenticate)) {
            throw new SystemException(ResultCode.USER_CREDENTIALS_ERROR);
        }
        //获取userid 生成token
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        mailUtils.checkMailCode(user.getCode(), loginUser.getUser().getEmail());
        String userId = loginUser.getUser().getId().toString();
        String jwt = JwtUtil.createJWT(userId);
        //把用户信息存入redis
        redisCache.setCacheObject(GlobalConstant.RECEPTION_LOGIN_TOKEN + "::" + userId, loginUser);
        //把token和userinfo封装 返回
        //把User转换成UserInfoVo
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(loginUser.getUser(), UserInfoVo.class);
        UserLoginVo vo = new UserLoginVo(jwt, userInfoVo);
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

    @Override
    public ResponseResult sendMail(String to) {
        // 邮件判空
        if (!StringUtils.hasText(to)) {
            throw new SystemException(ResultCode.EMAIL_NOT_NULL);
        }
        // TODO 反复获取验证redis，可以考虑抽取方法
        // 定义发送间隔为60s,查验是否还在间隔期
        if (Objects.nonNull(redisCache.getCacheObject(ReceptionConstant.VALIDATE_CODE_TIME_LOCK + "::" + to))) {
            throw new SystemException(ResultCode.OPERATION_TO_FREQUENT);
        }
        // 生成验证码并存入redis时效为5分钟
        // 整合消息队列
        boolean flag = mailSource.mailOutput().send(MessageBuilder.withPayload(to).build());
        return flag ? ResponseResult.okResult() : ResponseResult.errorResult(ResultCode.SEND_MAIL_FAIL);
    }

    @StreamListener(value = MailSink.MailInput)
    public void sendMailCode(@Payload String to) {

        Long code = Math.round(Math.random() * 10000);
        if (code < 1000) {
            code *= 10;
        }
        redisCache.setCacheObject(ReceptionConstant.MAIL_CODE + "::" + to,
                String.valueOf(code), ReceptionConstant.MAIL_CODE_TTL, TimeUnit.MINUTES);
        try {
            // 渲染指定的发送邮件的模板，并在需要加上相关语句地方进行用id查询组件
            String html = MailUtils.readHtmlToString(ReceptionConstant.MAIL_HTML_TEMPLATE);
            Document doc = Jsoup.parse(html);
            doc.getElementById(ReceptionConstant.MAIL_TEMPLATE_DIV_ID).html(String.valueOf(code));
            String result = doc.toString();
            // 进行发送邮件，成功则进行60s的上锁
            mailUtils.sendMail(to, ReceptionConstant.MAIL_UNIFIED_SUBJECT, result);
            redisCache.setCacheObject(ReceptionConstant.VALIDATE_CODE_TIME_LOCK + "::" + to, UUID.randomUUID(),
                    ReceptionConstant.MAIL_SEND_INTERVAL, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
            throw new SystemException(ResultCode.UNKNOWN_EXCEPTION);
        }
    }
}
