package com.oj.service.impl;


import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.oj.constants.SubmissionConstant;
import com.oj.dao.SubmissionDao;
import com.oj.entity.SubmissionStatusEntity;
import com.oj.enums.ResultCode;
import com.oj.exceptions.SystemException;
import com.oj.feign.ProblemFeignService;
import com.oj.pojo.MyMessage;
import com.oj.pojo.vo.ResultVo;
import com.oj.pojo.vo.SubmissionDto;
import com.oj.pojo.vo.UerProblemListVo;
import com.oj.service.SubmissionService;
import com.oj.utils.PageUtils;
import com.oj.utils.RedisCache;
import com.oj.utils.ResponseResult;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


@Service
public class SubmissionServiceImpl extends ServiceImpl<SubmissionDao, SubmissionStatusEntity> implements SubmissionService {
    private static final Logger log = LoggerFactory.getLogger(SubmissionServiceImpl.class);
    @Autowired
    private ProblemFeignService problemFeignService;
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private RedissonClient redisson;
    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Override
    @CachePut(value = SubmissionConstant.CACHE_GROUP + "#300", key = "#root.method.name + #uerProblemListVo.relatedUser+#uerProblemListVo.problemId")
    public ResponseResult getSubmissionList(UerProblemListVo uerProblemListVo) {
        /* 用uuid作为value可防止误删锁 */
        RLock lock = redisson.getLock("SubmissionListLock");
        lock.lock(SubmissionConstant.LOCK_TTL_LIST, TimeUnit.SECONDS);
        ResponseResult responseResult;
        try {
            responseResult = getResponseResult(uerProblemListVo);
        } finally {
            lock.unlock();
            /* 无论是否发生异常都会解锁 */
        }
        return responseResult;
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

        // 这里加上language的设置
        // 这里发送消息,这里可以加上tags，headers等附带判断消息
        RLock lock = redisson.getLock("SubmitLock");
        lock.lock(SubmissionConstant.LOCK_TTL_SUBMIT, TimeUnit.SECONDS);
        ResultVo result = null;
        try {
            ResponseResult res = problemFeignService.getProblemById(problemId);
            SubmissionDto submissionDto = (SubmissionDto) res.getData(new TypeReference<SubmissionDto>() {
            });

            submissionDto.setCode(code);
            submissionDto.setLanguage(language);
            if (Objects.nonNull(submission.getTestCase())) {
                submissionDto.setSubTestCase(submission.getTestCase());
            }
            String uuid = UUID.randomUUID().toString();

            rocketMQTemplate.asyncSend("sub-topic", MessageBuilder.withPayload(new MyMessage(submissionDto, uuid)).build(), new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    log.info("消息发送成功！: {}", sendResult);
                }

                @Override
                public void onException(Throwable throwable) {
                    log.error("消息发送失败！:" + throwable);
                }
            }, 5000);
            int count = 0;
            while (result == null) {
                try {
                    result = redisCache.getCacheObject("result::" + uuid);
                    // 这里可以将查的时间缩短点，但是有可能的是这边发成功了，但是发回来的时候失败了
                    if (count >= 50) {
                        break;
                    }
                    count++;
                    Thread.sleep(SubmissionConstant.LOCK_SPIN_TIME);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (res == null) {
                throw new SystemException(ResultCode.SUBMIT_RESULT_BLANK);
            }
        } catch (Exception e) {
            log.error("结果获取异常:" + e);
        } finally {
            lock.unlock();
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
}
