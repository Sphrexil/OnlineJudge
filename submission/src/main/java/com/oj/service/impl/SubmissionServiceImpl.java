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
import com.oj.pojo.vo.UerProblemVo;
import com.oj.service.SubmissionService;
import com.oj.utils.BeanCopyUtils;
import com.oj.utils.PageUtils;
import com.oj.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import java.util.Objects;


@Service
public class SubmissionServiceImpl extends ServiceImpl<SubmissionDao, SubmissionEntity> implements SubmissionService {

    @Autowired
    private ProblemFeignService problemFeignService;
    @Autowired
    private SubmissionSource submissionSource;

    @Override
    public ResponseResult getSubmissionList(Integer pageNum, Integer pageSize,UerProblemVo uerProblemVo) {
        if (Objects.isNull(uerProblemVo)) {
            // TODO 待加入错误信息
            throw new RuntimeException("信息为空");
        }
        // 取值
        Long author = uerProblemVo.getAuthor();
        Long problemId = uerProblemVo.getProblemId();
        String language = uerProblemVo.getLanguage();
        if (Objects.isNull(author)) {
            throw new RuntimeException("用户信息不能为空");
        }
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

        if (Objects.isNull(submission)) {
            throw new RuntimeException("提交不能为空");
        }
        Long problemId = submission.getProblemId();
        if (Objects.isNull(problemId)) {
            throw new RuntimeException("提交的题目不能为空");
        }
        String code = submission.getCode();
        if (Objects.isNull(code)) {
            throw new RuntimeException("提交的内容不能为空");
        }
        String language = submission.getLanguage();
        ResponseResult res = problemFeignService.getProblemById(problemId);
        SubmissionDto submissionDto = (SubmissionDto) res.getData(new TypeReference<SubmissionDto>() {
        });

        submissionDto.setCode(code);
        submissionDto.setLanguage(language);
        // 这里加上language的设置
        // 这里发送消息,这里可以加上tags，headers等附带判断消息
        boolean flag = submissionSource.submissionOutput().send(MessageBuilder.withPayload(submissionDto).build());
        if (flag) {
            int count = 0;
            while (res == null) {
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
        return ResponseResult.okResult(submissionDto);
    }

    // 接收第一次的消息和回调消息的示例如下
    private SubmissionEntity res = null; // 这里看自己需要什么类型的返回结果
    /* 第一次消息接收，应该将这个放到judge里面去，可以用一个变量接收 */
    @StreamListener(SubmissionSink.SubmissionInput)
    public void receive(@Payload SubmissionEntity msg) {
        System.out.println("消息接收成功:"+msg);
        submissionSource.resOut().send(MessageBuilder.withPayload(msg).build());
    }
    /* 回调消息接收 */
    @StreamListener(SubmissionSink.ResInput)
    public void setReceiveMsg(@Payload SubmissionEntity receiveMsg) {
        System.out.println("消息接收成功:"+receiveMsg);
        res = receiveMsg;
    }
}
