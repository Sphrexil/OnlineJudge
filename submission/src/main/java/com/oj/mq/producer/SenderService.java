package com.oj.mq.producer;

import com.alibaba.fastjson.JSON;
import com.oj.entity.SubmissionEntity;
import com.oj.mq.channels.SubmissionSink;
import com.oj.mq.channels.SubmissionSource;


import com.oj.pojo.dto.SubmissionDto;
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
 * author: zhenyu
 * version: 1.0
 */

@Service
public class SenderService {



    @Autowired
    private SubmissionSource submissionSource;
    @Autowired
    RedisCache redisCache;
    SubmissionDto res = null;
    @Cacheable(value = "result", key = "#root.method.name")
    public ResponseResult send(SubmissionEntity msg) {
//        boolean flagTest = source.output().send(MessageBuilder.withPayload(msg).build());

        MessageBuilder<SubmissionEntity> message = MessageBuilder.withPayload(msg);
        boolean flag1 = submissionSource.firthOutput().send(message.build());
//        boolean flag2 = submissionSource.secondOutput().send(message.build());
//        System.out.println("消息发送"+(flagTest?"flagTest成功":"flagTest失败")+"了");
        while (res == null) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        redisCache.setCacheObject("res", ResponseResult.okResult(res));
        return ResponseResult.okResult(res);
//        System.out.println("消息发送"+(flag2?"flag2成功":"flag2失败")+"了");
    }
    @StreamListener(SubmissionSink.SecondSubmissionInput)
    public void setReceiveMsg(@Payload SubmissionDto receiveMsg) {
        res = receiveMsg;
    }
}
