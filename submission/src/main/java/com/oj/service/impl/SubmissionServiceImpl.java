package com.oj.service.impl;


import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.oj.constants.GlobalConstant;
import com.oj.constants.SubmissionConstant;
import com.oj.dao.SubmissionDao;
import com.oj.entity.SubmissionStatusEntity;
import com.oj.enums.ResultCode;
import com.oj.exceptions.SystemException;
import com.oj.feign.ProblemFeignService;
import com.oj.mq.channels.SubmissionSource;
import com.oj.pojo.dto.SubmissionDto;
import com.oj.pojo.vo.ResultVo;
import com.oj.pojo.vo.UerProblemListVo;
import com.oj.service.SubmissionService;
import com.oj.utils.MD5Utils;
import com.oj.utils.PageUtils;
import com.oj.utils.RedisCache;
import com.oj.utils.ResponseResult;
import com.oj.utils.redisson.DefaultRedisLockSupport;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
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

import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;


@Service
public class SubmissionServiceImpl extends ServiceImpl<SubmissionDao, SubmissionStatusEntity> implements SubmissionService {
    private static final Logger log = LoggerFactory.getLogger(SubmissionServiceImpl.class);
    @Autowired
    private ProblemFeignService problemFeignService;
    @Autowired
    private SubmissionSource submissionSource;
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private RedissonClient redisson;

    @Autowired
    void initMethod() {
        // ????????????????????????
        DefaultRedisLockSupport.initDefaultRedissonClient(redisson);
    }

    @Override
    @CachePut(value = SubmissionConstant.CACHE_GROUP + "#300", key = "#root.method.name + #uerProblemListVo.relatedUser+#uerProblemListVo.problemId")
    public ResponseResult getSubmissionList(UerProblemListVo uerProblemListVo) {
        /* ???uuid??????value?????????????????? */
        ResponseResult finalResult = new DefaultRedisLockSupport(SubmissionConstant.SUBMISSION_LIST_LOCK + "::" + UUID.randomUUID(), 500, SubmissionConstant.LOCK_TTL_LIST * 1000, TimeUnit.MILLISECONDS).exec(item -> {
            ResponseResult responseResult = getResponseResult(item);
            return responseResult;
        }, uerProblemListVo);
        return finalResult;
    }

    private ResponseResult getResponseResult(UerProblemListVo uerProblemListVo) {
        PageUtils pageData;
        // ??????
        Long author = uerProblemListVo.getRelatedUser();
        Long problemId = uerProblemListVo.getProblemId();
        String language = uerProblemListVo.getLanguage();
        Integer pageNum = uerProblemListVo.getPageNum();
        Integer pageSize = uerProblemListVo.getPageSize();

        LambdaQueryWrapper<SubmissionStatusEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SubmissionStatusEntity::getRelatedUser, author)
                // ??????????????????????????????????????????????????????
                .eq(Objects.nonNull(problemId), SubmissionStatusEntity::getRelatedProblem, problemId)
                // ?????????????????????????????????????????????
                .eq(Objects.nonNull(language), SubmissionStatusEntity::getLanguage, language)
                .orderByDesc(SubmissionStatusEntity::getCreateTime);
        // TODO ?????????????????????????????????vo??????
        if (uerProblemListVo.getPageNum() == null || uerProblemListVo.getPageSize() == null) {
            return ResponseResult.okResult(list(queryWrapper));
        }
        IPage<SubmissionStatusEntity> submissionIPage = PageUtils.getPage(pageNum, pageSize, SubmissionStatusEntity.class);
        page(submissionIPage, queryWrapper);
        pageData = new PageUtils(submissionIPage);
        return ResponseResult.okResult(pageData);
    }

    @Override
    @CacheEvict(value = SubmissionConstant.CACHE_GROUP, key = "'getSubmissionList' + #submission.relatedUser+#submission.relatedProblem")
    public ResponseResult submit(SubmissionStatusEntity submission) {

        //??????????????????MD5??????????????????????????????id
        String CodeMD5 = MD5Utils.GetStringMD5((submission.getCode() + submission.getCode()).getBytes());
        boolean redisQueryResult =  redisCache.redisTemplate.hasKey(CodeMD5);;
        ResponseResult finalResult;
        if(redisQueryResult){
            throw new SystemException(ResultCode.CODE_ALREADY_EXISTS);
        }
        redisCache.setCacheObject(CodeMD5, "EXISTS", 1, TimeUnit.DAYS);
        // ????????????language?????????
        // ??????????????????,??????????????????tags???headers?????????????????????
         finalResult = new DefaultRedisLockSupport(SubmissionConstant.SUBMIT_LOCK + "::" + UUID.randomUUID(),
                500, SubmissionConstant.LOCK_TTL_SUBMIT * 1000, TimeUnit.MILLISECONDS)
                .exec(item -> {
                    ResultVo result = null;
                    try {
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
                        String uuid = UUID.randomUUID().toString();
                        boolean flag = submissionSource.submissionOutput().send(
                                MessageBuilder.withPayload(submissionDto)
                                        .setHeader(GlobalConstant.SUBMIT_UNIQUE_TOKEN, uuid)
                                /* ??????????????????????????????????????????tag???????????????????????????judge */
//                    .setHeader(RocketMQHeaders.TAGS, "cpp-tag")
                                .build());
                        if (flag) {
                            int count = 0;
                            while (result == null) {
                                try {
                                    result = redisCache.getCacheObject(GlobalConstant.JUDGED_RESULT_KEY + "::" + uuid);
                                    // ??????????????????????????????????????????????????????????????????????????????????????????????????????????????????
                                    // ??????????????????5s??????????????????????????????
                                    if (count >= 50) {
                                        break;
                                    }
                                    count++;
                                    Thread.sleep(SubmissionConstant.LOCK_SPIN_TIME);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        if (result == null) {
                            throw new SystemException(ResultCode.SUBMIT_RESULT_BLANK);
                        } else {
                            if (!submission.getIsDebug()) {
                                submission.setError(result.getCompileError());
                                submission.setTimeUsage(result.getTimeUsage());
                                submission.setMemoryUsage(result.getMemoryUsage());
                                submission.setStatus(result.getStatus());
                                this.save(submission);
                            }
                        }
                    } catch (Exception e) {
                        log.error("??????????????????:" + e);
                        return ResponseResult.errorResult(ResultCode.SUBMIT_RESULT_BLANK);
                    }
                    return ResponseResult.okResult(result);
                }, submission);
        return finalResult;
    }
}
