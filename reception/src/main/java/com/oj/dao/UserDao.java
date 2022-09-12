package com.oj.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.oj.user.UserEntity;
import org.apache.ibatis.annotations.Mapper;


/**
 * 用户表(User)表数据库访问层
 *
 * @author makejava
 * @since 2022-09-12 14:25:08
 */
@Mapper
public interface UserDao extends BaseMapper<UserEntity> {

}
