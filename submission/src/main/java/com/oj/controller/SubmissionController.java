package com.oj.controller;

import java.util.Arrays;


import com.oj.entity.SubmissionStatusEntity;
import com.oj.pojo.vo.UerProblemListVo;
import com.oj.service.SubmissionService;
import com.oj.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/submission")
public class SubmissionController {
    @Autowired
    private SubmissionService submissionService;

    @PostMapping("/submit")
    public ResponseResult submit(@Valid @RequestBody SubmissionStatusEntity submission) {

        return submissionService.submit(submission);
    }

    /**
     * 列表
     */
    @GetMapping("/list")
    public ResponseResult getSubmissionList(@Valid @RequestBody UerProblemListVo uerProblemListVo) {

        return submissionService.getSubmissionList(uerProblemListVo);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public ResponseResult info(@PathVariable("id") Long id) {
        SubmissionStatusEntity submission = submissionService.getById(id);

        return ResponseResult.okResult(submission);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public ResponseResult save(@RequestBody SubmissionStatusEntity submission) {
        submissionService.save(submission);

        return ResponseResult.okResult();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public ResponseResult update(@RequestBody SubmissionStatusEntity submission) {
        submissionService.updateById(submission);

        return ResponseResult.okResult();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    public ResponseResult delete(@RequestBody Long[] ids) {
        submissionService.removeByIds(Arrays.asList(ids));

        return ResponseResult.okResult();
    }

}
