package com.oj.Dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.oj.entity.ArticleEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 文章表(Article)表数据库访问层
 *
 * @author makejava
 * @since 2022-04-03 17:10:03
 */
@Mapper
public interface ArticleDao extends BaseMapper<ArticleEntity>{
}


