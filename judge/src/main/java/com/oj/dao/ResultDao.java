package com.oj.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.oj.entity.ResultEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * (Result)表数据库访问层
 *
 * @author makejava
 * @since 2022-09-06 09:11:01
 */
@Mapper
public interface ResultDao extends BaseMapper<ResultEntity> {

}

