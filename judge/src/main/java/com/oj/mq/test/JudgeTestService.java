package com.oj.mq.test;

import com.oj.entity.ResultEntity;
import com.oj.mq.channels.JudgeSink;
import com.oj.mq.channels.JudgeSource;


import com.oj.pojo.dto.SubmissionDto;
import com.oj.pojo.vo.ResultVo;
import com.oj.utils.RedisCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.TimeUnit;


/**
 * description: SenderService
 * date: 2022/8/30 11:27
 * @author: zhenyu
 * version: 1.0
 */

@Service
@Slf4j
public class JudgeTestService {

    private final JudgeSource judgeSource;
    final RedisCache redisCache;


    @Autowired
    public JudgeTestService(JudgeSource judgeSource, RedisCache redisCache) {
        this.judgeSource = judgeSource;
        this.redisCache = redisCache;
    }

    @StreamListener(JudgeSink.SubmissionInput)
    public void judge(Message message) {
        String key = (String) message.getHeaders().get("key");
        log.info("消息接收成功123{}",message.getPayload());
        ResultVo resultVo = new ResultVo(200, 300, "NullGirlfriendException", "Error");
        //        ResultEntity resultEntity = new ResultEntity(1L, resultVo, 5L, 1L, 2L, 1, date);
        redisCache.setCacheObject("result::"+key, resultVo, 20, TimeUnit.SECONDS);
    }
}
