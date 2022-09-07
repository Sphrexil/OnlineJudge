package com.oj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.oj.entity.TestCaseEntity;
import com.oj.utils.ResponseResult;
import org.springframework.stereotype.Service;

public interface TestCaseService extends IService<TestCaseEntity> {
    public ResponseResult getTestCaseListByProblemID(Integer problemID);
}
