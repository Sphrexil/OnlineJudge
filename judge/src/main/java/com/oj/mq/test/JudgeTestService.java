package com.oj.mq.test;

import com.oj.mq.channels.SubmissionSink;
import com.oj.mq.channels.SubmissionSource;


import com.oj.pojo.dto.SubmissionDto;
import com.oj.utils.RedisCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;



/**
 * description: SenderService
 * date: 2022/8/30 11:27
 * @author: zhenyu
 * version: 1.0
 */

@Service
@Slf4j
public class JudgeTestService {

    private final SubmissionSource submissionSource;
    final RedisCache redisCache;


    @Autowired
    public JudgeTestService(SubmissionSource submissionSource, RedisCache redisCache) {
        this.submissionSource = submissionSource;
        this.redisCache = redisCache;
    }

    @StreamListener(SubmissionSink.SubmissionInput)
    public void judge(@Payload SubmissionDto msg) {
        log.info("消息接收成功{}",msg);
        boolean flag = submissionSource.resOut().send(MessageBuilder.withPayload(msg).build());
        log.info("回调消息发送状态{}",flag);
    }
}
