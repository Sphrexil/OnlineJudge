package com.oj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.oj.entity.ProblemEntity;
import com.oj.utils.ResponseResult;



public interface ProblemService extends IService<ProblemEntity> {

    /* problem分页，按照时间排序 */
    ResponseResult getProblemList(Integer pageNum, Integer pageSize, Integer difficulty, Boolean isDescByDifficulty);

    ResponseResult getProblemById(Long id);

    ResponseResult getProblemByShowId(Long showId);
}

