package com.oj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.oj.entity.ProblemEntity;
import com.oj.utils.ResponseResult;



public interface ProblemService extends IService<ProblemEntity> {

    /* 不分难度的problem分页，按照时间排序 */
    ResponseResult getProblemList(Integer pageNum, Integer pageSize);
}

