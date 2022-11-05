package com.oj.mq.test;

import com.oj.constants.GlobalConstant;
import com.oj.mq.channels.JudgeSink;
import com.oj.mq.channels.JudgeSource;


import com.oj.service.pojo.dto.SubmissionDto;
import com.oj.pojo.vo.ResultVo;
import com.oj.utils.RedisCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
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
public class JudgeTestService {

    private final JudgeSource judgeSource;
    final RedisCache redisCache;


    @Autowired
    public JudgeTestService(JudgeSource judgeSource, RedisCache redisCache) {
        this.judgeSource = judgeSource;
        this.redisCache = redisCache;
    }

    @StreamListener(value =JudgeSink.SubmissionInput)
    public void judge(Message<SubmissionDto> message) {
        String key = (String) message.getHeaders().get(GlobalConstant.SUBMIT_UNIQUE_TOKEN);
        SubmissionDto payload =  message.getPayload();
        log.info("消息接收成功{}",message.getPayload());
        String language = payload.getLanguage();
        ResultVo resultVo = new ResultVo(200, 300, "NullGirlFriendException", "Wrong Answer");
        resultVo.setWrongSample("7\n3 6 1 4 2 5 7", "123123", "56");
        //        ResultEntity resultEntity = new ResultEntity(1L, resultVo, 5L, 1L, 2L, 1, date);
        redisCache.setCacheObject(GlobalConstant.JUDGED_RESULT_KEY + "::" + key, resultVo, 20, TimeUnit.SECONDS);
    }
}
