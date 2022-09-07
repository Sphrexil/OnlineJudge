package com.oj.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.oj.entity.TestCaseEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TestCaseDao extends BaseMapper<TestCaseEntity> {
}
