package com.oj.utils;



import com.oj.user.LoginUser;
import org.springframework.security.core.context.SecurityContextHolder;


/**
 * @Author 三更  B站： https://space.bilibili.com/663528522
 */
public class SecurityUtils
{

    /**
     * 获取用户
     **/
    public static LoginUser getLoginUser()
    {
        return (LoginUser) getAuthentication();
    }

    /**
     * 获取Authentication
     * @return
     */
    public static Object getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public static Boolean isAdmin(){
        Long id = getLoginUser().getUser().getId();
        return id != null && 1L == id;
    }

    public static Long getUserId() {
        return getLoginUser().getUser().getId();
    }
}
