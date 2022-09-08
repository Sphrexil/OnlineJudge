package com.oj.mq.test;

import com.alibaba.fastjson.JSON;


import com.oj.pojo.MyMessage;
import com.oj.pojo.vo.SubmissionDto;
import com.oj.pojo.vo.ResultVo;
import com.oj.utils.RedisCache;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;


/**
 * description: SenderService
 * date: 2022/8/30 11:27
 * @author: zhenyu
 * version: 1.0
 */

@Service
@Slf4j
@RocketMQMessageListener(consumerGroup = "sub-group", topic = "sub-topic" )
public class JudgeTestService implements RocketMQListener<MyMessage> {

    final RedisCache redisCache;
    @Autowired
    public JudgeTestService( RedisCache redisCache) {
        this.redisCache = redisCache;
    }
    @Override
    public void onMessage(MyMessage message) {
        String key = message.getToken();
        SubmissionDto msg = JSON.parseObject(JSON.toJSONString(message.getMsg()),SubmissionDto.class);
        log.info("消息接收成功123{}", msg);
        ResultVo resultVo = new ResultVo(200, 300, "NullGirlfriendException", "Error");
        redisCache.setCacheObject("result::"+key, resultVo, 20, TimeUnit.SECONDS);
    }

}
