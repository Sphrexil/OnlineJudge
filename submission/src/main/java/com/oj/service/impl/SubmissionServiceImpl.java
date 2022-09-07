package com.oj.service.impl;


import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.oj.constants.SubmissionConstant;
import com.oj.dao.SubmissionDao;
import com.oj.entity.SubmissionStatusEntity;
import com.oj.feign.ProblemFeignService;
import com.oj.mq.channels.SubmissionSink;
import com.oj.mq.channels.SubmissionSource;
import com.oj.pojo.dto.SubmissionDto;
import com.oj.pojo.vo.ResultVo;
import com.oj.pojo.vo.UerProblemListVo;
import com.oj.service.SubmissionService;
import com.oj.utils.PageUtils;
import com.oj.utils.RedisCache;
import com.oj.utils.ResponseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


@Service
public class SubmissionServiceImpl extends ServiceImpl<SubmissionDao, SubmissionStatusEntity> implements SubmissionService {
    private static final Logger log = LoggerFactory.getLogger(SubmissionServiceImpl.class);
    @Autowired
    private ProblemFeignService problemFeignService;
    @Autowired
    private SubmissionSource submissionSource;
    @Autowired
    private RedisCache redisCache;

    @Override
    @CachePut(value = SubmissionConstant.CACHE_GROUP + "#300", key = "#root.method.name + #uerProblemListVo.relatedUser+#uerProblemListVo.problemId")
    public ResponseResult getSubmissionList(UerProblemListVo uerProblemListVo) {
        /* 用uuid作为value可防止误删锁 */
        String uuid = UUID.randomUUID().toString();
        Boolean lock = redisCache.setCacheObjectIfAbsent(SubmissionConstant.LOCK_KEY, uuid, SubmissionConstant.LOCK_TTL, TimeUnit.SECONDS);
        if (lock) {
            ResponseResult responseResult;
            try {
                responseResult = getResponseResult(uerProblemListVo);
            }finally {
                /* 无论是否发生异常都会解锁 */
                redisCache.execute(new DefaultRedisScript<>(SubmissionConstant.LOCK_SCRIPT, Long.class), Arrays.asList(SubmissionConstant.LOCK_KEY), uuid);
            }
            return responseResult;
        } else {
            try {
                /* 如果发现业务已上锁，则进行休眠100毫秒自旋查询 */
                Thread.sleep(SubmissionConstant.LOCK_SPIN_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return getSubmissionList(uerProblemListVo);
        }
    }

    private ResponseResult getResponseResult(UerProblemListVo uerProblemListVo) {
        PageUtils pageData;
        // 取值
        Long author = uerProblemListVo.getRelatedUser();
        Long problemId = uerProblemListVo.getProblemId();
        String language = uerProblemListVo.getLanguage();
        Integer pageNum = uerProblemListVo.getPageNum();
        Integer pageSize = uerProblemListVo.getPageSize();

        LambdaQueryWrapper<SubmissionStatusEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SubmissionStatusEntity::getRelatedUser, author)
                // 用户可能会选择查询自己所有的提交记录
                .eq(Objects.nonNull(problemId), SubmissionStatusEntity::getRelatedProblem, problemId)
                // 用户可能会选择查询具体语言语言
                .eq(Objects.nonNull(language), SubmissionStatusEntity::getLanguage, language);
        // TODO 分页，需要根据需求封装vo返回
        IPage<SubmissionStatusEntity> submissionIPage = PageUtils.getPage(pageNum, pageSize, SubmissionStatusEntity.class);
        page(submissionIPage, queryWrapper);
        pageData = new PageUtils(submissionIPage);
        return ResponseResult.okResult(pageData);
    }

    @Override
    @CacheEvict(value = SubmissionConstant.CACHE_GROUP, key = "'getSubmissionList' + #submission.relatedUser+#submission.relatedProblem")
    public ResponseResult submit(SubmissionStatusEntity submission) {

        Long problemId = submission.getRelatedProblem();
        String code = submission.getCode();
        String language = submission.getLanguage();
        ResponseResult res = problemFeignService.getProblemById(problemId);
        SubmissionDto submissionDto = (SubmissionDto) res.getData(new TypeReference<SubmissionDto>() {
        });

        submissionDto.setCode(code);
        submissionDto.setLanguage(language);
        if (Objects.nonNull(submission.getTestCase())) {
            submissionDto.setSubTestCase(submission.getTestCase());
        }
        // 这里加上language的设置
        // 这里发送消息,这里可以加上tags，headers等附带判断消息
        boolean flag = submissionSource.submissionOutput().send(MessageBuilder.withPayload(submissionDto).build());
        if (flag) {
            int count = 0;
            while (result == null) {
                try {
                    // 这里可以将查的时间缩短点，但是有可能的是这边发成功了，但是发回来的时候失败了
                    if (count > 10) {
                        break;
                    }
                    count ++;
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        if (!submission.getIsDebug()) {
            submission.setError(result.getCompileError());
            submission.setTimeUsage(result.getTimeUsage());
            submission.setMemoryUsage(result.getMemoryUsage());
            submission.setStatus(result.getStatus());
            this.save(submission);
        }
        return ResponseResult.okResult(result);
    }

    // 接收第一次的消息和回调消息的示例如下
    private ResultVo result = null; // 这里看自己需要什么类型的返回结果

    /* 回调消息接收 */
    @StreamListener(SubmissionSink.ResInput)
    public void setReceiveMsg(@Payload ResultVo receiveMsg) {
        log.info("消息接收成功:"+receiveMsg);
        result = receiveMsg;
    }
}
