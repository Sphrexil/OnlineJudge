package com.oj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.oj.entity.ResultEntity;
import com.oj.service.pojo.dto.SubmissionDto;
import org.springframework.messaging.handler.annotation.Payload;


/**
 * (Result)表服务接口
 *
 * @author makejava
 * @since 2022-09-06 09:11:07
 */
public interface ResultService extends IService<ResultEntity> {
    public void judge(@Payload SubmissionDto msg);
}


