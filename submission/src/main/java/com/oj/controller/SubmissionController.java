package com.oj.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.oj.entity.SubmissionEntity;
import com.oj.service.SubmissionService;
import com.oj.utils.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/submission")
public class SubmissionController {
    @Autowired
    private SubmissionService submissionService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public ResponseResult list() {

        List<SubmissionEntity> list = submissionService.list();

        return ResponseResult.okResult(list);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public ResponseResult info(@PathVariable("id") Long id) {
        SubmissionEntity submission = submissionService.getById(id);

        return ResponseResult.okResult(submission);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public ResponseResult save(@RequestBody SubmissionEntity submission) {
        submissionService.save(submission);

        return ResponseResult.okResult();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public ResponseResult update(@RequestBody SubmissionEntity submission) {
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
