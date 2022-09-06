package com.oj.service.impl;


import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.oj.dao.SubmissionDao;
import com.oj.entity.SubmissionEntity;
import com.oj.feign.ProblemFeignService;
import com.oj.mq.channels.SubmissionSink;
import com.oj.mq.channels.SubmissionSource;
import com.oj.pojo.dto.SubmissionDto;
import com.oj.pojo.vo.UerProblemListVo;
import com.oj.service.SubmissionService;
import com.oj.utils.PageUtils;
import com.oj.utils.ResponseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.Objects;


@Service
public class SubmissionServiceImpl extends ServiceImpl<SubmissionDao, SubmissionEntity> implements SubmissionService {
    private static final Logger log = LoggerFactory.getLogger(SubmissionServiceImpl.class);
    @Autowired
    private ProblemFeignService problemFeignService;
    @Autowired
    private SubmissionSource submissionSource;

    @Override
    public ResponseResult getSubmissionList(UerProblemListVo uerProblemListVo) {
        if (Objects.isNull(uerProblemListVo)) {
            // TODO 待加入错误信息
            throw new RuntimeException("信息为空");
        }
        // 取值
        Long author = uerProblemListVo.getAuthor();
        Long problemId = uerProblemListVo.getProblemId();
        String language = uerProblemListVo.getLanguage();
        Integer pageNum = uerProblemListVo.getPageNum();
        Integer pageSize = uerProblemListVo.getPageSize();

        LambdaQueryWrapper<SubmissionEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SubmissionEntity::getAuthor, author)
                    // 用户可能会选择查询自己所有的提交记录
                    .eq(Objects.nonNull(problemId), SubmissionEntity::getProblemId, problemId)
                    // 用户可能会选择查询具体语言语言
                    .eq(Objects.nonNull(language), SubmissionEntity::getLanguage, language);
        // TODO 分页，需要根据需求封装vo返回
        IPage<SubmissionEntity> submissionIPage = PageUtils.getPage(pageNum, pageSize, SubmissionEntity.class);
        page(submissionIPage, queryWrapper);
        PageUtils pageData = new PageUtils(submissionIPage);

        return ResponseResult.okResult(pageData);
    }

    @Override
    public ResponseResult submit(SubmissionEntity submission) {

        Long problemId = submission.getProblemId();
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
            while (this.res == null) {
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
        return ResponseResult.okResult(this.res);
    }

    // 接收第一次的消息和回调消息的示例如下
    private SubmissionDto res = null; // 这里看自己需要什么类型的返回结果

    /* 回调消息接收 */
    @StreamListener(SubmissionSink.ResInput)
    public void setReceiveMsg(@Payload SubmissionDto receiveMsg) {
        log.info("消息接收成功:"+receiveMsg);
        res = receiveMsg;
    }

}
