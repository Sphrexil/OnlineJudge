package com.oj.mq.test;

import com.oj.entity.SubmissionEntity;
import com.oj.mq.channels.SubmissionSink;
import com.oj.mq.channels.SubmissionSource;


import com.oj.pojo.vo.ResultVo;
import com.oj.utils.RedisCache;
import com.oj.utils.ResponseResult;
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
public class SubTestService {

//    private final SubmissionSource submissionSource;
    final RedisCache redisCache;
    private ResultVo res = null;

    @Autowired
    public SubTestService( RedisCache redisCache) {
//        this.submissionSource = submissionSource;
        this.redisCache = redisCache;
    }

//    @Cacheable(value = "result", key = "#root.method.name")
    public ResponseResult send(SubmissionEntity msg) {
        MessageBuilder<SubmissionEntity> message = MessageBuilder.withPayload(msg);
//        boolean flag1 = submissionSource.submissionOutput().send(message.build());
//        if (flag1) {
//            while (res == null) {
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
        return ResponseResult.okResult(res);
    }

//    @StreamListener(SubmissionSink.ResInput)
//    public void setReceiveMsg(@Payload ResultVo receiveMsg) {
//        log.info("回调消息接收成功{}", receiveMsg);
//        res = receiveMsg;
//    }
}
