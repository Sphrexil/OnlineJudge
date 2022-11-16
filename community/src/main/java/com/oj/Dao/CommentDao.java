package com.oj.Dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.oj.entity.CommentEntity;
import org.apache.ibatis.annotations.Mapper;


/**
 * 评论表(SgComment)表数据库访问层
 *
 * @author makejava
 * @since 2022-04-09 15:29:55
 */
@Mapper
public interface CommentDao extends BaseMapper<CommentEntity> {

    Long getMaxId();
}


