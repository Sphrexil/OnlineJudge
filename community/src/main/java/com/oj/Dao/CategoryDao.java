package com.oj.Dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.oj.entity.CategoryEntity;
import org.apache.ibatis.annotations.Mapper;


/**
 * 分类表(Category)表数据库访问层
 *
 * @author makejava
 * @since 2022-04-04 14:26:34
 */
@Mapper
public interface CategoryDao extends BaseMapper<CategoryEntity> {

}


