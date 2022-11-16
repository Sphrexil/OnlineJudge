package com.oj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.oj.entity.CommentEntity;
import com.oj.utils.ResponseResult;


import javax.servlet.http.HttpServletRequest;


/**
 * 评论表(SgComment)表服务接口
 *
 * @author makejava
 * @since 2022-04-09 15:29:55
 */
public interface CommentService extends IService<CommentEntity> {

    ResponseResult commentList(Integer commentType, Long articleId, Integer pageNum, Integer pageSize);

    ResponseResult addComment(CommentEntity comment, HttpServletRequest request);

    ResponseResult getMaxId();

}


