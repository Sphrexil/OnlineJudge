package com.oj.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * description: UserRegisterVo
 * date: 2022/9/12 20:35
 * author: zhenyu
 * version: 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterVo {
    //用户名
    private String userName;
    //昵称
    private String nickName;
    //密码
    private String password;
    //邮箱
    private String email;
}
