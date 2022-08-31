package com.oj.service.impl;


import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.oj.dao.SubmissionDao;
import com.oj.entity.SubmissionEntity;
import com.oj.feign.ProblemFeignService;
import com.oj.pojo.dto.SubmissionDto;
import com.oj.pojo.vo.UerProblemVo;
import com.oj.service.SubmissionService;
import com.oj.utils.BeanCopyUtils;
import com.oj.utils.PageUtils;
import com.oj.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import java.util.Objects;


@Service
public class SubmissionServiceImpl extends ServiceImpl<SubmissionDao, SubmissionEntity> implements SubmissionService {

    @Autowired
    private ProblemFeignService problemFeignService;

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

        Long problemId = submission.getProblemId();
        String code = submission.getCode();
        ResponseResult res = problemFeignService.getProblemById(problemId);
        SubmissionDto submissionDto = (SubmissionDto) res.getData(new TypeReference<SubmissionDto>() {
        });

        System.out.println(submissionDto);
        submissionDto.setCode(code);

        return ResponseResult.okResult(submissionDto);
    }
}
