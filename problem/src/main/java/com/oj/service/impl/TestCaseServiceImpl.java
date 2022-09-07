package com.oj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oj.dao.TestCaseDao;
import com.oj.entity.TestCaseEntity;
import com.oj.service.TestCaseService;
import com.oj.utils.ResponseResult;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestCaseServiceImpl extends ServiceImpl<TestCaseDao, TestCaseEntity> implements TestCaseService {

    @Override
    public ResponseResult getTestCaseListByProblemID(Integer problemID){
        LambdaQueryWrapper<TestCaseEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TestCaseEntity::getRelatedProblem, 1)
                .orderByDesc(TestCaseEntity::getUpdateTime);
        List<TestCaseEntity> cases = this.baseMapper.selectList(queryWrapper);
        return ResponseResult.okResult(cases);
    }
}
