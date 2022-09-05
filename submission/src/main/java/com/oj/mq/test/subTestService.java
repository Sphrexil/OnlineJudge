package com.oj.mq.test;

import com.oj.entity.SubmissionEntity;
import com.oj.mq.channels.SubmissionSink;
import com.oj.mq.channels.SubmissionSource;


import com.oj.utils.RedisCache;
import com.oj.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
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
public class subTestService {

    private final SubmissionSource submissionSource;
    final RedisCache redisCache;
    SubmissionEntity res = null;

    @Autowired
    public subTestService(SubmissionSource submissionSource, RedisCache redisCache) {
        this.submissionSource = submissionSource;
        this.redisCache = redisCache;
    }

    @Cacheable(value = "result", key = "#root.method.name")
    public ResponseResult send(SubmissionEntity msg) {

        MessageBuilder<SubmissionEntity> message = MessageBuilder.withPayload(msg);
        boolean flag1 = submissionSource.submissionOutput().send(message.build());
        if (flag1) {
            while (res == null) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        redisCache.setCacheObject("res", ResponseResult.okResult(res));
        return ResponseResult.okResult(res);
    }
    public ResponseResult send(String msg) {

        MessageBuilder<String> message = MessageBuilder.withPayload(msg);
        boolean flag1 = submissionSource.submissionOutput().send(message.build());
        if (flag1) {
            while (res == null) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        redisCache.setCacheObject("res", ResponseResult.okResult(res));
        return ResponseResult.okResult(res);
    }
    @StreamListener(SubmissionSink.SubmissionInput)
    public void receive(@Payload SubmissionEntity msg) {
        System.out.println("消息接收成功:"+msg);
        submissionSource.resOut().send(MessageBuilder.withPayload(msg).build());
    }
    @StreamListener(SubmissionSink.ResInput)
    public void setReceiveMsg(@Payload SubmissionEntity receiveMsg) {
        System.out.println("消息接收成功:"+receiveMsg);
        res = receiveMsg;
    }
}
