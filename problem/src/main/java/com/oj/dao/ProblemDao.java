package com.oj.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.oj.entity.ProblemEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 *
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2022-08-27 16:35:57
 */
@Mapper
public interface ProblemDao extends BaseMapper<ProblemEntity> {
}
