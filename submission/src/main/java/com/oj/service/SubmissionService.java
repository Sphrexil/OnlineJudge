package com.oj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.oj.entity.SubmissionEntity;
import com.oj.pojo.vo.UerProblemListVo;
import com.oj.utils.ResponseResult;

/**
 *
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2022-08-27 16:37:04
 */
public interface SubmissionService extends IService<SubmissionEntity> {

    ResponseResult getSubmissionList(UerProblemListVo uerProblemListVo);

    ResponseResult submit(SubmissionEntity submission);
}

