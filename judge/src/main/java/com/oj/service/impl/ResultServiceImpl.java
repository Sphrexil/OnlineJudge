package com.oj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.oj.TestUtils.CompileException;
import com.oj.TestUtils.Judge;
import com.oj.TestUtils.JudgingArgument;
import com.oj.pojo.vo.TestResultVo;
import com.oj.dao.ResultDao;
import com.oj.entity.ResultEntity;
import com.oj.pojo.vo.SubmissionDto;
import com.oj.pojo.vo.ResultVo;
import com.oj.service.ResultService;
import com.oj.utils.RedisCache;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

/**
 * (Result)表服务实现类
 *
 * @author makejava
 * @since 2022-09-06 09:11:07
 */
@Service
@Slf4j
public class ResultServiceImpl extends ServiceImpl<ResultDao, ResultEntity> implements ResultService, RocketMQListener {


    final RedisCache redisCache;

    @Autowired
    public ResultServiceImpl(RedisCache redisCache) {
        this.redisCache = redisCache;
    }

    @Override
    public void judge(@Payload SubmissionDto msg) {
        log.info("消息接收成功{}",msg);
        ResultVo resultVo = new ResultVo();;
        JudgingArgument judgingArgument = new JudgingArgument(msg);
        ResultEntity resultEntity = new ResultEntity(1L, new ArrayList<>(), 5L, 1L, 2L, 1, new Date());
        try {
            TestResultVo testResultVo = Judge.process(judgingArgument);
            resultEntity.getResultOfCase().add(testResultVo);
        }catch (CompileException e){
            resultVo = new ResultVo(0, 0, e.getMessage(), "COMPILE_ERROR");
            return;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // 存入数据库
        for(TestResultVo i : resultEntity.getResultOfCase()){
            resultVo.setMemoryUsage(i.getMemory());
            resultVo.setTimeUsage(i.getCpuTime());
            switch (i.getResult()){
                case 0:
                    break;
                case 1:
                    resultVo.setStatus("TIME_LIMIT_EXCEEDED");
                    break;
                case 2:
                    resultVo.setStatus("TIME_LIMIT_EXCEEDED");
                    break;
                case 3:
                    resultVo.setStatus("MEMORY_LIMIT_EXCEEDED");
                    break;
                case 4:
                    resultVo.setStatus("RUNTIME_ERROR");
                    break;
                case 5:
                    resultVo.setStatus("SYSTEM_ERROR");
                    break;
                case 6:
                    resultVo.setStatus("WRONG_ANSWER");
                default:
                    break;
            }
        }
        if("".equals(resultVo.getStatus())){
            resultVo.setStatus("ACCEPT");
        }
        log.info("final result of test {}", resultVo);
    }

    @Override
    public void onMessage(Object message) {

    }
}
